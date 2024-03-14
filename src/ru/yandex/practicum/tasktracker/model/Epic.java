package ru.yandex.practicum.tasktracker.model;

import java.util.List;

public class Epic extends Task {
    private List<Integer> subtasks;

    public List<Integer> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(List<Integer> subtasks) {
        this.subtasks = subtasks;
    }
}