package pl.rengreen.taskmanager.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.rengreen.taskmanager.model.Task;
import pl.rengreen.taskmanager.model.User;
import pl.rengreen.taskmanager.service.NotificationService;
import pl.rengreen.taskmanager.service.UserService;

@Component
public class TaskNotificationHelper {
    
    @Autowired
    private NotificationService notificationService;
    
    @Autowired
    private UserService userService;
    
    public void notifyTaskCreated(Task task) {
        String currentUser = getCurrentUser();
        
        // Skip notification if no authenticated user (e.g., during data loading)
        if (currentUser == null) {
            return;
        }
        
        // Get current user object to get the name
        User currentUserObj = userService.getUserByEmail(currentUser);
        if (currentUserObj == null) {
            return;
        }
        String currentUserName = currentUserObj.getName();
        
        if (task.getOwner() != null && !task.getOwner().getName().equals(currentUserName)) {
            // Admin assigns task to another user
            String message = String.format("📋 You have been assigned a new task: '%s' by %s", 
                task.getName(), currentUserName);
            notificationService.createNotificationForSpecificUser(message, task.getOwner());
        } else {
            // User creates task for themselves
            String message = String.format("✅ You have successfully created a new task: '%s'", task.getName());
            User user = userService.getUserByEmail(currentUser);
            if (user != null) {
                notificationService.createNotificationForSpecificUser(message, user);
            }
        }
    }
    
    public void notifyTaskUpdated(Task oldTask, Task newTask) {
        String currentUser = getCurrentUser();
        
        // Skip notification if no authenticated user (e.g., during data loading)
        if (currentUser == null) {
            return;
        }
        
        // Get current user object to get the name
        User currentUserObj = userService.getUserByEmail(currentUser);
        if (currentUserObj == null) {
            return;
        }
        String currentUserName = currentUserObj.getName();
        
        String oldOwnerName = oldTask.getOwner() != null ? oldTask.getOwner().getName() : null;
        String newOwnerName = newTask.getOwner() != null ? newTask.getOwner().getName() : null;
        
        if (oldOwnerName != null && newOwnerName != null && !oldOwnerName.equals(newOwnerName)) {
            // Task is being reassigned
            notifyTaskReassigned(oldTask, newTask, currentUser);
        } else if (newOwnerName != null && !currentUserName.equals(newOwnerName)) {
            // Admin updates task of another user
            User taskOwner = userService.getUserByEmail(newTask.getOwner().getEmail());
            if (taskOwner != null) {
                String message = String.format("🔄 Your task '%s' has been updated by %s", 
                    newTask.getName(), currentUserName);
                notificationService.createNotificationForSpecificUser(message, taskOwner);
            }
        } else {
            // User updates their own task
            User user = userService.getUserByEmail(currentUser);
            if (user != null) {
                String message = String.format("🔄 You have successfully updated task: '%s'", newTask.getName());
                notificationService.createNotificationForSpecificUser(message, user);
            }
        }
    }
    
    public void notifyTaskDeleted(Task task) {
        String currentUser = getCurrentUser();
        
        // Skip notification if no authenticated user (e.g., during data loading)
        if (currentUser == null) {
            return;
        }
        
        // Get current user object to get the name
        User currentUserObj = userService.getUserByEmail(currentUser);
        if (currentUserObj == null) {
            return;
        }
        String currentUserName = currentUserObj.getName();
        
        String taskOwnerName = task.getOwner() != null ? task.getOwner().getName() : null;
        
        if (taskOwnerName != null && !currentUserName.equals(taskOwnerName)) {
            // Admin deletes task of another user
            String message = String.format("🗑️ Your task '%s' has been deleted by %s", 
                task.getName(), currentUserName);
            notificationService.createNotificationForSpecificUser(message, task.getOwner());
        } else if (taskOwnerName != null) {
            // User deletes their own task
            User user = userService.getUserByEmail(currentUser);
            if (user != null) {
                String message = String.format("🗑️ You have successfully deleted task: '%s'", task.getName());
                notificationService.createNotificationForSpecificUser(message, user);
            }
        }
    }
    
    private void notifyTaskReassigned(Task oldTask, Task newTask, String currentUser) {
        User oldOwner = oldTask.getOwner();
        User newOwner = newTask.getOwner();
        
        // Get current user name from email
        User currentUserObj = userService.getUserByEmail(currentUser);
        String currentUserName = currentUserObj != null ? currentUserObj.getName() : currentUser;
        
        // Notify previous user
        if (oldOwner != null) {
            String newOwnerName = newOwner != null ? newOwner.getName() : "Unassigned";
            String messageOld = String.format("📤 Task '%s' has been reassigned from you to %s by %s", 
                newTask.getName(), newOwnerName, currentUserName);
            notificationService.createNotificationForSpecificUser(messageOld, oldOwner);
        }
        
        // Notify new user
        if (newOwner != null) {
            String messageNew = String.format("📥 You have been assigned task '%s' by %s", 
                newTask.getName(), currentUserName);
            notificationService.createNotificationForSpecificUser(messageNew, newOwner);
        }
    }
    
    private String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || 
            "anonymousUser".equals(authentication.getName())) {
            return null;
        }
        return authentication.getName();
    }
}
