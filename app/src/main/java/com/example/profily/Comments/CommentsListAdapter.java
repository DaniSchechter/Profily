package com.example.profily.Comments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.profily.R;
import com.example.profily.Model.Schema.Comment.Comment;
import com.example.profily.Utils.DateTimeUtils;

import java.util.LinkedList;
import java.util.List;

public class CommentsListAdapter extends RecyclerView.Adapter<CommentsListAdapter.CommentRowViewHolder> {

    private List<Comment> commentsList = new LinkedList<>();

    @NonNull
    @Override
    public CommentRowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_row, parent, false);
        return new CommentRowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentRowViewHolder holder, int position) {
        Comment comment = commentsList.get(position);
        holder.bind(comment);
    }

    @Override
    public int getItemCount() {
        return commentsList.size();
    }

    void setComments(List<Comment> commentsList){
        this.commentsList = commentsList;
        notifyDataSetChanged(); //TODO need to check exactly what this function does
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

        public void bind(Comment comment) {

            commentatorUsername.setText("username " + comment.getUserId()); // TODO change
            commentDescription.setText(comment.getContent());
            actionElapsedTime.setText(DateTimeUtils.getFormattedElapsedTime(comment.getCreatedDate()));

            commentatorUsername.setOnClickListener(
                Navigation.createNavigateOnClickListener(
                    CommentsFragmentDirections.actionCommentsFragmentToProfileFragment(
                        comment.getUserId()
                    )
                )
            );

            commentatorImage.setOnClickListener(
                Navigation.createNavigateOnClickListener(
                    CommentsFragmentDirections.actionCommentsFragmentToProfileFragment(
                            comment.getUserId()
                    )
                )
            );
        }
    }
}