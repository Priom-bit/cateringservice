package com.example.cateringservice;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.HashMap;
import java.util.Map;

public class Signup_Form extends AppCompatActivity {
    private static final String TAG = Signup_Form.class.getSimpleName();
    EditText firstNameField;
    EditText lastNameField;
    EditText mobileoremailField;
    EditText newpasswordField;
    EditText confirmpasswordField;
    Button registerButton;
    RadioGroup genderGroup;

    KProgressHUD progressHUD;
    int selectedGenderIndex; // 0 for male ......... 1 for female

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup__form);
        /*findViewById(R.id.signuplayoutid).setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideSoftKeyboard(Signup_Form.this);
                return false;
            }
        });*/

        firstNameField = findViewById(R.id.signupfirstnameid);
        lastNameField = findViewById(R.id.signuplastnameid);
        mobileoremailField = findViewById(R.id.signupmobileoremailid);
        newpasswordField = findViewById(R.id.signupnewpassid);
        confirmpasswordField = findViewById(R.id.signupconfirmpassid);
        genderGroup = findViewById(R.id.signupgendergroupid);
        registerButton = findViewById(R.id.signupregisterid);
        setTitle("Sign Up");

        registerButton.setAlpha(0.5f);
        firstNameField.addTextChangedListener(new TextFieldListener());
        lastNameField.addTextChangedListener(new TextFieldListener());
        mobileoremailField.addTextChangedListener(new TextFieldListener());
        newpasswordField.addTextChangedListener(new TextFieldListener());
        confirmpasswordField.addTextChangedListener(new TextFieldListener());
        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (group.getCheckedRadioButtonId() == R.id.signupfemaleid)
                    selectedGenderIndex = 1;
                else
                    selectedGenderIndex = 0;

                Log.v(TAG, "Selected gender index is " + selectedGenderIndex);
            }
        });
        selectedGenderIndex = 0;
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
            if (signupInfoOk()) {
                registerButton.setAlpha(1.0f);
                registerButton.setEnabled(true);
            }
            else {
                registerButton.setAlpha(0.5f);
                registerButton.setEnabled(false);
            }
        }
    }

    public void hideSoftKeyboard(AppCompatActivity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    public void registrationButtonClicked(View view) {
        checkSignupInfo();
    }

    private void checkSignupInfo()
    {
        if(signupInfoOk()){
            //saveUserDataToFirestore();
            checkEmailExistInServer();
            //startActivity(new Intent(getApplicationContext(),Login_Form.class));
        }
        else {
            Toast.makeText(this, "Please fill up all the fields!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean signupInfoOk()
    {
        if(firstNameField.getText().toString().isEmpty() || lastNameField.getText().toString().isEmpty() || mobileoremailField.
            getText().toString().isEmpty() || newpasswordField.getText().toString().isEmpty() || confirmpasswordField.getText().toString().
            isEmpty() || !(newpasswordField.getText().toString().equals(confirmpasswordField.getText().toString())))
        {
            return false;
        }
        return true;
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

    public boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public void checkEmailExistInServer() {
        progressHUD = KProgressHUD.create(Signup_Form.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait!")
                .setDetailsLabel("Registration is progressing!")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        Map<String, String> param1 = new HashMap<>();
        param1.put("email", mobileoremailField.getText().toString());

        Services.getInstance().getRequest("users", param1, null ,1, new Services.FireStoreCompletionListener() {
            @Override
            public void onGetSuccess(QuerySnapshot queryDocumentSnapshots) {
                Log.v(TAG,"Nirob test service is success and ok");
                if (queryDocumentSnapshots.size() <= 0) {
                    saveUserDataToFirestore();
                }
                else {
                    progressHUD.dismiss();
                    Toast.makeText(getApplicationContext(), "Email or phone number already exists!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onPostSuccess() {
                Log.v(TAG,"Nirob test service is success post and ok");
            }

            @Override
            public void onFailure(String error) {
                Log.v(TAG,"Nirob test service is success but not ok; error is: " + error);
            }
        });


    }

    public void saveUserDataToFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("firstname", firstNameField.getText().toString());
        user.put("lastname", lastNameField.getText().toString());
        user.put("email", mobileoremailField.getText().toString() );
        user.put("password",newpasswordField.getText().toString());
        user.put("gender", selectedGenderIndex == 1 ? "female" : "male");

        Services.getInstance().postRequest("users", user, new Services.FireStoreCompletionListener() {
            @Override
            public void onGetSuccess(QuerySnapshot querySnapshots) {

            }

            @Override
            public void onPostSuccess() {
                Log.v(TAG, "Testing DocumentSnapshot added");
                progressHUD.dismiss();
                startActivity(new Intent(getApplicationContext(),Login_Form.class));
                finish();
            }

            @Override
            public void onFailure(String error) {
                Log.v(TAG, "Testing Error adding document error: " + error);
                progressHUD.dismiss();
            }
        });
    }
}