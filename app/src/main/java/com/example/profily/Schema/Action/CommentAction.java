package com.example.profily.Schema.Action;

public final class CommentAction extends Action {
    private static final ActionType type = ActionType.Comment;
    private static final String description = "commented on your post";

    public static ActionType getType() {
        return type;
    }

    public static String getDescription() {
        return " " + description;
    }
}
