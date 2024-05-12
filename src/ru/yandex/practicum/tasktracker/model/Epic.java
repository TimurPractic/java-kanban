package ru.yandex.practicum.tasktracker.model;

import java.util.List;

public class Epic extends Task {
    private List<Integer> subtasksIds;

    public List<Integer> getSubtasksIds() {
        return subtasksIds;
    }

    public void setSubtasksIds(List<Integer> subtasksIds) {
        this.subtasksIds = subtasksIds;
    }

    @Override
    public String toString() {
        return  getId() + ",EPIC," + getTitle() + "," + getStatus() + "," + getDescription();
    }
}