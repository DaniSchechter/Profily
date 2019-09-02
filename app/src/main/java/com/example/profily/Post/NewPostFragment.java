package com.example.profily.Post;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.profily.Camera.UploadImageActivity;
import com.example.profily.R;

import static android.app.Activity.RESULT_OK;
import static com.example.profily.Post.EditPostFragment.REQUEST_IMAGE_CAPTURE;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewPostFragment extends Fragment {


    private LinearLayout resultGroup;
    private ImageView selectedImage;
    private EditText description;
    private ImageButton saveButton;

    private PostViewModel viewModel;

    public NewPostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(PostViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_post, container, false);

        resultGroup = view.findViewById(R.id.new_post_result_group);
        selectedImage = view.findViewById(R.id.new_post_selected_image);
        description = view.findViewById(R.id.new_post_description);
        saveButton = view.findViewById(R.id.new_post_save_post);

        dispatchTakePictureIntent();
        selectedImage.setOnClickListener(v-> dispatchTakePictureIntent());
        viewModel.getBitmap().observe(this, bitmap -> selectedImage.setImageBitmap(bitmap));

        saveButton.setVisibility(View.GONE);
        saveButton.setOnClickListener(v -> {
            viewModel.addPost(description.getText().toString());
            getFragmentManager().popBackStack();
        });


        return view;
    }

    private void dispatchTakePictureIntent() {
        Intent intent = new Intent(getActivity(), UploadImageActivity.class);

        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            this.startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if(data == null) {
                return;
            }
            Bundle extras = data.getExtras();
            viewModel.setBitmap((Bitmap) extras.get(UploadImageActivity.IMAGE_KEY));
            saveButton.setVisibility(View.VISIBLE);
        }
    }


}
