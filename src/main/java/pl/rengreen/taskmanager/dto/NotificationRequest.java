package pl.rengreen.taskmanager.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * DTO for creating a notification.
 */
public class NotificationRequest {

    @NotEmpty
    @Email
    private String recipientEmail;

    @NotEmpty
    private String message;

    // Getters and Setters
    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}