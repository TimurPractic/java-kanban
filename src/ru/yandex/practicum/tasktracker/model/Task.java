package ru.yandex.practicum.tasktracker.model;

import ru.yandex.practicum.tasktracker.tasksmanager.TaskStatus;

public class Task {
    private TaskStatus status;
    private int id;
    private String title;
    private String description;

    @Override
    public String toString() {
        return "TasksManager.Task{" +
                "status=" + status +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
