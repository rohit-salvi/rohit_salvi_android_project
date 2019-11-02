package com.salavation.carmall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.salavation.carmall.listener.PaginationPostListener;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.List;

public class FeedsActivity extends AppCompatActivity {
    RecyclerPostAdapter adapter;
    LinearLayoutManager linearLayoutManager;

    RecyclerView rv;
    ProgressBar progressBar;

    private static final int PAGE_START = 0;
    private boolean isLoading = false;
    private int TOTAL_PAGES = 5;

    PostHelper postHelper;

    int[] sampleImages = {R.mipmap.image_1, R.mipmap.image_2, R.mipmap.image_3};
    private CarouselView carouselView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeds);

        rv = findViewById(R.id.recycler_view_post);

        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);

        rv.setItemAnimator(new DefaultItemAnimator());

        rv.addOnScrollListener(new PaginationPostListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;

                // mocking network delay for API call
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNextPage();
                    }
                }, 1000);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });


        carouselView = (CarouselView) findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);

        carouselView.setImageListener(imageListener);

        findViewById(R.id.sell).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FeedsActivity.this, PostAdActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }

    private void refreshData() {
        postHelper = new PostHelper(this);

        PostDatabase db = Room.databaseBuilder(getApplicationContext(),
                PostDatabase.class, "ads_post")
                .allowMainThreadQueries()
                .build();

        List<Post> post = db.postDAO().getAll();
        post.addAll(postHelper.getNextPosts());

        adapter = new RecyclerPostAdapter(this, post);
        adapter.addLoadingFooter();

        rv.setAdapter(adapter);
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

    private void loadNextPage() {
        adapter.removeLoadingFooter();
        isLoading = false;
        List<Post> movies = postHelper.getNextPosts();
        adapter.addAll(movies);
        adapter.addLoadingFooter();
    }

}
