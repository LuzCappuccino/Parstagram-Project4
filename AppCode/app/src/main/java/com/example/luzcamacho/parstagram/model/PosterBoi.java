package com.example.luzcamacho.parstagram.model;

import android.util.Log;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import static com.parse.Parse.getApplicationContext;

/**
 * Created by luzcamacho on 7/10/18.
 */

public class PosterBoi {
    final String tag = "In PosterBoi";
    public PosterBoi() {
        // do nothing
    }

    public void publishPost(MyCallback thing){
        Log.d(tag, "In create post");
        /* try to save our encoded file */
        thing.file.saveInBackground(thing);
    }

    public class MyCallback implements SaveCallback {

        private String description;
        private ParseFile file;
        private ParseUser user;

        public MyCallback(final String description, final ParseFile file, final ParseUser user){
            this.description = description;
            this.file = file;
            this.user = user;
        }

        public void done(ParseException e) {
            if(e == null){
                Log.d(tag, "Image saved. Proceeding");
                final Post newPost = new Post();
                newPost.setDescription(description);
                newPost.setImage(file);
                newPost.setUser(user);
                    /* now saving the actual post */
                newPost.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null){
                            Toast.makeText(getApplicationContext(), "Create post success", Toast.LENGTH_LONG).show();
                            Log.d(tag, "Create post success");
                        }
                        else{
                            Log.e(tag, "Failed to save post");
                            e.printStackTrace();
                        }
                    }
                });
            }
            else{
                Log.d(tag, "Failed to save image.");
                e.printStackTrace();
            }
        }
    }
}
