package com.example.cateringservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.HashMap;
import java.util.Map;

public class Login_Form extends AppCompatActivity {
    private static final String TAG = Login_Form.class.getSimpleName();
    public static final String MyPREFERENCES = "MyPrefs" ;
    EditText emailField;
    EditText passwordField;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__form);

        emailField = findViewById(R.id.loginemailid);
        passwordField = findViewById(R.id.loginPasswordid);
        loginButton = findViewById(R.id.loginloginid);
        setTitle("Log In");

        loginButton.setAlpha(0.5f);
        loginButton.setEnabled(false);

        emailField.addTextChangedListener(new TextFieldListener());
        passwordField.addTextChangedListener(new TextFieldListener());
    }

    class TextFieldListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (loginInfoOk()) {
                loginButton.setAlpha(1.0f);
                loginButton.setEnabled(true);
            }
            else {
                loginButton.setAlpha(0.5f);
                loginButton.setEnabled(false);
            }
        }
    }


    public void setTitle(String title){
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView textView = new TextView(this);
        textView.setText(title);
        textView.setTextSize(20);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(getResources().getColor(android.R.color.white));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(textView);
    }

    public void btn_signupForm(View view) {
        startActivity(new Intent(getApplicationContext(),Signup_Form.class));
        //startActivity(new Intent(getApplicationContext(),NavigationDrawer.class));
        //saveUserDataToFirestore();
    }


    public void checkEmailExistInServer() {
        KProgressHUD progressHUD = KProgressHUD.create(Login_Form.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait!")
                .setDetailsLabel("Checking Login Data!")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .whereEqualTo("email", emailField.getText().toString())
                .whereEqualTo("password", passwordField.getText().toString())
                .limit(1)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.v(TAG, "Testing email exists: " + queryDocumentSnapshots.size());
                        if (queryDocumentSnapshots.size() <= 0) {
                            progressHUD.dismiss();
                            Toast.makeText(getApplicationContext(), "Email or password mismatch!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            progressHUD.dismiss();
                            loginSuccessfull();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.v(TAG, "Testing email does not exists");
                        Toast.makeText(getApplicationContext(), "Error occured: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void loginSuccessfull() {
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        SharedPreferences.Editor editor = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).edit();
        editor.putBoolean("LoggedInKey", true);
        editor.apply();
    }

    public boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private boolean isFillupAllField()
    {
        if (emailField.getText().toString().length() > 0 && passwordField.getText().toString().length()>0) {
            return true;
        }
        return false;
    }

    private boolean loginInfoOk() {
        if (isFillupAllField() && isValidEmail(emailField.getText().toString()))
            return true;
        return false;
    }

    public void btnLoginClicked(View view) {
        checkEmailExistInServer();
    }
}