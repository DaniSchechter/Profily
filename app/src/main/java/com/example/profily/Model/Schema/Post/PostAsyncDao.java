package com.example.profily.Model.Schema.Post;

import android.os.AsyncTask;
import android.util.Log;

import com.example.profily.Model.Model;
import com.example.profily.Model.ModelSql;

import java.util.Arrays;
import java.util.List;

public class PostAsyncDao{

    public static void getAllPosts(final Model.GetAllPostsListener listener) {
        new AsyncTask<String,Void,List<Post>>(){

            @Override
            protected List<Post> doInBackground(String... strings) {
                return ModelSql.getInstance().postDao().getAllPosts();
            }

            @Override
            protected void onPostExecute(List<Post> data) {
                super.onPostExecute(data);
                if (listener != null) {
                    listener.onComplete(data);
                }
            }
        }.execute();

    }

    public static void addPosts(List<Post> postsList) {
        new AsyncTask<List<Post>, Void, Void>(){

            @Override
            protected Void doInBackground(List<Post>... posts) {
                ModelSql.getInstance().postDao().insertPosts(posts[0]);
                return null;
            }
        }.execute(postsList);

    }

    public static void addPostsAndFetch(List<Post> postsList, final Model.GetAllPostsListener listener) {
        new AsyncTask<List<Post>, Void, List<Post>>(){

            @Override
            protected List<Post> doInBackground(List<Post>... posts) {
                ModelSql.getInstance().postDao().insertPosts(posts[0]);
                return ModelSql.getInstance().postDao().getAllPosts();
            }

            @Override
            protected void onPostExecute(List<Post> posts) {
                super.onPostExecute(posts);
                if (listener != null) {
                    listener.onComplete(posts);
                }
            }
        }.execute(postsList);


    }
}
