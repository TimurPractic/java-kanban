package ru.yandex.practicum.tasktracker.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    private TaskStatus status;
    private int id;
    private String title;
    private String description;
    private Duration duration = null;
    private LocalDateTime startTime = null;
    private LocalDateTime endTime = null;

    @Override
    public String toString() {
        return  id + ",TASK," + title + "," + status + "," + description +
                ",NotApplicable," + startTime + "," + duration + "," + endTime;
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

    public void setStartTime(LocalDateTime dateTime) {
        startTime = dateTime;
    }

    public void setDuration(long minutes) {
        duration = Duration.ofMinutes(minutes);
    }

    public Duration getDuration() {
        return duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setEndTime(LocalDateTime dateTime) {
        endTime = dateTime;
    }

    public LocalDateTime getEndTime() {
        return startTime.plus(duration);
    }

    // Переопределяем метод hashCode
    @Override
    public int hashCode() {
        return Objects.hash(id, "TASK", title, status, description, duration, startTime, endTime);
    }

    // Переопределяем метод equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id &&
                Objects.equals(title, task.title) &&
                Objects.equals(status, task.status) &&
                Objects.equals(description, task.description) &&
                Objects.equals(duration, task.duration) &&
                Objects.equals(startTime, task.startTime) &&
                Objects.equals(endTime, task.endTime);
    }
}
