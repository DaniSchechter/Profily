package com.example.profily.Home;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.profily.Model.Schema.Post.PostAsyncDao;
import com.example.profily.Model.Schema.Post.PostLikeWrapper;
import com.example.profily.R;
import com.example.profily.Model.Schema.Post.Post;

import java.util.List;


public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostRowViewHolder> {

    private List<PostLikeWrapper> postsList; // Cached copy of posts

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

    void setPosts(List<PostLikeWrapper> postsList){
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

        public void bind(PostLikeWrapper post){
            PostAsyncDao.getUserNameById(post.post.getUserCreatorId(), name -> {
                username.setText(name);
            });
            numOfLikes.setText("3"); // TODO CHANGE TO A REAL NUMBER
            caption.setText(post.post.getCaption());
            comments.setText("View all 9999 comments");// TODO CHANGE TO A REAL NUMBER

            if(post.likeIdForCurrentUser() == null)
            {
                likedImage.setImageResource(R.drawable.ic_heart_white);
            } else {
                likedImage.setImageResource(R.drawable.ic_heart_red);
            }

            profileImage.setOnClickListener(
                Navigation.createNavigateOnClickListener(
                    HomeFragmentDirections.actionHomeFragmentToProfileFragment(
                            post.post.getUserCreatorId()
                    )
                )
            );

            username.setOnClickListener(
                Navigation.createNavigateOnClickListener(
                    HomeFragmentDirections.actionHomeFragmentToProfileFragment(
                            post.post.getUserCreatorId()
                    )
                )
            );

            comments.setOnClickListener(
                Navigation.createNavigateOnClickListener(
                        HomeFragmentDirections.actionHomeFragmentToCommentsFragment(
                                post.post.getPostId()
                        )
                )
            );

            likedImage.setOnClickListener(view -> {
                Log.d("TAG", "liked");
            });


        }
    }



}
