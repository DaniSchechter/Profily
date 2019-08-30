package com.example.profily.Camera;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.profily.R;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class UploadImageFragment extends Fragment {

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final String UPLOAD_IMAGE_INTENT = "upload image intent";
    private boolean imageTaken;

    private UploadImageViewModel viewModel;

    private LinearLayout operationsGroup;
    private TextView cameraUploadImage;
    private TextView galleryUploadImage;

    private LinearLayout resultGroup;
    private ImageView selectedImage;
    private EditText description;


    public UploadImageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(UploadImageViewModel.class);
        imageTaken = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload_image, container, false);
        operationsGroup = view.findViewById(R.id.upload_image_operations_group);
        cameraUploadImage = view.findViewById(R.id.upload_image_camera);
        galleryUploadImage = view.findViewById(R.id.upload_image_gallery);

        resultGroup = view.findViewById(R.id.upload_image_result_group);
        selectedImage = view.findViewById(R.id.upload_image_selected_image);
        description = view.findViewById(R.id.upload_image_description);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (imageTaken) {
            // Display the correct view - Selected image if selected or options to upload a new image
            resultGroup.setVisibility(View.VISIBLE);
            operationsGroup.setVisibility(View.GONE);
            selectedImage.setImageBitmap(viewModel.getImageBitmap());
        } else {
            resultGroup.setVisibility(View.GONE);
            operationsGroup.setVisibility(View.VISIBLE);
            cameraUploadImage.setOnClickListener(view1 -> dispatchTakePictureIntent());
        }
    }

    private void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(UPLOAD_IMAGE_INTENT, true);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            this.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if(data == null) {
                return;
            }
            Bundle extras = data.getExtras();
            viewModel.setImageBitmap((Bitmap)extras.get("data"));
            imageTaken = true;
        }
    }

}
