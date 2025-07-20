package pl.rengreen.taskmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.rengreen.taskmanager.enums.NotificationStatus;
import pl.rengreen.taskmanager.model.Notification;
import pl.rengreen.taskmanager.model.User;
import pl.rengreen.taskmanager.repository.NotificationRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserService userService;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository, UserService userService) {
        this.notificationRepository = notificationRepository;
        this.userService = userService;
    }

    @Override
    public void createNotification(String message) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notificationRepository.save(notification);
    }

    @Override
    public void createNotificationForUsers(String message, String targetUsers) {
        List<User> users = userService.findAllWithRoles();
        List<User> targetUserList;
        
        switch (targetUsers) {
            case "USER_ONLY":
                targetUserList = users.stream()
                    .filter(user -> user.getRoles().stream()
                        .anyMatch(role -> "USER".equals(role.getRole())))
                    .collect(Collectors.toList());
                break;
            case "ADMIN_ONLY":
                targetUserList = users.stream()
                    .filter(user -> user.getRoles().stream()
                        .anyMatch(role -> "ADMIN".equals(role.getRole())))
                    .collect(Collectors.toList());
                break;
            case "ALL":
            default:
                targetUserList = users;
                break;
        }
        
        // Create notification for each target user
        for (User user : targetUserList) {
            Notification notification = new Notification();
            notification.setMessage(message);
            notification.setUser(user);
            notificationRepository.save(notification);
        }
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

    @Override
    public long getUnreadNotificationCount() {
        return notificationRepository.countByStatus(NotificationStatus.UNREAD);
    }

    @Override
    public List<Notification> getRecentNotifications(int limit) {
        return notificationRepository.findRecentNotifications(PageRequest.of(0, limit));
    }

    // Helper method to get current user
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() 
            && !"anonymousUser".equals(authentication.getPrincipal())) {
            String email = authentication.getName();
            return userService.getUserByEmail(email);
        }
        return null;
    }

    @Override
    public List<Notification> getCurrentUserNotifications(int page, int size) {
        User currentUser = getCurrentUser();
        if (currentUser != null) {
            return notificationRepository.findByUserOrderByCreatedAtDesc(currentUser);
        }
        return List.of();
    }

    @Override
    public List<Notification> getCurrentUserNotificationsByStatus(String status, int page, int size) {
        User currentUser = getCurrentUser();
        if (currentUser != null) {
            NotificationStatus notificationStatus = NotificationStatus.valueOf(status.toUpperCase());
            return notificationRepository.findByUserAndStatusOrderByCreatedAtDesc(currentUser, notificationStatus);
        }
        return List.of();
    }

    @Override
    public long getCurrentUserUnreadNotificationCount() {
        User currentUser = getCurrentUser();
        if (currentUser != null) {
            return notificationRepository.countByUserAndStatus(currentUser, NotificationStatus.UNREAD);
        }
        return 0;
    }

    @Override
    public List<Notification> getCurrentUserRecentNotifications(int limit) {
        User currentUser = getCurrentUser();
        if (currentUser != null) {
            return notificationRepository.findRecentNotificationsByUser(currentUser, PageRequest.of(0, limit));
        }
        return List.of();
    }
    
    @Override
    public void createNotificationForSpecificUser(String message, User user) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setUser(user);
        notification.setStatus(NotificationStatus.UNREAD);
        notification.setCreatedAt(LocalDateTime.now());
        notificationRepository.save(notification);
    }
}