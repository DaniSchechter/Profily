package com.example.profily.Model;

import androidx.room.TypeConverter;

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
}
