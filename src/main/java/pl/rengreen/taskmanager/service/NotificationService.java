package pl.rengreen.taskmanager.service;

import pl.rengreen.taskmanager.model.Notification;
import pl.rengreen.taskmanager.model.User;

import java.util.List;

public interface NotificationService {
    void createNotification(String message);
    void createNotificationForUsers(String message, String targetUsers);
    List<Notification> getNotifications(String status, int page, int size);
    List<Notification> getAllNotifications(int page, int size);
    void markAsRead(Long id);
    void markAsReadBatch(List<Long> ids);
    void deleteNotification(Long id);
    void deleteNotificationsBatch(List<Long> ids);
    long getUnreadNotificationCount();
    List<Notification> getRecentNotifications(int limit);
    
    // Methods for current user
    List<Notification> getCurrentUserNotifications(int page, int size);
    List<Notification> getCurrentUserNotificationsByStatus(String status, int page, int size);
    long getCurrentUserUnreadNotificationCount();
    List<Notification> getCurrentUserRecentNotifications(int limit);
}