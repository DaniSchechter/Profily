package com.example.profily.Profile;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.profily.R;
import com.example.profily.Model.Schema.Post.Post;

import java.util.List;

public class ImageGridAdapter extends RecyclerView.Adapter<ImageGridAdapter.ImageCellViewHolder> {

    private List<Post> postsList;

    @NonNull
    @Override
    public ImageCellViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_row, parent, false);
        return new ImageCellViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageCellViewHolder holder, int position) {
        if (postsList != null) {
            holder.bind(postsList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if (postsList != null) {
            return postsList.size();
        }
        return 0;
    }

    void setPosts(List<Post> postsList){
        if(postsList != null && postsList.size() >0 ) {
            this.postsList = postsList;
            notifyDataSetChanged();
        }
    }

    static class ImageCellViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        ProgressBar progressBar;

        public ImageCellViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.profile_grid_image);
            progressBar = itemView.findViewById(R.id.profile_grid_progress_bar);
        }

        public void bind(Post post){
            Glide.with(image.getContext()).load(post.getImageURL())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            progressBar.setVisibility(View.INVISIBLE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressBar.setVisibility(View.INVISIBLE);
                            return false;
                        }
                    }).into(image);

            image.setOnClickListener(
                Navigation.createNavigateOnClickListener(
                    ProfileFragmentDirections.actionProfileFragmentToPost(
                            post.getPostId()
                    )
                )
            );
        }
    }
}
