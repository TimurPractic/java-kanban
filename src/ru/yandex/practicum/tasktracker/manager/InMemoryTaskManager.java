package ru.yandex.practicum.tasktracker.manager;

import ru.yandex.practicum.tasktracker.model.Epic;
import ru.yandex.practicum.tasktracker.model.Subtask;
import ru.yandex.practicum.tasktracker.model.Task;
import ru.yandex.practicum.tasktracker.tasksmanager.TaskStatus;
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
    public void deleteTask(Task newTask) {
        tasks.remove(newTask.getId());
    }

    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void deleteAllTasks() {
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
        epics.put(newEpic.getId(), newEpic);
    }

    @Override
    public void deleteEpic(Epic newEpic) {
        subtasks.remove(newEpic.getSubtasks());
        epics.remove(newEpic.getId());
    }

    @Override
    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    @Override
    public void deleteAllEpics(){
        subtasks.clear();
        epics.clear();
    }

    private void checkEpicStatus(Epic epic) {
        boolean isReady = true;
        boolean isInProgress = false;
        if(!epic.getSubtasks().isEmpty()) {
            for (int subId : epic.getSubtasks()) {
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
        List <Task> epicSubtasks = new ArrayList<>();
        if (epics.containsKey(epicId)) {
            Epic epic = epics.get(epicId);
            for (Integer subId : epic.getSubtasks()) {
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
    }

    @Override
    public void deleteAllSubTasks() {
        List<Epic> epicsToDelete = new ArrayList<>();
        for (Subtask subtask : subtasks.values()){
            epicsToDelete.add(getEpicById(subtask.getEpicId()));
            getEpicById(subtask.getEpicId()).setSubtasks(new ArrayList<>());
        }
        subtasks.clear();
        for (Epic epic : epicsToDelete){
            checkEpicStatus(epic);
        }
    }

    @Override
    public Subtask getSubtaskById(int subtaskId) {
        historyManager.add(subtasks.get(subtaskId));
        return subtasks.get(subtaskId);
    }
}
