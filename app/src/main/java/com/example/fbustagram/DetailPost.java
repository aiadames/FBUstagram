package com.example.fbustagram;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;

import org.parceler.Parcels;


public class DetailPost extends AppCompatActivity {

    private Post post;
    TextView tvDescription;
    TextView tvHandle;
    TextView tvLikes;
    TextView tvUserDescription;
    ImageView ivImage;
    ImageButton ibLike;
    ImageButton ibReply;
    ImageButton ibComment;
    ImageButton ibSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post);
        //ButterKnife.bind(DetailPost.this);

        ConstraintLayout constraintLayout = findViewById(R.id.layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        post = (Post) Parcels.unwrap(getIntent().getParcelableExtra("post"));

        tvDescription = findViewById(R.id.tvDescription);
        tvHandle = findViewById(R.id.tvHandle);
        tvLikes = findViewById(R.id.tvLikes);
        tvUserDescription = findViewById(R.id.tvUserDescription);
        ivImage = findViewById(R.id.ivImage);
        ibLike = findViewById(R.id.ibLike);
        ibReply = findViewById(R.id.ibReply);
        ibComment = findViewById(R.id.ibComment);
        ibSave = findViewById(R.id.ibSave);

        tvHandle.setText(post.getUser().getUsername());
        tvDescription.setText(post.getDescription());
        tvUserDescription.setText(post.getUser().getUsername());
        Glide.with(this).load(post.getImage().getUrl()).into(ivImage);



    }


}
