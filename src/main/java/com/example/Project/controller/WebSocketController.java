package com.example.Project.controller;

import com.example.Project.model.Notification;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/like")
    @SendTo("/topic/likes")
    public Notification sendLikeNotification(Notification notification) {
        return notification;
    }

    @MessageMapping("/comment")
    @SendTo("/topic/comments")
    public Notification sendCommentNotification(Notification notification) {
        return notification;
    }
}
