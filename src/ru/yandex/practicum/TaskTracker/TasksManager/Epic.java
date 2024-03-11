package ru.yandex.practicum.TaskTracker.TasksManager;

import java.util.List;
import java.util.ArrayList;

public class Epic extends Task {
    private List<Subtask> subtasks;

    public Epic(String title) {
        super(title);
        this.subtasks = new ArrayList<>();
    }

    public List<Subtask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(List<Subtask> subtasks) {
        this.subtasks = subtasks;
    }
}