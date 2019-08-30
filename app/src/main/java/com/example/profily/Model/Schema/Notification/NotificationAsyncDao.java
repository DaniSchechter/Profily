package com.example.profily.Model.Schema.Notification;

import android.os.AsyncTask;

import com.example.profily.Model.Model;
import com.example.profily.Model.ModelSql;

import java.util.List;

public class NotificationAsyncDao {

    public static void getAllNotifications(final String userId,final int numOfNotifications, final Model.GetAllNotificationsListener listener) {
        new AsyncTask<String,Void, List<Notification>>(){

            @Override
            protected List<Notification> doInBackground(String... strings) {
                return ModelSql.getInstance().notificationDao().getAllNotifications(userId, numOfNotifications);
            }

            @Override
            protected void onPostExecute(List<Notification> data) {
                super.onPostExecute(data);
                if (listener != null) {
                    listener.onComplete(data);
                }
            }
        }.execute();

    }

    public static void addNotification(Notification notification) {
        new AsyncTask<Notification, Void, Void>(){

            @Override
            protected Void doInBackground(Notification... notifications) {
                ModelSql.getInstance().notificationDao().insertNotifications(notifications[0]);
                return null;
            }
        }.execute(notification);

    }

    public static void addNotificationsAndFetch(final String userId, final int numOfNotifications, List<Notification> notificationsList, final Model.GetAllNotificationsListener listener) {
        new AsyncTask<List<Notification>, Void, List<Notification>>(){

            @Override
            protected List<Notification> doInBackground(List<Notification>... notifications) {
                ModelSql.getInstance().notificationDao().insertNotifications(notifications[0]);
                return ModelSql.getInstance().notificationDao().getAllNotifications(userId, numOfNotifications);
            }

            @Override
            protected void onPostExecute(List<Notification> notifications) {
                super.onPostExecute(notifications);
                if (listener != null) {
                    listener.onComplete(notifications);
                }
            }
        }.execute(notificationsList);
    }

}
