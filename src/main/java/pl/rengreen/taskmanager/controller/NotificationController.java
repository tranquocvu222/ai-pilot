package pl.rengreen.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.rengreen.taskmanager.model.Notification;
import pl.rengreen.taskmanager.service.NotificationService;

import java.util.List;

@Controller
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public String listNotifications(@RequestParam(defaultValue = "") String status,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    Model model) {
        List<Notification> notifications;
        if (status.isEmpty()) {
            notifications = notificationService.getAllNotifications(page, size); // Fetch all notifications
        } else {
            notifications = notificationService.getNotifications(status, page, size); // Fetch filtered notifications
        }
        model.addAttribute("notifications", notifications);
        model.addAttribute("status", status);
        return "views/notifications";
    }

    @PostMapping("/{id}/read")
    public String markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return "redirect:/notifications";
    }

    @PostMapping("/read-many")
    public String markAsReadBatch(@RequestParam List<Long> ids) {
        notificationService.markAsReadBatch(ids);
        return "redirect:/notifications";
    }

    @PostMapping("/create")
    public String createNotification(@RequestParam String message) {
        notificationService.createNotification(message);
        return "redirect:/notifications";
    }

    @DeleteMapping("/{id}")
    public String deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return "redirect:/notifications";
    }

    @PostMapping("/delete-many")
    public String deleteNotificationsBatch(@RequestParam List<Long> ids) {
        notificationService.deleteNotificationsBatch(ids);
        return "redirect:/notifications";
    }
}