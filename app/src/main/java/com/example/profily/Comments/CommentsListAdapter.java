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
import com.example.profily.Schema.Comment;
import com.example.profily.Utils.DateTimeUtils;

import java.util.Vector;

public class CommentsListAdapter extends RecyclerView.Adapter<CommentsListAdapter.CommentRowViewHolder> {

    private Vector<Comment> commentsList; //TODO maybe to delete

    public CommentsListAdapter(Vector<Comment> commentsList) {
        this.commentsList = commentsList;

    }//TODO maybe to delete

    @NonNull
    @Override
    public CommentRowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_row, parent, false);
        return new CommentRowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentRowViewHolder holder, int position) {
        Comment comment = commentsList.elementAt(position);
        holder.bind(comment);
    }

    @Override
    public int getItemCount() {
        return commentsList.size();
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
            actionElapsedTime.setText(DateTimeUtils.getFormattedElapsedTime(comment.getActionDateTime()));

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