package com.example.fbustagram;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder>{

    private Context context;
    private List<Post> posts;

    public PostsAdapter(Context context, List<Post> posts){
        this.context = context;
        this.posts = posts;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent,false);
        return new ViewHolder(view);
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

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvHandle;
        private ImageView ivImage;
        private TextView tvDescription;
        private ImageView ivProfile;
        private TextView tvHandleDescription;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHandle = itemView.findViewById(R.id.tvHandle);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            ivProfile = itemView.findViewById(R.id.ivProfile);
            tvHandleDescription = itemView.findViewById(R.id.tvHandleDescription);

            // set the item tweets in RecyclerView to an click listener
            // get the position of the tweet in the RecyclerView and get the specific tweet object in the ArrayList mTweets
            // pass through all the details of the tweet through a new Intent to begin the DetailsTweet Activity
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, "clicked on tweet now time for detail", Toast.LENGTH_SHORT).show();
                    int position = getAdapterPosition();
                    // make sure the position is valid, i.e. actually exists in the view
                    if (position != RecyclerView.NO_POSITION) {
                        // get the movie at the position, this won't work if the class is static
                        Post post = posts.get(position);
                        // create intent for the new activity
                        Intent detailIntent = new Intent(context, DetailPost.class);
                        detailIntent.putExtra("post",  Parcels.wrap(post));
                        context.startActivity(detailIntent);
                    }
                }
            });
        }

        public void bind(Post post) {
            tvHandle.setText(post.getUser().getUsername());
            tvHandleDescription.setText(post.getUser().getUsername());
            ParseFile image = post.getImage();
            ParseFile profileImage = post.getProfilePicture();

            RequestOptions rq = new RequestOptions();
            rq = rq.transform(new CenterCrop(), new RoundedCorners(60)).format(DecodeFormat.PREFER_ARGB_8888);

            if (image != null) {
                Glide.with(context)
                        .load(image.getUrl())
                        .into(ivImage);
            }
            if (profileImage != null){
                Glide.with(context)
                        .load(profileImage.getUrl())
                        .apply(rq)
                        .into(ivProfile);
            }

            tvDescription.setText(post.getDescription());
        }



    }


}
