package com.example.luzcamacho.parstagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.luzcamacho.parstagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

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

        loadTopPosts();
    }

    private void createPost(String description, ParseFile file, ParseUser user){
        //TODO: create and save post
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
}
