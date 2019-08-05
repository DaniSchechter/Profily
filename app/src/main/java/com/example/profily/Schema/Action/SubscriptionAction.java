package com.example.profily.Schema.Action;

public final class SubscriptionAction extends Action{
    private static final ActionType type = ActionType.Subscription;
    private static final String description = "started following you";

    public static ActionType getType() {
        return type;
    }

    public static String getDescription() {
        return " " + description;
    }
}
