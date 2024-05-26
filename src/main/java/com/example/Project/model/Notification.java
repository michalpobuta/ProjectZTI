package com.example.Project.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Notification {

    private String message;
    private String userId;

    public Notification() {
    }

    public Notification(String message, String userId) {
        this.message = message;
        this.userId = userId;
    }

}
