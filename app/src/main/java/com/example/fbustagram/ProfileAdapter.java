package com.example.fbustagram;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.parse.ParseFile;

import org.parceler.Parcels;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder>{

    protected Context context;
    protected List<Post> posts;

    public ProfileAdapter (Context context, List<Post> posts){
        this.context = context;
        this.posts = posts;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_profile_post, parent,false);
        return new ProfileAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }


    // ViewHolder class to bind physical values
    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivImage);
            // set the item tweets in RecyclerView to an click listener
            // get the position of the tweet in the RecyclerView and get the specific tweet object in the ArrayList mTweets
            // pass through all the details of the tweet through a new Intent to begin the DetailsTweet Activity
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   clickedPost();
                }
            });
        }


        public void bind(Post post) {
            ParseFile image = post.getImage();
            // formatting to round corners and center crop style alongside loading high res
            RequestOptions rq = new RequestOptions();
            rq = rq.transform(new CenterCrop(), new RoundedCorners(60)).format(DecodeFormat.PREFER_ARGB_8888);
            if (image != null) {
                Glide.with(context)
                        .load(image.getUrl())
                        .into(ivImage);
            }
        }

        public void clickedPost(){
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                // get the movie at the position, this won't work if the class is static
                Post post = posts.get(position);
                // create intent for the new activity
                Intent detailIntent = new Intent(context, DetailPost.class);
                detailIntent.putExtra("post", Parcels.wrap(post));
                context.startActivity(detailIntent);
            }
        }
    }

}
