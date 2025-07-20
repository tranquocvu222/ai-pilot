package pl.rengreen.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
            notifications = notificationService.getCurrentUserNotifications(page, size); // Fetch current user notifications
        } else {
            notifications = notificationService.getCurrentUserNotificationsByStatus(status, page, size); // Fetch filtered notifications for current user
        }
        model.addAttribute("notifications", notifications);
        model.addAttribute("status", status);
        model.addAttribute("unreadNotificationCount", notificationService.getCurrentUserUnreadNotificationCount());
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
    public String createNotification(@RequestParam String message, 
                                   @RequestParam(defaultValue = "ALL") String targetUsers,
                                   RedirectAttributes redirectAttributes) {
        try {
            notificationService.createNotificationForUsers(message, targetUsers);
            redirectAttributes.addFlashAttribute("successMessage", "Notification created successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to create notification: " + e.getMessage());
        }
        return "redirect:/notifications";
    }

    @GetMapping("/create")
    public String showCreateNotificationForm() {
        return "views/create-notification";
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