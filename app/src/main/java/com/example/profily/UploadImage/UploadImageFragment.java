package com.example.profily.UploadImage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.profily.Model.Model;
import com.example.profily.Model.Schema.Post.Post;
import com.example.profily.R;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class UploadImageFragment extends Fragment {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    final static int RESAULT_SUCCESS = 0;
    EditText caption;
    ImageView img;
    boolean pickedImage = false;
    Button saveBtn;
    private Bitmap imageBitmap;


    public UploadImageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_upload_image, container, false);
        caption = v.findViewById(R.id.upload_post_caption);
        img = v.findViewById(R.id.upload_post_img);
        saveBtn = v.findViewById(R.id.upload_new_picture_save_btn);


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

                if (pickedImage){
                    Post post = new Post();
                    post.setCaption(caption.getText().toString());
                    post.setUserCreatorId(Model.instance.getConnectedUserId());
                    Model.instance.addPost(post, imageBitmap, new Model.AddPostListener() {
                        @Override
                        public void onComplete(boolean success) {
                            Navigation.findNavController(getView()).popBackStack();
                        }
                    });
                }
            }
        });

//        String user_id = new_pictureArgs.fromBundle(getArguments()).getUserId();
//        TextView textView = v.findViewById(R.id.new_picture);
//        textView.setText(user_id);

        return  v;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE &&
                resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            img.setImageBitmap(imageBitmap);
            pickedImage = true;
        }
    }

}