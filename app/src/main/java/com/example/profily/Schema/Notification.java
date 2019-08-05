package com.example.profily.Schema;

import com.example.profily.Schema.Action.Action;

import java.time.LocalDateTime;

public class Notification {

    private String notificationId
    private Action action;
    private String triggeringUser;
    private String effectedUser;
    private LocalDateTime actionDateTime;
}

