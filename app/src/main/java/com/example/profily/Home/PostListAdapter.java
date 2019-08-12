package com.example.profily.Home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.profily.Model.Schema.Post.PostAsyncDao;
import com.example.profily.R;
import com.example.profily.Model.Schema.Post.Post;

import java.util.List;


public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostRowViewHolder> {

    private List<Post> postsList; // Cached copy of posts

    @NonNull
    @Override
    public PostRowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_row, parent, false);
        return new PostRowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostRowViewHolder holder, int position) {
        if (postsList != null) {
            holder.bind(postsList.get(position));
        } else {
            // TODO show spinner
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
        this.postsList = postsList;
        notifyDataSetChanged(); //TODO need to check exactly what this function does
    }

    static class PostRowViewHolder extends RecyclerView.ViewHolder {

        ImageView profileImage;
        ImageView mainImage;
        TextView username;
        ImageView likedImage;
        ImageView commentImage;
        TextView numOfLikes;
        TextView caption;
        TextView comments;

        public PostRowViewHolder(@NonNull View itemView) {

            super(itemView);
            profileImage = itemView.findViewById(R.id.post_profile_image);
            mainImage = itemView.findViewById(R.id.post_main_image);
            username = itemView.findViewById(R.id.post_username);
            likedImage = itemView.findViewById(R.id.post_like_image);
            commentImage = itemView.findViewById(R.id.post_comment_image);
            numOfLikes = itemView.findViewById(R.id.post_num_of_likes);
            caption = itemView.findViewById(R.id.post_caption);
            comments = itemView.findViewById(R.id.post_comments_link);


        }

        public void bind(Post post){
            PostAsyncDao.getUserNameById(post.getUserCreatorId(), name -> {
                username.setText(name);
            });
            numOfLikes.setText(post.getLikedUsersList().size() + " likes");
            caption.setText(post.getCaption());
            comments.setText("View all " + post.getCommentsList().size() + " comments");

            profileImage.setOnClickListener(
                Navigation.createNavigateOnClickListener(
                    HomeFragmentDirections.actionHomeFragmentToProfileFragment(
                            post.getUserCreatorId()
                    )
                )
            );

            username.setOnClickListener(
                Navigation.createNavigateOnClickListener(
                    HomeFragmentDirections.actionHomeFragmentToProfileFragment(
                            post.getUserCreatorId()
                    )
                )
            );

            comments.setOnClickListener(
                    Navigation.createNavigateOnClickListener(
                            HomeFragmentDirections.actionHomeFragmentToCommentsFragment(
                                    post.getPostId()
                            )
                    )
            );
        }
    }



}
