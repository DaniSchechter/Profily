package com.example.profily.Camera;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.widget.TextView;

import com.example.profily.R;



/**
 * A simple {@link Fragment} subclass.
 */
public class UploadImageActivity extends AppCompatActivity {

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final String IMAGE_KEY = "upload image intent";

    private TextView cameraUploadImage;


    public UploadImageActivity() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        cameraUploadImage = findViewById(R.id.upload_image_camera);
    }

    @Override
    public void onResume() {
        super.onResume();
        cameraUploadImage.setOnClickListener(view1 -> dispatchTakePictureIntent());
    }

    private void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            this.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if(data == null) {
                return;
            }
            Bundle extras = data.getExtras();
            Intent result = new Intent();
            result.putExtra(IMAGE_KEY, (Bitmap)extras.get("data"));
            setResult(RESULT_OK, result);
            finish();
        }
    }

}
