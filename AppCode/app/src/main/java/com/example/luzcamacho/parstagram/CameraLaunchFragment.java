package com.example.luzcamacho.parstagram;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;


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
    private FragmentActivity fragAct;

    public CameraLaunchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* set up our views */
        fragAct = (FragmentActivity) getActivity();
        View PicPreview = fragAct.findViewById(R.id.ivPreviewPicFrag);
        btSubmitPost = btSubmitPost.findViewById(R.id.btSubmitPost);
        etEnterCaption = etEnterCaption.findViewById(R.id.etEnterCaption);
        onLaunchCamera(PicPreview);
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
        // TODO: WHY IS THIS RED UM
        if (intent.resolveActivity(fragAct.getPackageManager()) != null) {
            /* Start the image capture intent to take photo */
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    private File getPhotoFileUri(String photoFileName) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera_launch, container, false);
    }

}
