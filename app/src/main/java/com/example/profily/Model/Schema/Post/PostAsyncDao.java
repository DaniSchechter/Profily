package com.example.profily.Model.Schema.Post;

import android.os.AsyncTask;
import android.util.Log;

import com.example.profily.Model.Model;
import com.example.profily.Model.ModelSql;

import java.util.Arrays;
import java.util.List;

public class PostAsyncDao{

    public static void getAllPosts(final int numOfPosts, final Model.GetAllPostsListener listener) {
        new AsyncTask<String,Void,List<Post>>(){

            @Override
            protected List<Post> doInBackground(String... strings) {
                return ModelSql.getInstance().postDao().getAllPosts(numOfPosts);
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

    public static void getPostById(final String postId, final Model.GetPostByIdListener listener){
        new AsyncTask<String,Void,Post>(){

            @Override
            protected Post doInBackground(String... strings) {
                return ModelSql.getInstance().postDao().getPostById(strings[0]);
            }

            @Override
            protected void onPostExecute(Post data) {
                super.onPostExecute(data);
                if (listener != null) {
                    listener.onComplete(data);
                }
            }
        }.execute(postId);
    }

    public static void getUserNameById(String userId, final Model.GetUserNameByIdListener listener)
    {
        new AsyncTask<String, Void, String>(){

            @Override
            protected String doInBackground(String... strings) {
                return ModelSql.getInstance().postDao().getUserNameById(userId);
            }

            @Override
            protected void onPostExecute(String username) {
                super.onPostExecute(username);
                if (listener != null) {
                    listener.onComplete(username);
                }
            }
        }.execute();
    }

    public static void addPost(Post post) {
        new AsyncTask<Post, Void, Void>(){

            @Override
            protected Void doInBackground(Post... posts) {
                ModelSql.getInstance().postDao().insertPost(posts[0]);
                return null;
            }
        }.execute(post);

    }

    public static void addPostsAndFetch(final int numOfPosts, List<Post> postsList, final Model.GetAllPostsListener listener) {
        new AsyncTask<List<Post>, Void, List<Post>>(){

            @Override
            protected List<Post> doInBackground(List<Post>... posts) {
                ModelSql.getInstance().postDao().insertPosts(posts[0]);
                return ModelSql.getInstance().postDao().getAllPosts(numOfPosts);
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


    public static void addPostAndFetch(Post post, final Model.GetPostByIdListener listener) {
        new AsyncTask<Post, Void, Post>(){

            @Override
            protected Post doInBackground(Post... posts) {
                ModelSql.getInstance().postDao().insertPost(posts[0]);
                return ModelSql.getInstance().postDao().getPostById(post.getPostId());
            }

            @Override
            protected void onPostExecute(Post post) {
                super.onPostExecute(post);
                if (listener != null) {
                    listener.onComplete(post);
                }
            }
        }.execute(post);
    }
}
