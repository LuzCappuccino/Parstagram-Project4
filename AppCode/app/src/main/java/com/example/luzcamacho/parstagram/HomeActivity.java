package com.example.luzcamacho.parstagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.parse.ParseUser;

public class HomeActivity extends AppCompatActivity {
    public static String tag = "HomeActivity";

    final Fragment fragmentCamera = new CameraLaunchFragment();
    final Fragment fragmentFeed = new LoadFeedFragment();
    final ProfileFragment fragmentProfile = new ProfileFragment();
    public FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fragmentManager = getSupportFragmentManager();

        switchToFeed();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.btCamera:
                        Log.d(tag, "Clicked on camera");
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.BigFrameBoi, fragmentCamera).commit();
                        return true;
                    case R.id.btFeed:
                        Log.d(tag, "Clicked on feed");
                        FragmentTransaction fragmentTransaction2 = fragmentManager.beginTransaction();
                        fragmentTransaction2.replace(R.id.BigFrameBoi, fragmentFeed).commit();
                        return true;
                    case R.id.btLogOut:
                        Log.d(tag, "Clicked on logout");
                        onClickLogout();
                        return true;
                    case R.id.btProfile:
                        Log.d(tag, "Clicked on profile");
                        fragmentProfile.setParseUser(null);
                        FragmentTransaction fragmentTransaction3 = fragmentManager.beginTransaction();
                        fragmentTransaction3.replace(R.id.BigFrameBoi, fragmentProfile).commit();
                }
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.camera, menu);
        return true;
    }

    public void onClickLogout() {
        /* Logging out the User */
        Log.d(tag, "Logging out the user");
        ParseUser.logOut();
        ParseUser currentUser = ParseUser.getCurrentUser();
        if(currentUser == null){
            Toast.makeText(getApplicationContext(), "Logout successful!", Toast.LENGTH_LONG).show();
            Log.d(tag, "User is now null. Logout successful.");
        }
        else{
            Log.d(tag, "User is not null :( Something went wrong");
        }
        Intent back = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(back);
        finish();
    }

    public void switchToFeed(){
        FragmentTransaction fragmentTransaction2 = fragmentManager.beginTransaction();
        fragmentTransaction2.replace(R.id.BigFrameBoi, fragmentFeed).commit();
    }

    public void switchToProfile() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.BigFrameBoi, fragmentProfile).commit();
    }

    public void clickedProfileSwitch(ParseUser clickedUser) {
        ProfileFragment newClickedUser = new ProfileFragment();
        newClickedUser.setParseUser(clickedUser);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.BigFrameBoi, newClickedUser).commit();
    }
//
//    public void onClickCamera(MenuItem item) {
//        /* time to launch the camera */
//        Log.d(tag, "Clicked on camera");
//    }
//
//    public void onClickFeed(MenuItem item) {
//        /* TODO: implement later with fragment */
//        Toast.makeText(getApplicationContext(), "You clicked on feed!", Toast.LENGTH_LONG).show();
//    }
}
