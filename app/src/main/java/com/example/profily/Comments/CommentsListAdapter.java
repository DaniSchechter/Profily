package com.example.profily.Comments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.profily.MainActivity;
import com.example.profily.Model.Schema.Comment.CommentWrapper;
import com.example.profily.R;
import com.example.profily.Utils.DateTimeUtils;

import java.util.LinkedList;
import java.util.List;

public class CommentsListAdapter extends RecyclerView.Adapter<CommentsListAdapter.CommentRowViewHolder> {

    private List<CommentWrapper> commentsList = new LinkedList<>();

    @NonNull
    @Override
    public CommentRowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_row, parent, false);
        return new CommentRowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentRowViewHolder holder, int position) {
        CommentWrapper comment = commentsList.get(position);
        holder.bind(comment);
    }

    @Override
    public int getItemCount() {
        return commentsList.size();
    }

    void setComments(List<CommentWrapper> commentsList){
        this.commentsList = commentsList;
        notifyDataSetChanged(); //TODO need to check exactly what this function does
    }

    public void deleteItem(int position) {
        CommentWrapper comment = this.commentsList.get(position);
        if (CommentsViewModel.checkDelete(comment)) {
            this.commentsList.remove(position);
            CommentsViewModel.deleteItem(comment);
        }
        else{
            CharSequence text = "Cannot delete post";
            Toast.makeText(MainActivity.context, text, Toast.LENGTH_SHORT).show();
        }
        notifyDataSetChanged();

    }


    static class CommentRowViewHolder extends RecyclerView.ViewHolder {

        ImageView commentatorImage;
        TextView commentatorUsername;
        TextView commentDescription;
        TextView actionElapsedTime;

        public CommentRowViewHolder(@NonNull View itemView) {

            super(itemView);

            commentatorImage = itemView.findViewById(R.id.comment_commentator_image);
            commentatorUsername = itemView.findViewById(R.id.comment_commentator_username);
            commentDescription = itemView.findViewById(R.id.comment_description);
            actionElapsedTime = itemView.findViewById(R.id.comment_elapsed_time);

        }

        public void bind(CommentWrapper comment) {
            if (comment.getUser() == null) {
                return;
            }
            commentatorUsername.setText(comment.getUser().getUsername()); // TODO change
            commentDescription.setText(comment.comment.getContent());
            actionElapsedTime.setText(DateTimeUtils.getFormattedElapsedTime(comment.comment.getCreatedDate()));

            if(!comment.getUser().getProfileImageURL().isEmpty()){
                Glide.with(commentatorImage.getContext()).load(comment.getUser().getProfileImageURL()).into(commentatorImage);
            } else {
                commentatorImage.setImageResource(R.drawable.profile);
            }

            commentatorUsername.setOnClickListener(
                Navigation.createNavigateOnClickListener(
                    CommentsFragmentDirections.actionCommentsFragmentToProfileFragment(
                        comment.comment.getUserId()
                    )
                )
            );

            commentatorImage.setOnClickListener(
                Navigation.createNavigateOnClickListener(
                    CommentsFragmentDirections.actionCommentsFragmentToProfileFragment(
                            comment.comment.getUserId()
                    )
                )
            );
        }
    }
}