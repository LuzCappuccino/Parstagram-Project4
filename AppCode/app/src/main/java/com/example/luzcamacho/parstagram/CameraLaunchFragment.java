package com.example.luzcamacho.parstagram;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.luzcamacho.parstagram.model.Post;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class CameraLaunchFragment extends Fragment {

    public final String APP_TAG = "CameraFragment";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";
    File photoFile;
    /* loading views */
    public Button btSubmitPost;
    public EditText etEnterCaption;
    public ImageButton btSetProfile;
    /* used as our context    n l,ll,e*/
    private HomeActivity fragAct;

    public CameraLaunchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(APP_TAG, "Starting my camera fragment");
        fragAct = (HomeActivity) getActivity();
        View PicPreview = fragAct.findViewById(R.id.BigFragView);
        btSubmitPost = fragAct.findViewById(R.id.btSubmitPost);
        etEnterCaption = fragAct.findViewById(R.id.etEnterCaption);
        btSetProfile = fragAct.findViewById(R.id.btSetProfilePic);
        onLaunchCamera(PicPreview);
        /* setting on click listener */
        btSubmitPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitPost();
            }

        });
        btSetProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setProfilePic();
            }
        });

    }

    private void onLaunchCamera(View picPreview) {
        /* create Intent to take a picture and return control to the calling application */
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        /* Create a File reference to access to future access */
        photoFile = getPhotoFileUri(photoFileName);
        /* wrap File object into a content provider
         * required for API >= 24
         * See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-o
         * */
        Uri fileProvider = FileProvider.getUriForFile(getContext(), "com.example.luzcamacho.parstagram", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);
        /* If you call startActivityForResult() using an intent that no app can handle, your app will crash.
         * So as long as the result is not null, it's safe to use the intent.
         * */
        if (intent.resolveActivity(fragAct.getPackageManager()) != null) {
            /* Start the image capture intent to take photo */
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
        /* done */
    }

    private File getPhotoFileUri(String FileName) {
        /* Get safe storage directory for photos
         * Use `getExternalFilesDir` on Context to access package-specific directories.
         * This way, we don't need to request external read/write runtime permissions. */
        File mediaStorageDir = new File(fragAct.getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

        /* Create the storage directory if it does not exist */
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d(APP_TAG, "failed to create directory");
        }
        /* Return the file target for the photo based on filename */
        File file = new File(mediaStorageDir.getPath() + File.separator + FileName);
        return file;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE){
            if (resultCode == RESULT_OK){
                /* by this point we have the camera photo on disk */
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                /* RESIZE BITMAP, see section below
                 * Load the taken image into a preview */
                ImageView ivPreview = fragAct.findViewById(R.id.ivPreviewPicFrag);
                ivPreview.setImageBitmap(takenImage);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera_launch, container, false);
    }

    public void submitPost(){
        final String description = etEnterCaption.getText().toString();
        Log.d(APP_TAG, "Description: " + description);
        final ParseFile file = new ParseFile(getPhotoFileUri(photoFileName));
        final ParseUser user = ParseUser.getCurrentUser();
        Log.d(APP_TAG, "Username: " + user.getUsername());
        /* time to create our Poster boi */
        file.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    Post newPost = new Post();
                    newPost.setUser(user);
                    newPost.setImage(file);
                    newPost.setDescription(description);
                    newPost.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e == null){
                                // wait for submission, go to feed
                                Log.d(APP_TAG, "submission done");
                                fragAct.switchToFeed();
                            }
                            else{
                                // post toast, still go to feed
                                e.printStackTrace();
//                                fragAct.switchToFeed();
                            }
                        }
                    });
                } else{
                    /* if the image fails to encode */
                    e.printStackTrace();
                }
            }
        });
    }


    private void setProfilePic() {
        Log.d(APP_TAG, "Changing our profile pic");
        final ParseFile file = new ParseFile(getPhotoFileUri(photoFileName));
        final ParseUser user = ParseUser.getCurrentUser();

        file.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    user.put("ProfilePic", file);
                    user.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e == null){
                                Toast.makeText(fragAct, "Profile pic updated", Toast.LENGTH_LONG).show();
                                fragAct.switchToProfile();
                            }
                            else{
                                Log.d(APP_TAG, "User has been updated");
                            }
                        }
                    });
                }
                else{
                    Log.d(APP_TAG, "Failed to save profile pic");
                    e.printStackTrace();
                }
            }
        });
    }

}
