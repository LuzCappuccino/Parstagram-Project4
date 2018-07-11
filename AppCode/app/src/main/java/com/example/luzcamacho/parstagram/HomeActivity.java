package com.example.luzcamacho.parstagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.luzcamacho.parstagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    public static String tag = "HomeActivity";
    public RecyclerView rvPostViewer;
    public InstaAdapter instaAdapter;
    public ArrayList<Post> myPosts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        /* find our recycler view */
        rvPostViewer = findViewById(R.id.rvPostViewer);
        /* initialize everything (my post array) */
        myPosts = new ArrayList<>();
        /* construct adapter with the data source */
        instaAdapter = new InstaAdapter(myPosts);
        /* recycler view set up (layout manager) */
        rvPostViewer.setLayoutManager(new LinearLayoutManager(this ));
        /* set the adapter */
        rvPostViewer.setAdapter(instaAdapter);
        /* time to fetch and load posts into our rv */
        loadTopPosts();
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
//                        Log.d(tag, "Post[" + i + "] = " + objects.get(i).getDescription()
//                                + "\n Username = " + objects.get(i).getUser().getUsername());
                        myPosts.add(objects.get(i));
                        Log.d(tag, "In index: " + i + ". Username: " + myPosts.get(i).getUser()
                            + ". Description: " + myPosts.get(i).getDescription());
                        Log.d(tag, "Image url for index[" + i + "]. " + myPosts.get(i).getImage().getUrl());
                        instaAdapter.notifyItemInserted(myPosts.size() - 1);
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

    public void onClickCamera(MenuItem item) {
        /* time to launch the camera */
        Log.d(tag, "Clicked on camera");
        Intent goCamera = new Intent(getApplicationContext(), CameraActivity.class);
        startActivity(goCamera);
    }
}
