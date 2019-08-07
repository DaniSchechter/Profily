package com.example.profily.Schema.Action;

public final class CommentAction extends Action {
    private final ActionType type = ActionType.Comment;
    private final String description = "commented on your post";

    @Override
    public ActionType getType() {
        return type;
    }

    @Override
    public String getDescription() {
        return " " + description;
    }
}
