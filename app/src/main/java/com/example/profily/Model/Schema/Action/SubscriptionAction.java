package com.example.profily.Model.Schema.Action;

public final class SubscriptionAction extends Action{
    private final ActionType type = ActionType.Subscription;
    private final String description = "started following you";

    @Override
    public ActionType getType() {
        return type;
    }

    @Override
    public String getDescription() {
        return " " + description;
    }
}
