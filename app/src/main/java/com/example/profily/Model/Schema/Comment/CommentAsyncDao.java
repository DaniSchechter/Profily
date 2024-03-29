package com.example.profily.Model.Schema.Comment;

import android.os.AsyncTask;
import com.example.profily.Model.Model;
import com.example.profily.Model.ModelSql;

import java.util.List;

public class CommentAsyncDao {

    public static void getAllComments(final String postId, final Model.GetAllCommentsListener listener) {
        new AsyncTask<String,Void, List<Comment>>(){

            @Override
            protected List<Comment> doInBackground(String... strings) {
                return ModelSql.getInstance().commentDao().getAllComments(postId);
            }

            @Override
            protected void onPostExecute(List<Comment> data) {
                super.onPostExecute(data);
                if (listener != null) {
                    listener.onComplete(data);
                }
            }
        }.execute();

    }

    public static void addComment(Comment comment) {
        new AsyncTask<Comment, Void, Void>(){

            @Override
            protected Void doInBackground(Comment... comment) {
                ModelSql.getInstance().commentDao().insertComment(comment[0]);
                return null;
            }
        }.execute(comment);

    }

    public static void addCommentsAndFetch(final String postId, List<Comment> commentsList, final Model.GetAllCommentsListener listener) {
        new AsyncTask<List<Comment>, Void, List<Comment>>(){

            @Override
            protected List<Comment> doInBackground(List<Comment>... comments) {
                ModelSql.getInstance().commentDao().insertComments(comments[0]);
                return ModelSql.getInstance().commentDao().getAllComments(postId);
            }

            @Override
            protected void onPostExecute(List<Comment> comments) {
                super.onPostExecute(comments);
                if (listener != null) {
                    listener.onComplete(comments);
                }
            }
        }.execute(commentsList);
    }

    public static void updateComment(Comment comment){
        new AsyncTask<Comment, Void, Void>(){

            @Override
            protected Void doInBackground(Comment... comments) {
                ModelSql.getInstance().commentDao().insertComment(comments[0]);
                return null;
            }
        }.execute(comment);
    }
}
