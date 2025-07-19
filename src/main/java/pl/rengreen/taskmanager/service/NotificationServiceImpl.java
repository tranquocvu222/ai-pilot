package pl.rengreen.taskmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.rengreen.taskmanager.dto.NotificationRequest;
import pl.rengreen.taskmanager.dto.NotificationResponse;
import pl.rengreen.taskmanager.model.Notification;
import pl.rengreen.taskmanager.repository.NotificationRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of NotificationService.
 */
@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void sendNotification(NotificationRequest request) {
        Notification notification = new Notification();
        notification.setRecipientEmail(request.getRecipientEmail());
        notification.setMessage(request.getMessage());
        notificationRepository.save(notification);
    }

    @Override
    public List<NotificationResponse> getUnreadNotifications(String recipientEmail) {
        return notificationRepository.findByRecipientEmailAndIsReadFalse(recipientEmail)
                .stream()
                .map(notification -> {
                    NotificationResponse response = new NotificationResponse();
                    response.setId(notification.getId());
                    response.setRecipientEmail(notification.getRecipientEmail());
                    response.setMessage(notification.getMessage());
                    response.setRead(notification.isRead());
                    response.setCreatedAt(notification.getCreatedAt());
                    return response;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void markNotificationAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException("Notification not found"));
        notification.setRead(true);
        notificationRepository.save(notification);
    }
}