# Notification Management Feature Design

## 1. Objective

Build a notification management feature for the system, allowing users to receive, view, mark as read, and delete notifications in a secure, efficient, user-friendly, and extensible manner.

---

## 2. Main Functionalities

### 2.1. Create Notification

- **Automatic:** The system creates notifications when events occur, for example:
- Assigning a task to a user.
- Task status changes (completed, canceled, etc.).
- **Manual (Admin):** Admins can create notifications for all users or a group of users.

**Validation:**
- `message` must not be empty and have a maximum length of 500 characters.
- `user_id` must exist in the users table (or be null for broadcast notifications).

**Error Handling:**
- If the notification cannot be saved (DB error, non-existent user), return an error code and detailed message.

---

### 2.2. Display Notification List

- Users view their notifications, with pagination support and sorted by newest first.
- Can filter by status: Read/Unread.

**Validation:**
- Check user authentication (token, session).
- Check authorization; users can only view their own notifications.

**Error Handling:**
- If unauthorized access (not logged in, trying to access another user’s notifications), return 401/403.

---

### 2.3. Mark Notification as Read

- Users can mark one or multiple notifications as read.

**Validation:**
- Notification must belong to the current user.
- Notification must exist.

**Error Handling:**
- If notification does not exist or does not belong to the user, return 404/403.

---

### 2.4. Delete Notification

- Users can delete one or multiple notifications.

**Validation:**
- Notification must belong to the current user.
- Notification must exist.

**Error Handling:**
- If notification does not exist or does not belong to the user, return 404/403.

---

## 3. Flow (Sequence of Operations)

1. When an event occurs (e.g., task assignment), the system calls NotificationService to create a notification with valid information.
2. The notification is saved to the DB, with the initial status as UNREAD.
3. Users access the notification list UI.
4. Users can:
- Mark notifications as read (single or batch)
- Delete notifications (single or batch)
5. All actions check authentication and authorization and return error messages if applicable.

---

## 4. Validation & Error Handling

| Error Case | Code | Error Message | Description |
|------------------------ |------|------------------------------------------------------------------|--------------------------------------------------|
| Unauthorized | 401 | Unauthorized: Please login first. | User not logged in or token expired |
| Forbidden | 403 | Forbidden: You do not have permission for this notification. | User not permitted to operate on this notification |
| Notification not found | 404 | Not Found: Notification not found or does not belong to user. | Notification not found by id or does not belong to user |
| Invalid input | 400 | Bad Request: Invalid data (empty message, invalid user, etc.). | E.g. empty message, non-existent user_id, invalid id |
| Server error | 500 | Internal Server Error: Something went wrong on the server side. | Unexpected system/server error |

---

## 5. Sequence Diagram

```mermaid
sequenceDiagram
participant User
participant UI
participant API
participant NotificationService
participant DB

User->>UI: Access Notification Page
UI->>API: GET /notifications (with token)
API->>DB: Query user notifications
DB-->>API: Return notification list
API-->>UI: Return result
UI-->>User: Display list

User->>UI: Mark notification as read
UI->>API: POST /notifications/{id}/read (with token)
API->>DB: Check permission, update status
DB-->>API: OK or error
API-->>UI: Operation result
UI-->>User: Show result

User->>UI: Delete notification
UI->>API: DELETE /notifications/{id} (with token)
API->>DB: Check permission, update is_deleted
DB-->>API: OK or error
API-->>UI: Operation result
UI-->>User: Show result

Note over API,DB: If validation error, return corresponding error code