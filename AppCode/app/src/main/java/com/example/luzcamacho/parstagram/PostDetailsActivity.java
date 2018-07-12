package com.example.luzcamacho.parstagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luzcamacho.parstagram.model.GlideApp;
import com.example.luzcamacho.parstagram.model.Post;

import org.parceler.Parcels;


public class PostDetailsActivity extends AppCompatActivity {
    public ImageView ivDetailsPic;
    public TextView tvDetailsUsername;
    public TextView tvDetailsCaption;
    public TextView tvDetailsDate;

    Post myPost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        ivDetailsPic = findViewById(R.id.ivDetailsPic);
        tvDetailsUsername = findViewById(R.id.tvDetailsUsername);
        tvDetailsCaption = findViewById(R.id.tvDetailsCaption);
        tvDetailsDate = findViewById(R.id.tvDetailsDate);
        /* display that shit */
        myPost = Parcels.unwrap(getIntent().getParcelableExtra(Post.class.getSimpleName()));

        tvDetailsCaption.setText(myPost.getDescription());
        tvDetailsUsername.setText(myPost.getUser().getUsername());
        tvDetailsDate.setText(myPost.getDate());

        String postURL = myPost.getImage().getUrl();
        /* use glide to display */
        GlideApp.with(getApplicationContext())
                .load(postURL)
                .into(ivDetailsPic);
    }
}
