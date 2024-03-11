package ru.yandex.practicum.TaskTracker.TasksManager;

public class Subtask extends Task {
    private int epicId;

   public Subtask(String title, int id) {
        super(title);
        this.epicId = id;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }
}