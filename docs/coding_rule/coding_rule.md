# Project Coding Rules

## Naming Conventions

- **Class names**: PascalCase (e.g., `TaskController`, `NotificationService`)
- **Method names**: camelCase (e.g., `updateTask`, `sendNotification`)
- **Variable names**: camelCase (e.g., `taskList`, `userEmail`)
- **Constants**: UPPER_SNAKE_CASE (e.g., `DEFAULT_PAGE_SIZE`)
- **Packages**: all lowercase (e.g., `controller`, `service`, `repository`)

---

## Project Structure Guidelines

- `controller`: Handles HTTP requests and responses
- `service`: Contains business logic
- `repository`: Interfaces for database access
- `model`: Domain entities (annotated with `@Entity`)
- `dto`: Request/response data transfer objects
- `configuration`: Beans, security, CORS, etc.
- `templates`: Thymeleaf HTML pages
- `static`: CSS, JS, images

---

## Code Style Rules

- Follow **SOLID principles**
- No business logic in controllers
- Always use `@Service`, `@Repository`, `@RestController` properly
- Use constructor injection (`@RequiredArgsConstructor`)
- Never use field injection (`@Autowired` on fields)
- Use DTOs for external data representation, avoid exposing entities
- Validate request payloads using `@Valid` and validation annotations

---

## API Design Guidelines

- Use RESTful MVC principles
- Endpoint naming: `GET /tasks`, `POST /tasks`, `PUT /tasks/{id}`, `DELETE /tasks/{id}`
- Always return model view

---

## Testing Practices

- Place tests in `src/test/java/...`
- Use meaningful test method names: `shouldUpdateTaskWhenValidRequest`
- Mock dependencies using Mockito
- Use JUnit 5

---

## Security Guidelines

- Avoid exposing sensitive information in logs or responses
- Secure endpoints with `@PreAuthorize` or Spring Security config
- Sanitize user inputs if applicable

---

## Documentation

- Each class should have a brief class-level comment (JavaDoc)
- Each public method should be documented using `/** */`
- Add TODO comments for incomplete features

---

## Example

```java
/**
 * Service for handling task logic.
 */
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    /**
     * Updates a task by its ID.
     *
     * @param taskId ID of the task
     * @param updateDto Task update request
     * @return Updated Task
     */
    public Task updateTask(Long taskId, TaskUpdateRequest updateDto) {
        Task task = taskRepository.findById(taskId)
            .orElseThrow(() -> new TaskNotFoundException(taskId));
        task.setTitle(updateDto.getTitle());
        return taskRepository.save(task);
    }
}
```