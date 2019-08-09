package com.example.profily.Profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.profily.R;
import com.example.profily.Model.Schema.Post.Post;

import java.util.Vector;


public class ImageGridAdapter extends RecyclerView.Adapter<ImageGridAdapter.ImageCellViewHolder> {

    private Vector<Post> postsList; //TODO maybe to delete

    public ImageGridAdapter(Vector<Post> postsList) {
        this.postsList = postsList;
    }//TODO maybe to delete

    @NonNull
    @Override
    public ImageCellViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_row, parent, false);
        return new ImageCellViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageCellViewHolder holder, int position) {
        Post post = postsList.elementAt(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }

    static class ImageCellViewHolder extends RecyclerView.ViewHolder {

        ImageView image; // TODO reference to the real picture

        public ImageCellViewHolder(@NonNull View itemView) {

            super(itemView);

            image = itemView.findViewById(R.id.profile_grid_image);
        }

        public void bind(Post post){
            // TODO user the post's image
            image.setOnClickListener(
                Navigation.createNavigateOnClickListener(
                    ProfileFragmentDirections.actionProfileFragmentToPost(
                            post.getId()
                    )
                )
            );
        }
    }
}
