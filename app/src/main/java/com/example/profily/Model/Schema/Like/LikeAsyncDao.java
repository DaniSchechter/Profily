package com.example.profily.Model.Schema.Like;

import android.os.AsyncTask;

import com.example.profily.Model.Model;
import com.example.profily.Model.ModelSql;


public class LikeAsyncDao {

    public static void likeByUser(String postId, String userId, final Model.FindLikeListener listener) {
        new AsyncTask<Void, Void, String>(){

            @Override
            protected String doInBackground(Void... voids) {
                return ModelSql.getInstance().likeDao().likeByUser(postId, userId);
            }

            @Override
            protected void onPostExecute(String likeId) {
                super.onPostExecute(likeId);
                if (listener != null) {
                    listener.onComplete(likeId);
                }
            }
        }.execute();
    }

    public static void like(String likeId, String postId, String likingUserId, Model.LikeOperationListener listener) {
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                ModelSql.getInstance().likeDao().like(likeId, postId, likingUserId);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                listener.onComplete("");
            }
        }.execute();
    }

    public static void unlike(String likeId, Model.UnlikeOperationListener listener) {
        new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                ModelSql.getInstance().likeDao().unlike(likeId);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                listener.onComplete(true);
            }
        }.execute();
    }
}
