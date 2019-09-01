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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.profily.Camera.UploadImageActivity;
import com.example.profily.Model.Schema.Post.Post;
import com.example.profily.R;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditPostFragment extends Fragment {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private PostViewModel postViewModel;

    //Post vars
    private TextView postCaption;
    private ImageView postImage;
    private Button savePostBtn;


    public EditPostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postViewModel = ViewModelProviders.of(this).get(PostViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_post, container, false);
        postCaption = view.findViewById(R.id.edit_post_caption);

        postImage = view.findViewById(R.id.edit_post_post_image);
        savePostBtn = view.findViewById(R.id.edit_post_save_post_btn);

        String postId = null;
        if (getArguments() != null && getArguments().size()!=0)
        {
            postId = EditPostFragmentArgs.fromBundle(getArguments()).getPostId();
        }

        postImage.setOnClickListener(v -> dispatchTakePictureIntent());

        postViewModel.populatePostDetails(postId);

        postViewModel.getPost().observe(this, postData->{
            if (postData.post.getCaption() != null){
                postCaption.setText(postData.post.getCaption());
            }
            else{
                postCaption.setText("");
            }

            savePostBtn.setOnClickListener(view1 -> {
                postViewModel.updatePost(
                        new Post(postData.post.getPostId(),
                                postData.post.getUserCreatorId(),
                                postData.post.getImageURL(),
                                postCaption.getText().toString(),
                                false,
                                postData.post.getCreatedDate()
                        ));
                getFragmentManager().popBackStack();
            });
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
            postImage.setImageBitmap((Bitmap) extras.get(UploadImageActivity.IMAGE_KEY)); // TODO CHANGE TO VIEW MODEL
        }
    }

}
