package com.example.Project.service;

import com.example.Project.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public NotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendLikeNotification(String message, String userId) {
        Notification notification = new Notification(message, userId);
        messagingTemplate.convertAndSendToUser(userId, "/queue/likes", notification);
    }

    public void sendCommentNotification(String message, String userId) {
        Notification notification = new Notification(message, userId);
        messagingTemplate.convertAndSendToUser(userId, "/queue/comments", notification);
    }
}
