package com.example.profily.Model.Schema.Action;

import androidx.room.TypeConverters;

import com.example.profily.Model.Converters;

@TypeConverters(Converters.class)

public class Action {
    private ActionType type;

    private String description;

    public enum ActionType {
        Like, Comment, Subscription
    }

    public ActionType getType() { return type; }
    public void setType(ActionType actionType) { this.type = actionType; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}


