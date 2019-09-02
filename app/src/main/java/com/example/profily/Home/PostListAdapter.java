package com.example.profily.Home;

import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.profily.Model.Schema.Post.PostLikeWrapper;
import com.example.profily.Post.PostViewModel;
import com.example.profily.R;

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
        if(postsList != null && postsList.size() >0 ) {
            this.postsList = postsList;
            notifyDataSetChanged(); //TODO need to check exactly what this function does
        }
    }

    static class PostRowViewHolder extends RecyclerView.ViewHolder {

        ImageView profileImage;
        ImageView mainImage;
        ImageView editPostBtn;
        ImageView deletePostBtn;
        ImageView likedImage;
        TextView username;
        TextView numOfLikes;
        TextView caption;
        TextView comments;


        final String cRedImageTag = "red_image_tag";
        final String cWhiteImageTag = "white_image_tag";

        public PostRowViewHolder(@NonNull View itemView) {

            super(itemView);
            profileImage = itemView.findViewById(R.id.post_profile_image);
            mainImage = itemView.findViewById(R.id.post_main_image);
            username = itemView.findViewById(R.id.post_username);
            likedImage = itemView.findViewById(R.id.post_like_image);
            numOfLikes = itemView.findViewById(R.id.post_num_of_likes);
            caption = itemView.findViewById(R.id.post_caption);
            comments = itemView.findViewById(R.id.post_comments_link);
            editPostBtn = itemView.findViewById(R.id.post_edit_post_btn);
            deletePostBtn = itemView.findViewById(R.id.post_delete_post_btn);

        }

        public void bind(PostLikeWrapper post){
            if (post.getUser() == null){
                return;
            }
            username.setText(post.getUser().getUsername());
            numOfLikes.setText("" + post.getNumOfLikes() + " likes");
            caption.setText(post.post.getCaption());
            comments.setText("View comments");
            if(!post.getUser().getProfileImageURL().isEmpty()){
                Glide.with(profileImage.getContext()).load(post.getUser().getProfileImageURL()).into(profileImage);
            } else {
                profileImage.setImageResource(R.drawable.profile);
            }

            Glide.with(mainImage.getContext()).load(post.post.getImageURL()).into(mainImage);


            if (HomeViewModel.checkEdit(post.post.getUserCreatorId())){
                editPostBtn.setVisibility(View.VISIBLE);
                deletePostBtn.setVisibility(View.VISIBLE);
            }
            else{
                editPostBtn.setVisibility(View.INVISIBLE);
                deletePostBtn.setVisibility(View.INVISIBLE);
            }

            if(post.likeIdForCurrentUser() == null)
            {
                likedImage.setImageResource(R.drawable.ic_heart_white);
                likedImage.setTag(cWhiteImageTag);
            } else if( ! String.valueOf(likedImage.getTag()).equals(cRedImageTag)){
                likedImage.setImageResource(R.drawable.ic_heart_red);
                likedImage.setTag(cRedImageTag);
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

            likedImage.setOnClickListener(likedImage -> HomeViewModel.likeToggle(post));

            editPostBtn.setOnClickListener(
                    Navigation.createNavigateOnClickListener(
                            HomeFragmentDirections.actionHomeFragmentToEditPostFragment(
                                    post.post.getPostId()
                            )
                    )
            );

            deletePostBtn.setOnClickListener(viewOnClick -> {
                post.post.setWasDeleted(true);
                PostViewModel.updatePost(post.post, null);
            });
        }
    }



}
