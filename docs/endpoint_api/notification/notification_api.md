## API Information

- **API Name**: Create Notification
- **Purpose**: Automatically or manually create a new notification
- **HTTP Method**: POST
- **Endpoint**: /notifications

---

## API Information

- **API Name**: Get Notification List
- **Purpose**: Retrieve the list of notifications for the current user with filtering and pagination
- **HTTP Method**: GET
- **Endpoint**: /notifications?status={status}&page={page}&size={size}

---

## API Information

- **API Name**: Mark Notification as Read
- **Purpose**: Mark a single notification as read
- **HTTP Method**: POST
- **Endpoint**: /notifications/{id}/read

---

## API Information

- **API Name**: Batch Mark Notifications as Read
- **Purpose**: Mark multiple notifications as read in a single request
- **HTTP Method**: POST
- **Endpoint**: /notifications/read-many

---

## API Information

- **API Name**: Delete Notification
- **Purpose**: Delete a single notification
- **HTTP Method**: DELETE
- **Endpoint**: /notifications/{id}

---

## API Information

- **API Name**: Batch Delete Notifications
- **Purpose**: Delete multiple notifications at once
- **HTTP Method**: POST
- **Endpoint**: /notifications/delete-many

---
