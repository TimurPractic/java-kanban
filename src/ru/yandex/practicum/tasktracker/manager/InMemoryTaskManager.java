package ru.yandex.practicum.tasktracker.manager;

import ru.yandex.practicum.tasktracker.model.Epic;
import ru.yandex.practicum.tasktracker.model.Subtask;
import ru.yandex.practicum.tasktracker.model.Task;
import ru.yandex.practicum.tasktracker.model.TaskStatus;
import ru.yandex.practicum.tasktracker.utils.Managers;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Subtask> subtasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final HistoryManager historyManager = Managers.getDefaultHistory();
    private int idSequence = 0;

    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public List<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public void addTask(Task newTask) {
        int newId = idSequence++;
        newTask.setId(newId);
        newTask.setStatus(TaskStatus.NEW);
        tasks.put(newTask.getId(), newTask);
    }

    @Override
    public void deleteTask(int taskId) {
        tasks.remove(taskId);
        historyManager.remove(taskId);
    }

    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void deleteAllTasks() {
        for (int i : tasks.keySet()){
            historyManager.remove(i);
        }
        tasks.clear();
    }

    @Override
    public Task getTaskById(int taskId) {
        Task task = tasks.get(taskId);
        historyManager.add(task);
        return task;
    }

    @Override
    public void addEpic(Epic newEpic) {
        int newId = idSequence++;
        newEpic.setId(newId);
        newEpic.setStatus(TaskStatus.NEW);
        newEpic.setSubtasksIds(new ArrayList<>());
        epics.put(newEpic.getId(), newEpic);
    }

    @Override
    public void deleteEpic(Epic newEpic) {

        List<Integer> subtasksForDeletion = newEpic.getSubtasksIds();
        subtasks.remove(newEpic.getSubtasksIds());
        epics.remove(newEpic.getId());
        historyManager.remove(newEpic.getId());
        for (int id :subtasksForDeletion) {
            historyManager.remove(id);
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    @Override
    public void deleteAllEpics() {
        for (int i : subtasks.keySet()){
            historyManager.remove(i);
        }
        for (int j : epics.keySet()){
            historyManager.remove(j);
        }
        subtasks.clear();
        epics.clear();
    }

    private void checkEpicStatus(Epic epic) {
        boolean isReady = true;
        boolean isInProgress = false;
        if (!epic.getSubtasksIds().isEmpty()) {
            for (int subId : epic.getSubtasksIds()) {
                if (subtasks.get(subId).getStatus().equals(TaskStatus.IN_PROGRESS)) {
                    isInProgress = true;
                    break;
                } else if (!subtasks.get(subId).getStatus().equals(TaskStatus.DONE)) {
                    isReady = false;
                    break;
                }
            }
            if (isInProgress) {
                epic.setStatus(TaskStatus.IN_PROGRESS);
            }
            if (isReady) {
                epic.setStatus(TaskStatus.DONE);
            }
        } else {
            epic.setStatus(TaskStatus.NEW);
        }
    }

    @Override
    public Epic getEpicById(Integer epicId) {
        if (epics.containsKey(epicId)) {
            historyManager.add(epics.get(epicId));
            return epics.get(epicId);
        }
        return null;
    }

    @Override
    public List<Task> getSubtasksFromEpicById(Integer epicId) {
        List<Task> epicSubtasks = new ArrayList<>();
        if (epics.containsKey(epicId)) {
            Epic epic = epics.get(epicId);
            for (Integer subId : epic.getSubtasksIds()) {
                epicSubtasks.add(subtasks.get(subId));
            }
        }
        return epicSubtasks;
    }

    @Override
    public void addSubTask(Subtask newSubTask) {
        int newId = idSequence++;
        newSubTask.setId(newId);
        newSubTask.setStatus(TaskStatus.NEW);
        subtasks.put(newSubTask.getId(), newSubTask);
        int currEpicID = newSubTask.getEpicId();
        Epic epic = getEpicById(currEpicID);
        List<Integer> epicSubtasksIds = epic.getSubtasksIds();
        epicSubtasksIds.add(newSubTask.getId());
        epic.setSubtasksIds(epicSubtasksIds);
        checkEpicStatus(epic);
    }

    @Override
    public void updateSubTask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        checkEpicStatus(getEpicById(subtask.getEpicId()));
    }

    @Override
    public void deleteSubTask(int subtaskId) {
        Subtask newSubTask = subtasks.get(subtaskId);
        Epic currEpic = getEpicById(newSubTask.getEpicId());
        subtasks.remove(subtaskId);
        checkEpicStatus(currEpic);
        historyManager.remove(subtaskId);
    }

    @Override
    public void deleteAllSubTasks() {
        List<Epic> epicsToDelete = new ArrayList<>();
        for (Subtask subtask : subtasks.values()) {
            epicsToDelete.add(getEpicById(subtask.getEpicId()));
            getEpicById(subtask.getEpicId()).setSubtasksIds(new ArrayList<>());
            historyManager.remove(subtask.getId());
        }
        subtasks.clear();
        for (Epic epic : epicsToDelete) {
            epic.setStatus(TaskStatus.NEW);
        }
    }

    @Override
    public Subtask getSubtaskById(int subtaskId) {
        historyManager.add(subtasks.get(subtaskId));
        return subtasks.get(subtaskId);
    }
}
