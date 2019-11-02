package com.salavation.carmall;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.io.File;
import java.util.List;

public class CarDetailsActivity extends AppCompatActivity {

    private CarouselView carouselView;
    Post post;
    private TextView model, maker, price, location, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);

        carouselView = (CarouselView) findViewById(R.id.carousel_details);
        model = findViewById(R.id.model);
        maker = findViewById(R.id.maker);
        price = findViewById(R.id.price);
        location = findViewById(R.id.location);
        content = findViewById(R.id.content);

        post = (Post) getIntent().getSerializableExtra(Post.DATA_INTENT);

        if (post != null) {
            model.setText(post.model);
            maker.setText(post.make);
            price.setText(getResources().getString(R.string.rs) + " " + post.price);
            location.setText(post.location);
            content.setText(post.content);
        }

        carouselView.setPageCount(3);
        carouselView.setImageListener(imageListener);

        findViewById(R.id.relative_contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEmailDialog(post);
            }
        });
    }

    private void showEmailDialog(final Post post) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_send_email);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        final EditText editSubject = dialog.findViewById(R.id.edit_sub);
        final EditText editMessage = dialog.findViewById(R.id.edit_text);

        editSubject.setText("Enquiry about " + post.title);

        dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                composeEmail(editSubject.getText().toString(), editMessage.getText().toString(), post);
            }
        });

        dialog.show();

    }

    private void composeEmail(String subject, String message, Post post) {

        //String[] TO = {"developer@aigen.tech"};
        String[] TO = {"salvi.rohit25@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }

    }

    private void addImageToView(ImageView image, String path) {
        try {
            Uri uri = Uri.parse(path);
            image.setImageURI(uri);
        } catch (Exception e) {
            image.setImageResource(R.drawable.not_available);
        }
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            if (post != null && !post.imgUris.isEmpty())
                imageView.setImageResource(post.imgUris.get(position));
            else if (!post.images.isEmpty()) {
                List<String> images = Converters.fromString(post.images);
                try {
                    addImageToView(imageView, images.get(position));
                } catch (Exception e) {
                    imageView.setImageResource(R.drawable.not_available);
                }
            }
        }
    };
}
