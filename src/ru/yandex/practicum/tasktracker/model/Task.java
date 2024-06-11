package ru.yandex.practicum.tasktracker.model;

import java.time.Duration;
import java.time.LocalDateTime;

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
}
