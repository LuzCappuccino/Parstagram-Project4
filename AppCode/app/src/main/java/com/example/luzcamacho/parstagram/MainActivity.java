package com.example.luzcamacho.parstagram;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private Button btLogin;
    private Button btSignUp;
    public String tag = "LoginActivity";
    private AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btLogin = (Button) findViewById(R.id.btLogin);
        btSignUp = (Button) findViewById(R.id.btSignUp);

        if(ParseUser.getCurrentUser() != null){
            Log.d(tag, "there is a user in here somewhere");
            /* time to log in background */
            Log.d(tag, "Username: " + ParseUser.getCurrentUser().getUsername());

            Intent data = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(data);
            // so the user can't back out into login screen
            finish();
        }
        else{
            Log.d(tag, "User is null");
        }

        /* start animation! */
        RelativeLayout myThing = findViewById(R.id.rlLogin);
        animationDrawable =(AnimationDrawable) myThing.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(2000);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();
                login(username, password);
            }
        });

        btSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        animationDrawable.start();
    }

    private void login(String username, String password) {
        /* set up parse config */
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e == null){
                    Log.d(tag, "User was logged in successfully");
                    Toast.makeText(getApplicationContext(), "User was logged in successfully", Toast.LENGTH_LONG).show();
                    // since we are in a callback we wanna make sure we do maincativity.this
                    Intent data = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(data);
                    // so the user can't back out into login screen
                    finish();
                }
                else{
                    Log.e(tag, "Login failure");
                    Toast.makeText(getApplicationContext(), "User was not logged in", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
    }
}
