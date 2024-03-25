package ru.yandex.practicum.tasktracker.manager;

import ru.yandex.practicum.tasktracker.model.Epic;
import ru.yandex.practicum.tasktracker.model.Subtask;
import ru.yandex.practicum.tasktracker.model.Task;

import java.util.List;

public interface TaskManager {
    List<Task> getTasks();
    List<Subtask> getSubtasks();
    List<Epic> getEpics();
    void addTask(Task newTask);
    void deleteTask(int taskId);
    void updateTask(Task task);
    void deleteAllTasks();
    Task getTaskById(int taskId);
    void addEpic(Epic newEpic);
    void deleteEpic(Epic newEpic);
    void updateEpic(Epic epic);
    void deleteAllEpics();
    Epic getEpicById(Integer id);
    void addSubTask(Subtask newSubTask);
    void updateSubTask(Subtask subtask);
    void deleteSubTask(int subtaskId);
    void deleteAllSubTasks();
    Subtask getSubtaskById(int id);
    List<Task> getSubtasksFromEpicById(Integer id);
}