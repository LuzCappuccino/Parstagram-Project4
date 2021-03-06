package com.example.luzcamacho.parstagram.model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Created by luzcamacho on 7/9/18.
 */

@ParseClassName("Post")
public class Post extends ParseObject {
    /* refrences to our columns on our parse dashboard */
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_USER = "user";
    private static final String KEY_DATE = "updatedAt";

    /* returns description for the post */
    public String getDescription() {
        return getString(KEY_DESCRIPTION);
    }

    public void setDescription(String description){
        put(KEY_DESCRIPTION, description);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile image) {
        put(KEY_IMAGE, image);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user){
        put(KEY_USER, user);
    }

    public String getDate() { return ParseRelativeDate.getRelativeTimeAgo(getUpdatedAt().toString()); }

    public static class Query extends ParseQuery<Post>
    {
        public Query(){
            super(Post.class);
        }

        public Query getTop() {
            orderByDescending("createdAt");
            setLimit(20);
            /* the builder pattern: allows user to chain methods */
            return this;
        }

        public Query withUser() {
            include("user");
            return this;
        }
    }
}
