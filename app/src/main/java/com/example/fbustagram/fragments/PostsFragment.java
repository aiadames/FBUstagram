package com.example.fbustagram.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.fbustagram.EndlessRecyclerViewScrollListener;
import com.example.fbustagram.Post;
import com.example.fbustagram.PostsAdapter;
import com.example.fbustagram.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class PostsFragment extends Fragment {


    private RecyclerView rvPosts ;
    protected PostsAdapter adapter;
    protected List<Post> mPosts;
    public static final String TAG = "PostsFragment";
    private SwipeRefreshLayout swipeContainer;
    private EndlessRecyclerViewScrollListener scrollListener;
    private boolean isLoading;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setUpRecyclerView();
        setUpSwipeContainer();
        queryPosts();
    }



    // HELPER METHODS:
    // for a refresh swiping up action, set on refresh listener, then clear all posts and the adapter to get ready for a newer query
    public void setUpSwipeContainer(){
        swipeContainer = getView().findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPosts.clear();
                adapter.clear();
                queryPosts();
            }
        });
        // colors of progress for swiping icon
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }


    public void setUpRecyclerView(){
        rvPosts = getView().findViewById(R.id.rvPosts);
        //create the adapter and data source
        mPosts = new ArrayList<>();
        adapter = new PostsAdapter(getContext(), mPosts);
        // set the adapter on the recycler view
        rvPosts.setAdapter(adapter);
        // set the layout manager on the recycler view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvPosts.setLayoutManager(linearLayoutManager);
        rvPosts.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {

            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                if (! isLoading){
                    queryPostsEndless();
                }
            }
        };
        rvPosts.addOnScrollListener(scrollListener);
    }


    // retrieving posts method
    protected void queryPosts(){
        // new parse query where calling for 20 posts at once, sorted by created date, and includes User values
        isLoading = true;
        ParseQuery<Post> postQuery = new ParseQuery<Post>(Post.class);
        postQuery.include(Post.KEY_USER);
        postQuery.setLimit(20);
        postQuery.addDescendingOrder(Post.KEY_CREATED_AT);
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null){
                    Log.e(TAG, "Error with query");
                    e.printStackTrace();
                    return;
                }
                // add all posts found from query into list and notify adapter
                adapter.addAll(posts);
                adapter.notifyDataSetChanged();

                if (mPosts.size() == posts.size()){
                    isLoading = false;
                }
                // set the refreshing as false to stop the loading/query call
                swipeContainer.setRefreshing(false);
            }
        });
    }

    // endless scrolling parsing and displaying method
    protected void queryPostsEndless(){
        isLoading = true;
        ParseQuery<Post> postQuery = new ParseQuery<Post>(Post.class);
        postQuery.include(Post.KEY_USER);
        postQuery.setLimit(20);
        postQuery.whereLessThan("createdAt", mPosts.get(mPosts.size() - 1).getCreatedAt());
        postQuery.addDescendingOrder(Post.KEY_CREATED_AT);
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null){
                    Log.e(TAG, "Error with query");
                    e.printStackTrace();
                    return;
                }
                adapter.addAll(posts);
                adapter.notifyDataSetChanged();
                scrollListener.resetState();
            }
        });
    }


}
