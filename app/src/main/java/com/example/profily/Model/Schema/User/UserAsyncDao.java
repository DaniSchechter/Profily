package com.example.profily.Model.Schema.User;

import android.os.AsyncTask;

import com.example.profily.Model.Model;
import com.example.profily.Model.ModelSql;
import com.example.profily.Model.Schema.Post.Post;
import com.example.profily.Model.Schema.Post.Post;

import java.util.List;

public class UserAsyncDao {

    public static void getAllUserPosts(String userId, final int numOfPosts, final Model.GetAllUserPostsListener listener) {
        new AsyncTask<String,Void, List<Post>>(){

            @Override
            protected List<Post> doInBackground(String... strings) {
                return ModelSql.getInstance().userDao().getAllUserPosts(userId, numOfPosts);
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

    public static void addUserDetailsAndFetch(final String userId, final User user, final Model.GetConnectedUserListener listener)
    {
        new AsyncTask<User, Void, User>(){

            @Override
            protected User doInBackground(User... users) {
                ModelSql.getInstance().userDao().insertUser(users[0]);
                return ModelSql.getInstance().userDao().getUserById(userId);
            }

            @Override
            protected void onPostExecute(User user) {
                super.onPostExecute(user);
                if (listener != null) {
                    listener.onComplete(user);
                }
            }
        }.execute(user);
    }

    public static void addUserPostsAndFetch(final String postId, final int numOfPosts, List<Post> postsList, final Model.GetAllUserPostsListener listener) {
        new AsyncTask<List<Post>, Void, List<Post>>() {

            @Override
            protected List<Post> doInBackground(List<Post>... posts) {
                ModelSql.getInstance().postDao().insertPosts(posts[0]);
                return ModelSql.getInstance().userDao().getAllUserPosts(postId, numOfPosts);
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

    public static void addUsers(List<User> usersList) {
        new AsyncTask<List<User>, Void, Void>(){

            @Override
            protected Void doInBackground(List<User>... users) {
                ModelSql.getInstance().userDao().insertAllUsers(users[0]);
                return null;
            }
        }.execute(usersList);

    }

    public static void addUser(User user)
    {
        new AsyncTask<User, Void, Void>(){

            @Override
            protected Void doInBackground(User... users) {
                ModelSql.getInstance().userDao().insertUser(users[0]);
                return null;
            }
        }.execute(user);
    }

    public static void getUserById(String userId,final Model.GetConnectedUserListener listener)
    {
        new AsyncTask<String, Void, User>(){

            @Override
            protected User doInBackground(String... strings) {
                return ModelSql.getInstance().userDao().getUserById(userId);
            }

            @Override
            protected void onPostExecute(User user) {
                super.onPostExecute(user);
                if (listener != null) {
                    listener.onComplete(user);
                }
            }
        }.execute();
    }

    public static void getPostCount(String userId, final Model.GetPostCountListener listener)
    {
        new AsyncTask<String, Void, Integer>(){

            @Override
            protected Integer doInBackground(String... strings) {
                return ModelSql.getInstance().userDao().getPostCount(userId);
            }

            @Override
            protected void onPostExecute(Integer numOfPosts) {
                super.onPostExecute(numOfPosts);
                if (listener != null) {
                    listener.onComplete(numOfPosts);
                }
            }
        }.execute();
    }
}
