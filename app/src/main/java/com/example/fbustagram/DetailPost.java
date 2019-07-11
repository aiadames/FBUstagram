package com.example.fbustagram;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import org.parceler.Parcels;


public class DetailPost extends AppCompatActivity {

    private Post post;
    TextView tvDescription;
    TextView tvHandle;
    ImageView ivImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post);
        Post post = (Post) Parcels.unwrap(getIntent().getParcelableExtra("post"));

        ivImage = findViewById(R.id.ivImage);
        tvHandle = findViewById(R.id.ivHandle);
        tvDescription = findViewById(R.id.tvDescription);

        tvHandle.setText(post.getUser().getUsername());
        tvDescription.setText(post.getDescription());
        Glide.with(this).load(post.getImage().getUrl()).into(ivImage);



    }


}
