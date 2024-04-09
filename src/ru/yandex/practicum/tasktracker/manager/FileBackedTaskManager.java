package ru.yandex.practicum.tasktracker.manager;

import ru.yandex.practicum.tasktracker.model.Epic;
import ru.yandex.practicum.tasktracker.model.Subtask;
import ru.yandex.practicum.tasktracker.model.Task;

import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager {
    @Override
    public List<Task> getTasks() {
        save();
        return super.getTasks();
        }

    @Override
    public List<Subtask> getSubtasks() {
        save();
        return super.getSubtasks();
        }

    @Override
    public List<Epic> getEpics() {
        save();
        return super.getEpics();
    }

    @Override
    public void addTask(Task newTask) {
        save();
        super.addTask(newTask);
    }

    @Override
    public void deleteTask(int taskId) {
        save();
        super.deleteTask(taskId);
    }

    @Override
    public void updateTask(Task task) {
        save();
        super.updateTask(task);
    }

    @Override
    public void deleteAllTasks() {
        save();
        super.deleteAllTasks();
    }

    @Override
    public Task getTaskById(int taskId) {
        save();
        return super.getTaskById(taskId);
    }

    @Override
    public void addEpic(Epic newEpic) {
        save();
        super.addEpic(newEpic);
    }

    @Override
    public void deleteEpic(Epic newEpic) {
        save();
        super.deleteEpic(newEpic);
    }

    @Override
    public void updateEpic(Epic epic) {
        save();
        super.updateEpic(epic);
    }

    @Override
    public void deleteAllEpics() {
        save();
        super.deleteAllEpics();
    }

    @Override
    public Epic getEpicById(Integer epicId) {
        save();
        return super.getEpicById(epicId);
    }

    @Override
    public List<Task> getSubtasksFromEpicById(Integer epicId) {
        save();
        return super.getSubtasksFromEpicById(epicId);
    }

    @Override
    public void addSubTask(Subtask newSubTask) {
        save();
        super.addSubTask(newSubTask);
    }

    @Override
    public void updateSubTask(Subtask subtask) {
        super.updateSubTask(subtask);
        save();
    }

    @Override
    public void deleteSubTask(int subtaskId) {
        super.deleteSubTask(subtaskId);
        save();
    }

    @Override
    public void deleteAllSubTasks() {
        super.deleteAllSubTasks();
        save();
    }

    @Override
    public Subtask getSubtaskById(int subtaskId) {
        save();
        return super.getSubtaskById(subtaskId);
        }
///////////////////////////////////////////////////////////////////////////////////

    public void save() {

    }

    public String toString(Task task) {
        return "FileBackedTaskManager{" +
                "historyManager=" + historyManager +
                '}';
    }

    public static String historyToString(HistoryManager manager) {

    }

    public static List<Integer> historyFromString(String value) {

    }

    public static FileBackedTaskManager loadFromFile(File file) {

    }
}
