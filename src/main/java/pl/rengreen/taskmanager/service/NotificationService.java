package pl.rengreen.taskmanager.service;

import pl.rengreen.taskmanager.dto.NotificationRequest;
import pl.rengreen.taskmanager.dto.NotificationResponse;

import java.util.List;

/**
 * Service interface for notification operations.
 */
public interface NotificationService {

    void sendNotification(NotificationRequest request);

    List<NotificationResponse> getUnreadNotifications(String recipientEmail);

    void markNotificationAsRead(Long notificationId);
}