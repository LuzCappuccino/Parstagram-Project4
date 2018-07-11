package com.example.luzcamacho.parstagram;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luzcamacho.parstagram.model.GlideApp;
import com.example.luzcamacho.parstagram.model.Post;

import java.util.ArrayList;

/**
 * Created by luzcamacho on 7/11/18.
 */

public class InstaAdapter extends RecyclerView.Adapter<InstaAdapter.ViewHolder> {

    private static ArrayList<Post> myFeed;
    static Context context;

    public InstaAdapter(ArrayList<Post> myList) {
        this.myFeed = myList;
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
        holder.Username.setText(currPost.getUser().getUsername());
        holder.Caption.setText(currPost.getDescription());
        holder.Date.setText(currPost.getDate());
        // TODO: work with the image later
        GlideApp.with(context)
                .load(photoURL)
                .into(holder.Pic);
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

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView Pic;
        public TextView Username;
        public TextView Caption;
        public TextView Date;

        public ViewHolder(View itemView) {
            /* viewholder thaaaannnggss */
            super(itemView);
            Pic = itemView.findViewById(R.id.ivPostPic);
            Username = itemView.findViewById(R.id.tvUserInfo);
            Caption = itemView.findViewById(R.id.tvCaption);
            Date = itemView.findViewById(R.id.tvDate);
        }
    }
}
