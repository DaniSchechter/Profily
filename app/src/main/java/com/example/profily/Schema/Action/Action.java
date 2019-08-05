package com.example.profily.Schema.Action;

public abstract class Action {

    public enum ActionType {
        Like, Comment, Subscription
    }

    public abstract String getDescription();
}


