package com.example.cateringservice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignupActivity extends AppCompatActivity {
    private String TAG = SignupActivity.class.getSimpleName();

    EditText firstName;
    EditText lastName;
    EditText emailOrPhone;
    EditText newPass;
    EditText confirmPass;
    EditText birthDateYear;
    EditText birthDateMonth;
    EditText birthDateDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firstName = findViewById(R.id.firstnameid);

        lastName = findViewById(R.id.lastnameid);

        emailOrPhone = findViewById(R.id.emailormobileid);

        newPass = findViewById(R.id.newpassid);

        confirmPass = findViewById(R.id.confirmpassid);

//        birthDateYear = findViewById(R.id.birthdateyearid);

//        birthDateMonth = findViewById(R.id.birthdatemonthid);

//        birthDateDay = findViewById(R.id.birthdatedayid);

//        Button registerButton = findViewById(R.id.registerbuttonid);

       /* registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shouldSendDataToServer())
                {
                    sendDataToServer();
                    Log.v(TAG, "sendDataToServer calling");
                }
                else {
                    Log.v(TAG, "sendDataToServer not calling");
                }
            }
        });*/
    }

    boolean isAllFieldFilled()
    {
        return !firstName.getText().toString().isEmpty() && !lastName.getText().toString().isEmpty() && !emailOrPhone.getText().toString().isEmpty() && !newPass.getText().toString().isEmpty() && !confirmPass.getText().toString().isEmpty() &&
            !birthDateYear.getText().toString().isEmpty() && !birthDateMonth.getText().toString().isEmpty() && !birthDateDay.getText().toString().isEmpty();

    }

    boolean isPasswordOk()
    {
        return newPass.getText().toString().equals(confirmPass.getText().toString());
    }

    boolean shouldSendDataToServer()
    {
        return isAllFieldFilled() && isPasswordOk();
    }

    void sendDataToServer()
    {

    }
}