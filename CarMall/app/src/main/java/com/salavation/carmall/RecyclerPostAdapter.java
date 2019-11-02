package com.salavation.carmall;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RecyclerPostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private Context context;

    private boolean isLoadingAdded = false;
    List<Post> postList = new ArrayList<>();

    public RecyclerPostAdapter(Context context, List<Post> posts) {
        this.context = context;
        postList = posts;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.loader_view, parent, false);
                viewHolder = new RecyclerPostAdapter.LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.post_item, parent, false);
        viewHolder = new PostViewHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case ITEM:
                PostViewHolder viewHolder = (PostViewHolder) holder;
                final Post post = postList.get(position);
                if (post != null) {
                    viewHolder.title.setText(post.title);
                    viewHolder.content.setText(post.content);
                    viewHolder.location.setText(post.location);
                    viewHolder.price.setText(context.getResources().getString(R.string.rs) + " " + post.price);

                    if (!post.imgUris.isEmpty())
                        viewHolder.image.setImageResource(post.imgUris.get(0));
                    else if (!post.images.isEmpty()) {
                        List<String> image = Converters.fromString(post.images);
                        addImageToView(viewHolder.image, image.get(0));
                    }

                    viewHolder.parent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent detailsIntent = new Intent(context, CarDetailsActivity.class);
                            detailsIntent.putExtra(Post.DATA_INTENT, post);
                            context.startActivity(detailsIntent);
                        }
                    });
                }
                break;
            case LOADING:
                break;
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

    @Override
    public int getItemCount() {
        return postList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == postList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public void add(Post po) {
        postList.add(po);
        notifyItemInserted(postList.size() - 1);
    }

    public void addAll(List<Post> mcList) {
        for (Post mc : mcList) {
            add(mc);
        }
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Post());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = postList.size() - 1;
        Post item = postList.get(position);

        if (item != null) {
            postList.remove(position);
            notifyItemRemoved(position);
        }
    }


    /**
     * Main list's content ViewHolder
     */
    protected class PostViewHolder extends RecyclerView.ViewHolder {
        private final TextView title, location;
        private final TextView content;
        private final TextView price;
        private final ImageView image;
        private final View parent;

        public PostViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            content = itemView.findViewById(R.id.content);
            image = itemView.findViewById(R.id.action_image);
            location = itemView.findViewById(R.id.location);

            parent = itemView.findViewById(R.id.parent);

        }
    }


    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }

}
