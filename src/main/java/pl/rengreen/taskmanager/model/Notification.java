package pl.rengreen.taskmanager.model;

import pl.rengreen.taskmanager.enums.NotificationStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @Enumerated(EnumType.STRING)
    private NotificationStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime readAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Notification() {
        this.createdAt = LocalDateTime.now();
        this.status = NotificationStatus.UNREAD;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getReadAt() {
        return readAt;
    }

    public void setReadAt(LocalDateTime readAt) {
        this.readAt = readAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}