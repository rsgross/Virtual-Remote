package com.example.rsg.virtualremote;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class TakePictureActivity extends AppCompatActivity implements View.OnClickListener {

    private Button takePicture;
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageViewPhoto;
    private String pathToImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_picture);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        takePicture = (Button) findViewById(R.id.buttonTakePicture);
        takePicture.setOnClickListener(this);

        imageViewPhoto = (ImageView) findViewById(R.id.imageViewPhoto);

    }

    @Override
    public void onClick(View v) {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            //imageViewPhoto.setImageBitmap(photo);
            saveToInternalStorage(photo);

            Bitmap bmImg = BitmapFactory.decodeFile(pathToImage);
            imageViewPhoto.setImageBitmap(bmImg);
        }
    }

    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"window.jpg");
        String TAG = "Debugging";

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        File[] appFiles = directory.listFiles();
        Log.d(TAG, "saveToInternalStorage: File array length " + Integer.toString(directory.listFiles().length));
        for(File f: appFiles) {
            if(f.getName().equals("book.jpg")) {
                pathToImage = f.getAbsolutePath();
            }
            Log.d(TAG, "saveToInternalStorage: " + f.toString());
        }
        return directory.getAbsolutePath();
    }
}