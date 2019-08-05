package com.example.profily.Schema.Action;

public final class LikeAction extends Action {
    private static final ActionType type = ActionType.Like;
    private static final String description = "liked your post";

    public static ActionType getType() {
        return type;
    }

    public static String getDescription() {
        return " " + description;
    }
}
