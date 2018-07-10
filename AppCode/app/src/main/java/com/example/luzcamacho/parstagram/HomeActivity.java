package com.example.luzcamacho.parstagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.luzcamacho.parstagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    public static String tag = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        /* we are gonna grab the logged in users posts */
        // ensures that we are using a different thread for pulling posts
        ParseQuery.getQuery(Post.class).findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if(null == e){
                    for(int i = 0;i < objects.size(); i++){
                        Log.d(tag, "Post[" + i + "] = " + objects.get(i).getDescription());
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
