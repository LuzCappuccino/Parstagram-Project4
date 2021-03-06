package com.example.luzcamacho.parstagram;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.luzcamacho.parstagram.model.GlideApp;
import com.parse.ParseUser;


public class ProfileFragment extends Fragment {

    public String tag = "ProfileFragment";

    public ImageView ivProfilePic;
    public TextView tvProfileUsername;
    public TextView tvProfileHandle;
    /* act as our kinda context */
    private FragmentActivity fragAct;
    private ParseUser finalUser = null;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(tag,"In Profile Fragment");
        /* set up "activity" */
        fragAct = (FragmentActivity) getActivity();
        ivProfilePic = fragAct.findViewById(R.id.ivActualProfilePic);
        tvProfileUsername = fragAct.findViewById(R.id.tvProfileUsername);
        tvProfileHandle = fragAct.findViewById(R.id.tvProfileHandle);
        if(finalUser == null) finalUser = ParseUser.getCurrentUser();
        populateProfile(finalUser);
    }

    public void populateProfile(ParseUser user) {
        String profilePicUrl = "";
        if(user.getParseFile("ProfilePic") != null){
            profilePicUrl = user.getParseFile("ProfilePic").getUrl();
        }
        String Username = user.getUsername();
        String Handle = user.getString("handle");

        tvProfileUsername.setText(Username);
        tvProfileHandle.setText("@" + Handle);

        GlideApp.with(fragAct)
                .load(profilePicUrl)
                .transform(new CircleCrop())
                .into(ivProfilePic);

    }

    public void setParseUser(ParseUser user){
        this.finalUser = user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
}
