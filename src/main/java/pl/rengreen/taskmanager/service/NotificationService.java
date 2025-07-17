package pl.rengreen.taskmanager.service;

import pl.rengreen.taskmanager.model.Notification;

import java.util.List;

public interface NotificationService {
    void sendNotification(Notification notification);

    List<Notification> getUnreadNotifications(String recipientEmail);

    void markAsRead(Long notificationId);
}