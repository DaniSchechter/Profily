package com.example.profily.Model.Schema.Action;

import androidx.room.Ignore;
import androidx.room.TypeConverters;

import com.example.profily.Model.Converters;

@TypeConverters(Converters.class)

public class Action {
    private ActionType type;

    private String description;

    public enum ActionType {
        Like, Comment
    }

    public Action(){}

    @Ignore
    public Action(ActionType type, String description){
        this.type = type;
        this.description = description;
    }

    public ActionType getType() { return type; }
    public void setType(ActionType actionType) { this.type = actionType; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}


