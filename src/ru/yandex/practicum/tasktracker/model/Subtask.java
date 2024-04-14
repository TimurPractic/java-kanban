package ru.yandex.practicum.tasktracker.model;

public class Subtask extends Task {
    private int epicId;
    //super(title, description, status);

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return  getId() +",SUBTASK," + getTitle() + "," + getStatus() + "," + getDescription() + "," + epicId;
    }
}