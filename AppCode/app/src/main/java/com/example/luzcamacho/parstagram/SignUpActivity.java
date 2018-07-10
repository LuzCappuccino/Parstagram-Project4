package com.example.luzcamacho.parstagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {
    public static String tag = "SignUpActivity";
    private EditText Username;
    private EditText Handle;
    private EditText Email;
    private EditText Password;
    private Button SignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Username = findViewById(R.id.etSetUsername);
        Handle = findViewById(R.id.etSetHandle);
        Email = findViewById(R.id.etSetEmail);
        Password = findViewById(R.id.etSetPassword);
        SignUp = findViewById(R.id.btSignUp);

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean success = SubmitUser();
                if(success){
                    Toast.makeText(getApplicationContext(), "Success?", Toast.LENGTH_LONG).show();
                    Intent back = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(back);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Failed :(", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public boolean SubmitUser(){
        final String NewUsername = Username.getText().toString();
        final String NewHandle = Handle.getText().toString();
        final String NewEmail = Email.getText().toString();
        final String NewPassword = Password.getText().toString();

        Log.d(tag, "Username: "+NewUsername);
        Log.d(tag, "Handle: "+ NewHandle);
        Log.d(tag, "Email: "+NewEmail);
        Log.d(tag, "Password: " + NewPassword);

        if(NewUsername.length() == 0){
            Toast.makeText(getApplicationContext(), "Please include Username", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(NewHandle.length() == 0){
            Toast.makeText(getApplicationContext(), "Please include Handle", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(NewEmail.length() == 0){
            Toast.makeText(getApplicationContext(), "Please include an Email", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(NewPassword.length() == 0){
            Toast.makeText(getApplicationContext(), "Please include a Password", Toast.LENGTH_LONG).show();
            return false;
        }
        ParseUser newUser= new ParseUser();
        newUser.setUsername(NewUsername);
        newUser.put("handle", NewHandle);
        newUser.setEmail(NewEmail);
        newUser.setPassword(NewPassword);
        /* Actually saving user now */
        newUser.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    Log.d(tag, "Sign up!");
                }
                else{
                    Log.d(tag, "Failed to sign up :(");
                    e.printStackTrace();
                }
            }
        });
        return true;
    }
}
