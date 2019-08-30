package com.example.profily.Camera;

import android.graphics.Bitmap;

import androidx.lifecycle.ViewModel;

public class UploadImageViewModel extends ViewModel {
    private Bitmap imageBitmap;

    public Bitmap getImageBitmap() {
        return this.imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap.copy(imageBitmap.getConfig(), imageBitmap.isMutable());
    }
}
