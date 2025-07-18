package pl.rengreen.taskmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.rengreen.taskmanager.enums.NotificationStatus;
import pl.rengreen.taskmanager.model.Notification;
;
import pl.rengreen.taskmanager.repository.NotificationRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void createNotification(String message) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getAllNotifications(int page, int size) {
        return notificationRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    @Override
    public List<Notification> getNotifications(String status, int page, int size) {
        NotificationStatus notificationStatus = NotificationStatus.valueOf(status.toUpperCase());
        return notificationRepository.findByStatus(notificationStatus);
    }

    @Override
    public void markAsRead(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notification not found with id: " + id));
        notification.setStatus(NotificationStatus.READ);
        notification.setReadAt(LocalDateTime.now());
        notificationRepository.save(notification);
    }

    @Override
    public void markAsReadBatch(List<Long> ids) {
        ids.forEach(this::markAsRead);
    }

    @Override
    public void deleteNotification(Long id) {
        notificationRepository.deleteById(id);
    }

    @Override
    public void deleteNotificationsBatch(List<Long> ids) {
        ids.forEach(notificationRepository::deleteById);
    }
}