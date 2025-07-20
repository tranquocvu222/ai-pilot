package pl.rengreen.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import pl.rengreen.taskmanager.model.Notification;
import pl.rengreen.taskmanager.service.NotificationService;

import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class GlobalControllerAdvice {

    private final NotificationService notificationService;

    @Autowired
    public GlobalControllerAdvice(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @ModelAttribute("unreadNotificationCount")
    public long addUnreadNotificationCount() {
        // Check if user is authenticated
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() 
            && !"anonymousUser".equals(authentication.getPrincipal())) {
            return notificationService.getCurrentUserUnreadNotificationCount();
        }
        return 0;
    }

    @ModelAttribute("recentNotifications")
    public List<Notification> addRecentNotifications() {
        // Check if user is authenticated
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() 
            && !"anonymousUser".equals(authentication.getPrincipal())) {
            return notificationService.getCurrentUserRecentNotifications(10);
        }
        return Collections.emptyList();
    }
}
