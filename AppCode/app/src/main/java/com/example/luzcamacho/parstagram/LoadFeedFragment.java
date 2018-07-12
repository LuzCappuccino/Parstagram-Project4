package com.example.luzcamacho.parstagram;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.luzcamacho.parstagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

public class LoadFeedFragment extends Fragment {
    /* our recycler view */
    public RecyclerView rvPostViewer;
    /* our data set */
    private ArrayList<Post> myFeed;
    /* our adapter */
    private InstaAdapter instaAdapter;
    /* our standard tag */
    private static String tag = "LoadFeedFragment";
    /* will be used in place of context */
    private FragmentActivity fragAct;

    public LoadFeedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* set up our  "context" */
        fragAct = (FragmentActivity) getActivity();
        /* set up our recycler view */
        rvPostViewer = rvPostViewer.findViewById(R.id.rvFeed);
        /* init data set */
        myFeed = new ArrayList<>();
        /* construct adapter with data set */
        instaAdapter = new InstaAdapter(myFeed);
        /* recycler view set up (layout manager) */
        rvPostViewer.setLayoutManager(new LinearLayoutManager(fragAct));
        /* set the adapter */
        rvPostViewer.setAdapter(instaAdapter);
        /* time to fetch and load posts into our rv */
        loadTopPosts();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_load_feed, container, false);
    }

    public void loadTopPosts() {
        /* call a query */
        final Post.Query PostQuery = new Post.Query();
        /* get top posts with the user */
        PostQuery.getTop().withUser();
        /* ensures that we ar eusing a different thread for pulling posts */
        PostQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if(e == null){
                    /* if the error returns null */
                    for(int i = 0; i < objects.size(); i++){
                        myFeed.add(objects.get(i));
                        instaAdapter.notifyItemInserted(myFeed.size() - 1);
                        Log.d(tag, "In index: " + i + ". Username: " + myFeed.get(i).getUser().getUsername() +
                            ". Description: " + myFeed.get(i).getDescription()+ ". Image URL: " +
                            myFeed.get(i).getImage().getUrl());
                    }
                }
                else{
                    /* if we have an error */
                    Log.d(tag, "Failed to fetch posts");
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
