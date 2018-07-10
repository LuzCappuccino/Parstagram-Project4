package com.example.luzcamacho.parstagram;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.luzcamacho.parstagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    public String imagePath = Environment.getExternalStorageDirectory() + "/Camera/IMG_20180710_114653.jpg";
    public static String tag = "HomeActivity";
    private Button btCreate;
    private Button btRefresh;
    private EditText etDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /* wire our views */
        btCreate = (Button) findViewById(R.id.btCreate);
        btRefresh = (Button) findViewById(R.id.btRefresh);
        etDescription = (EditText) findViewById(R.id.etDescription);

        btCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(tag, "click has been identified");
                final String description = etDescription.getText().toString();
                final ParseUser user = ParseUser.getCurrentUser();
                //TODO: have user select file and find file path accordingly
                final File file = new File(imagePath);
                boolean exists = file.exists();
                Log.d(tag, "Valid?:" + exists);
                boolean isDirectory = file.isDirectory();
                Log.d(tag, "Directory?:"+isDirectory);
                boolean isFile = file.isFile();
                Log.d(tag, "File?:"+isFile);
                final ParseFile parseFile = new ParseFile(file);
                parseFile.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null){
                            Log.d(tag,"saved image");
                            // create post
                            Log.d(tag, "going into createPost");
                            createPost(description, parseFile, user);
                        }
                        else{
                            e.printStackTrace();
                        }
                    }
                });

            }
        });

        btRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadTopPosts();
            }
        });
    }

    private void createPost(String description, ParseFile file, ParseUser user){
        Log.d(tag, "In create post");
        final Post newPost = new Post();
        newPost.setDescription(description);
        newPost.setImage(file);
        newPost.setUser(user);
        /* time to push onto the server */
        newPost.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){ //meaning there is no error
                    Toast.makeText(getApplicationContext(), "Create post success", Toast.LENGTH_LONG).show();
                    Log.d(tag, "Create post success");
                }
                else{
                    Log.e(tag, "Failed to save post");
                    e.printStackTrace();
                }
            }
        });
    }

    public void loadTopPosts() {
        final Post.Query PostQuery = new Post.Query();
        /* get top posts with the user */
        PostQuery.getTop().withUser();
        // ensures that we are using a different thread for pulling posts
        PostQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if(null == e){
                    for(int i = 0;i < objects.size(); i++){
                        Log.d(tag, "Post[" + i + "] = " + objects.get(i).getDescription()
                                + "\n Username = " + objects.get(i).getUser().getUsername());
                    }
                }
                else{
                    Log.d(tag, "failed to fetch posts");
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.camera, menu);
        return true;
    }

    public void onClickLogout(MenuItem item) {
        /* Logging out the User */
        Log.d(tag, "Logging out the user");
        ParseUser.logOut();
        ParseUser currentUser = ParseUser.getCurrentUser();
        if(currentUser == null){
            Log.d(tag, "User is now null. Logout successful.");
        }
        else{
            Log.d(tag, "User is not null :( Something went wrong");
        }
        Intent back = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(back);
        finish();
    }
}
