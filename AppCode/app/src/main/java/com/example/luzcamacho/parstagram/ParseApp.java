package com.example.luzcamacho.parstagram;

import android.app.Application;

import com.example.luzcamacho.parstagram.model.Post;
import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by luzcamacho on 7/9/18.
 */

public class ParseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        /* register the subcless */
        ParseObject.registerSubclass(Post.class);

        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.app_key))
                .clientKey(getString(R.string.master_key))
                .server(getString(R.string.server_name))
                .build();

        Parse.initialize(configuration);
    }
}
