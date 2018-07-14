package com.example.luzcamacho.parstagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.luzcamacho.parstagram.model.GlideApp;
import com.example.luzcamacho.parstagram.model.Post;

import org.parceler.Parcels;


public class PostDetailsActivity extends AppCompatActivity {
    public ImageView ivDetailsPic;
    public TextView tvDetailsUsername;
    public TextView tvDetailsCaption;
    public TextView tvDetailsDate;
    public TextView tvDetailsActualUsername;
    public ImageView ivDetailsProfilePics;

    Post myPost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        ivDetailsPic = findViewById(R.id.ivDetailsPic);
        tvDetailsUsername = findViewById(R.id.tvDetailsUsername);
        tvDetailsCaption = findViewById(R.id.tvDetailsCaption);
        tvDetailsDate = findViewById(R.id.tvDetailsDate);
        tvDetailsActualUsername = findViewById(R.id.tvDetailsActualUsername);
        ivDetailsProfilePics = findViewById(R.id.ivDetailsProfilePic);
        /* display that shit */
        myPost = Parcels.unwrap(getIntent().getParcelableExtra(Post.class.getSimpleName()));

        tvDetailsCaption.setText(myPost.getDescription());
        String missingAt = myPost.getUser().getString("handle");
        tvDetailsUsername.setText("@" + missingAt);
        tvDetailsDate.setText(myPost.getDate());
        String actualUsername = myPost.getUser().getUsername();
        tvDetailsActualUsername.setText(actualUsername);

        String profilePicUrl = "";
        if(myPost.getUser().getParseFile("ProfilePic") != null){
            profilePicUrl = myPost.getUser().getParseFile("ProfilePic").getUrl();
        }
        String postURL = myPost.getImage().getUrl();
        /* use glide to display */
        GlideApp.with(getApplicationContext())
                .load(postURL)
                .into(ivDetailsPic);

        GlideApp.with(getApplicationContext())
                .load(profilePicUrl)
                .transform(new CircleCrop())
                .into(ivDetailsProfilePics);
    }
}
