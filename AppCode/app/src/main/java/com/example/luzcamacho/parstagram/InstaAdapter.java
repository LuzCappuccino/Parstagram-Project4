package com.example.luzcamacho.parstagram;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.luzcamacho.parstagram.model.GlideApp;
import com.example.luzcamacho.parstagram.model.Post;

import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * Created by luzcamacho on 7/11/18.
 */

public class InstaAdapter extends RecyclerView.Adapter<InstaAdapter.ViewHolder> {

    private static ArrayList<Post> myFeed;
    static Context context;
    private HomeActivity myRights;

    public InstaAdapter(ArrayList<Post> myList, HomeActivity myRights) {
        this.myFeed = myList;
        this.myRights = myRights;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        /* wire up our view */
        View postView = inflater.inflate(R.layout.insta_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(postView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post currPost = myFeed.get(position);
        /* set in the holder */
        /* double check this cast */
        String photoURL = currPost.getImage().getUrl();
        String profilePicUrl = "";
        if(currPost.getUser().getParseFile("ProfilePic") != null){
            profilePicUrl = currPost.getUser().getParseFile("ProfilePic").getUrl();
        }

        String missingAt = currPost.getUser().getString("handle");
        String actualUsername = currPost.getUser().getUsername();
        holder.Username.setText("@" + missingAt);
        holder.Caption.setText(currPost.getDescription());
        holder.Date.setText(currPost.getDate());
        GlideApp.with(context)
                .load(photoURL)
                .into(holder.Pic);
        GlideApp.with(context)
                .load(profilePicUrl)
                .transform(new CircleCrop())
                .into(holder.ProfilePic);
        holder.ActualUsername.setText(actualUsername);
    }

    @Override
    public int getItemCount() {
        return myFeed.size();
    }

    public void clear() {
        myFeed.clear();
        notifyDataSetChanged();
    }

    public void addAll(ArrayList<Post> newFeed) {
        this.myFeed.addAll(newFeed);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView Pic;
        public TextView Username;
        public TextView Caption;
        public TextView Date;
        public TextView ActualUsername;
        public ImageView ProfilePic;

        public ViewHolder(View itemView) {
            /* viewholder thaaaannnggss */
            super(itemView);
            Pic = itemView.findViewById(R.id.ivPostPic);
            Username = itemView.findViewById(R.id.tvUserInfo);
            Caption = itemView.findViewById(R.id.tvCaption);
            Date = itemView.findViewById(R.id.tvDate);
            ActualUsername = itemView.findViewById(R.id.tvActuallyUsername);
            ProfilePic = itemView.findViewById(R.id.ivTimeProfilePic);

            itemView.setOnClickListener(this);

            ProfilePic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    doThings();
                }
            });
        }

        private void doThings() {
            int position = getAdapterPosition();
            if(position != RecyclerView.NO_POSITION){
                Post post = myFeed.get(position);
                /* init the new intent */
                myRights.clickedProfileSwitch(post.getUser());
            }
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if(position != RecyclerView.NO_POSITION){
                Post post = myFeed.get(position);
                /* initialize the new intent */
                Intent intent = new Intent(context, PostDetailsActivity.class);
                intent.putExtra(Post.class.getSimpleName(), Parcels.wrap(post));
                /* start activity with created intent */
                context.startActivity(intent);
            }
        }


    }




}
