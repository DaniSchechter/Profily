package com.example.profily.Schema.Action;

public final class LikeAction extends Action {
    private final ActionType type = ActionType.Like;
    private final String description = "liked your post";

    public ActionType getType() {
        return type;
    }

    @Override
    public String getDescription() {
        return " " + description;
    }
}
