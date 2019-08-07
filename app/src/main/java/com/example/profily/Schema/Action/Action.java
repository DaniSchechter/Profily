package com.example.profily.Schema.Action;

public abstract class Action {

    public enum ActionType {
        Like, Comment, Subscription
    }

    public abstract ActionType getType();
    public abstract String getDescription();
}


