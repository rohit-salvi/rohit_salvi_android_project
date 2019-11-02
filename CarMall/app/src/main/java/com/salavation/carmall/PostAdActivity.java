package com.salavation.carmall;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.room.Room;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PostAdActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_MULTIPLE = 2121;
    private static final int REQUEST_WRITE_PERMISSION = 21;
    private EditText edTitle, edMaker, edModel, edPrice, edLocation, edDesc;
    private Button btnImages;
    private Button btnSave;

    List<String> images = new ArrayList<String>();
    private TextView added;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // When an Image is picked
        if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK
                && null != data) {
            Uri selectedImageUri = data.getData();
            images.add(selectedImageUri.toString());

            added.setVisibility(View.VISIBLE);
            added.setText("Images added : " + images.size());
        } else
            Toast.makeText(this, "Select at least one Image.", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_ad);

        edTitle = findViewById(R.id.ad_title);
        edMaker = findViewById(R.id.car_maker);
        edModel = findViewById(R.id.car_model);
        edPrice = findViewById(R.id.car_price);
        edLocation = findViewById(R.id.car_location);
        edDesc = findViewById(R.id.desc);
        added = findViewById(R.id.added);

        btnImages = findViewById(R.id.picker);
        btnSave = findViewById(R.id.save);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInputs(edTitle.getText().toString(), edMaker.getText().toString(), edModel.getText().toString()
                        , edPrice.getText().toString(), edLocation.getText().toString())) {
                    saveAd(edTitle.getText().toString(), edMaker.getText().toString(), edModel.getText().toString()
                            , edPrice.getText().toString(), edLocation.getText().toString(), edDesc.getText().toString(), images);
                    finish();
                }
            }
        });

        btnImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (images.size() < 3) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(PostAdActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
                        } else {
                            imagePickerDialog();
                        }
                    } else {
                        imagePickerDialog();
                    }
                } else {
                    Toast.makeText(PostAdActivity.this, "Maximum 3 images can be added.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        added.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            imagePickerDialog();
    }

    private void imagePickerDialog() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(Intent.createChooser(intent, "select a picture"), PICK_IMAGE_MULTIPLE);
    }

    private void saveAd(String title, String maker, String model, String price, String loc, String desc, List<String> images) {

        Post post = new Post();
        post.title = title;
        post.make = maker;
        post.model = model;
        post.price = price;
        post.location = loc;
        post.content = desc;

        post.images = Converters.fromArrayList(images);

        PostDatabase db = Room.databaseBuilder(getApplicationContext(),
                PostDatabase.class, "ads_post")
                .allowMainThreadQueries()
                .build();

        db.postDAO().insert(post);
    }

    private boolean validateInputs(String title, String maker, String model, String price, String loc) {
        if (title.isEmpty() || maker.isEmpty() || model.isEmpty() || price.isEmpty() || loc.isEmpty()) {
            Toast.makeText(this, "Incomplete information. Please fill all data.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (images.isEmpty()) {
            Toast.makeText(this, "Please select at least one image..", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;

    }
}
