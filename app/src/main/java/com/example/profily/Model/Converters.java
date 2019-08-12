package com.example.profily.Model;

import androidx.room.TypeConverter;

import com.example.profily.Model.Schema.Action.Action;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.util.Date;
import java.util.List;

public class Converters {
    @TypeConverter
    public static List<String> strToList(String str) {
        return new Gson().fromJson(str, new TypeToken<List<String>>() {}.getType());
    }

    @TypeConverter
    public static String listToStr(List<String> list) {
        return new Gson().toJson(list);
    }

    @TypeConverter
    public static Date longToDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long dateToLong(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static String ActionToString(Action.ActionType actionType) {
        if (actionType == null) {
            return null;
        }
        if (actionType == Action.ActionType.Comment) {
            return Action.ActionType.Comment.toString();
        } else if (actionType == Action.ActionType.Like) {
            return Action.ActionType.Like.toString();
        } else {
            return Action.ActionType.Subscription.toString();
        }
    }

    @TypeConverter
    public static Action.ActionType stringToAction(String actionType) {
        if (actionType == null) {
            return null;
        }
        if (actionType.equals(Action.ActionType.Comment.toString())) {
            return Action.ActionType.Comment;
        } else if (actionType.equals(Action.ActionType.Like.toString())) {
            return Action.ActionType.Like;
        } else {
            return Action.ActionType.Subscription;
        }
    }
}
