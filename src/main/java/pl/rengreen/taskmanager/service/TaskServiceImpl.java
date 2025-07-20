package pl.rengreen.taskmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.rengreen.taskmanager.model.Task;
import pl.rengreen.taskmanager.model.User;
import pl.rengreen.taskmanager.repository.NotificationRepository;
import pl.rengreen.taskmanager.repository.TaskRepository;
import pl.rengreen.taskmanager.util.TaskNotificationHelper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    private TaskRepository taskRepository;
    private final NotificationService notificationService;
    private final TaskNotificationHelper taskNotificationHelper;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, NotificationService notificationService, TaskNotificationHelper taskNotificationHelper) {
        this.taskRepository = taskRepository;
        this.notificationService = notificationService;
        this.taskNotificationHelper = taskNotificationHelper;
    }

    @Override
    public void createTask(Task task) {
        Task savedTask = taskRepository.save(task);
        
        // Send notification
        taskNotificationHelper.notifyTaskCreated(savedTask);
    }

    @Override
    public void updateTask(Long id, Task updatedTask) {
        Task existingTask = taskRepository.getOne(id);
        
        // Store old task data for comparison
        Task oldTask = new Task();
        oldTask.setId(existingTask.getId());
        oldTask.setName(existingTask.getName());
        oldTask.setDescription(existingTask.getDescription());
        oldTask.setDate(existingTask.getDate());
        oldTask.setOwner(existingTask.getOwner());
        
        // Update task
        existingTask.setName(updatedTask.getName());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setDate(updatedTask.getDate());
        existingTask.setOwner(updatedTask.getOwner());
        Task savedTask = taskRepository.save(existingTask);
        
        // Send notification
        taskNotificationHelper.notifyTaskUpdated(oldTask, savedTask);
    }

    @Override
    public void deleteTask(Long id) {
        Task task = taskRepository.getOne(id);
        
        if (task != null) {
            taskRepository.deleteById(id);
            
            // Send notification
            taskNotificationHelper.notifyTaskDeleted(task);
        }
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public List<Task> findByOwnerOrderByDateDesc(User user) {
        return taskRepository.findByOwnerOrderByDateDesc(user);
    }

    @Override
    public void setTaskCompleted(Long id) {
        Task task = taskRepository.getOne(id);
        task.setCompleted(true);
        taskRepository.save(task);
        notificationService.createNotification(
                "Task complete: "+ task.getName());
        
    }

    @Override
    public void setTaskNotCompleted(Long id) {
        Task task = taskRepository.getOne(id);
        task.setCompleted(false);
        taskRepository.save(task);
        notificationService.createNotification(
                "Task Not Complete: "+ task.getName());
    }

    @Override
    public List<Task> findFreeTasks() {
        return taskRepository.findAll()
                .stream()
                .filter(task -> task.getOwner() == null && !task.isCompleted())
                .collect(Collectors.toList());

    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    @Override
    public void assignTaskToUser(Task task, User user) {
        // Store old task data for comparison
        Task oldTask = new Task();
        oldTask.setId(task.getId());
        oldTask.setName(task.getName());
        oldTask.setDescription(task.getDescription());
        oldTask.setDate(task.getDate());
        oldTask.setOwner(task.getOwner());
        
        // Assign task
        task.setOwner(user);
        Task savedTask = taskRepository.save(task);
        
        // Send notification using TaskNotificationHelper
        taskNotificationHelper.notifyTaskUpdated(oldTask, savedTask);
    }

    @Override
    public void unassignTask(Task task) {
        // Store old task data for comparison
        Task oldTask = new Task();
        oldTask.setId(task.getId());
        oldTask.setName(task.getName());
        oldTask.setDescription(task.getDescription());
        oldTask.setDate(task.getDate());
        oldTask.setOwner(task.getOwner());
        
        // Unassign task
        task.setOwner(null);
        Task savedTask = taskRepository.save(task);
        
        // Send notification using TaskNotificationHelper
        taskNotificationHelper.notifyTaskUpdated(oldTask, savedTask);
    }

}
