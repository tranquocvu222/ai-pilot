package pl.rengreen.taskmanager.service;

import pl.rengreen.taskmanager.model.Notification;

import java.util.List;

public interface NotificationService {
    void createNotification(String message);
    List<Notification> getNotifications(String status, int page, int size);
    void markAsRead(Long id);
    void markAsReadBatch(List<Long> ids);
    void deleteNotification(Long id);
    void deleteNotificationsBatch(List<Long> ids);
    List<Notification> getAllNotifications(int page, int size);
}