package ru.yandex.practicum.tasktracker.manager;

import ru.yandex.practicum.tasktracker.model.Epic;
import ru.yandex.practicum.tasktracker.model.Subtask;
import ru.yandex.practicum.tasktracker.model.Task;
import ru.yandex.practicum.tasktracker.model.TaskStatus;
import ru.yandex.practicum.tasktracker.utils.Managers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;
import java.util.Optional;


public class InMemoryTaskManager implements TaskManager {
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Subtask> subtasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    public final HistoryManager historyManager = Managers.getDefaultHistory();
    private Set<Task> prioritizedTasks = new TreeSet<>(Comparator.comparing(Task::getStartTime,
            Comparator.nullsLast(Comparator.naturalOrder())));

    private int idSequence = 0;
    private LocalDateTime defaultDateTime = LocalDateTime.of(2000, 1, 1, 0, 0);;

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
        if (validator(newTask)) {
            int newId = idSequence++;
            newTask.setId(newId);
            newTask.setStatus(TaskStatus.NEW);
//            newTask.setStartTime(defaultDateTime.plusSeconds(idSequence));
//            newTask.setDuration(0);
            tasks.put(newTask.getId(), newTask);
            historyManager.add(newTask);
            prioritizedTasks.add(newTask);
        }
    }

    @Override
    public void deleteTask(int taskId) {
        tasks.remove(taskId);
        historyManager.remove(taskId);
    }

    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
        historyManager.add(task);
    }

    @Override
    public void deleteAllTasks() {
        tasks.keySet().forEach(historyManager::remove);
        tasks.clear();
    }

    @Override
    public Task getTaskById(int taskId) {
        if (tasks.containsKey(taskId)) {
            Task task = tasks.get(taskId);
            historyManager.add(task);
            return task;
        }
        return null;
    }

    @Override
    public void addEpic(Epic newEpic) {
        if (validator(newEpic)) {
            int newId = idSequence++;
            newEpic.setId(newId);
            newEpic.setStatus(TaskStatus.NEW);
            newEpic.setSubtasksIds(new ArrayList<>());
//            newEpic.setStartTime(findEarliestStartTime(getSubtasksFromEpicById(newId)));
//            newEpic.setDuration(0);
            epics.put(newEpic.getId(), newEpic);
            historyManager.add(newEpic);
            prioritizedTasks.add(newEpic);
        }
    }

    public static Optional<LocalDateTime> findEarliestStartTime(List<Subtask> subtasks) {
        return subtasks.stream()
                .map(Subtask::getStartTime)
                .filter(startTime -> startTime != null)
                .min(Comparator.naturalOrder());
    }

    @Override
    public void deleteEpic(Epic newEpic) {
        List<Integer> subtasksForDeletion = newEpic.getSubtasksIds();
        for (int subtaskId : subtasksForDeletion) {
            subtasks.remove(subtaskId);
        }
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
        historyManager.add(epic);
    }

    @Override
    public void deleteAllEpics() {
        for (int subtaskId : subtasks.keySet()) {
            historyManager.remove(subtaskId);
        }
        for (int epicId : epics.keySet()) {
            historyManager.remove(epicId);
        }
        subtasks.clear();
        epics.clear();
    }

    public void checkEpicStatus(Epic epic) {
        boolean isReady = true;
        boolean isInProgress = epic.getSubtasksIds().stream()
                .map(subId -> subtasks.get(subId).getStatus())
                .anyMatch(status -> status.equals(TaskStatus.IN_PROGRESS));

        if (!isInProgress) {
            isReady = epic.getSubtasksIds().stream()
                    .map(subId -> subtasks.get(subId).getStatus())
                    .allMatch(status -> status.equals(TaskStatus.DONE));
        }
        if (isInProgress) {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        } else if (isReady) {
            epic.setStatus(TaskStatus.DONE);
        } else {
            epic.setStatus(TaskStatus.NEW);
        }
    }

    public void setEpicDuration(Epic epic) {
            if (epic != null && epics.containsKey(epic.getId())) {
                List<Integer> subtaskIds = epic.getSubtasksIds();
                Stream<Subtask> subtasksStream = getSubtasks().stream()
                        .filter(subtask -> subtaskIds.contains(subtask.getId()) &&
                                subtask.getStartTime() != null &&
                                subtask.getDuration() != null);
                Optional<LocalDateTime> minStartTime = subtasksStream
                        .map(Subtask::getStartTime)
                        .min(LocalDateTime::compareTo);
                subtasksStream = getSubtasks().stream()
                        .filter(subtask -> subtask.getDuration() != null);
                Duration duration = subtasksStream
                        .map(Subtask::getDuration)
                        .reduce(Duration.ZERO, Duration::plus);
                LocalDateTime endTime = minStartTime.map(start -> start.plus(duration)).orElse(null);
                epic.setStartTime(minStartTime.orElse(null));
                epic.setEndTime(endTime);
                epic.setDuration(duration.toMinutes());
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
//        newSubTask.setStartTime(defaultDateTime.plusSeconds(idSequence));
//        newSubTask.setDuration(idSequence);
        subtasks.put(newSubTask.getId(), newSubTask);
        int currentEpicId = newSubTask.getEpicId();
        Epic epic = getEpicById(currentEpicId);
        List<Integer> epicSubtasksIds = epic.getSubtasksIds();
        epicSubtasksIds.add(newSubTask.getId());
        epic.setSubtasksIds(epicSubtasksIds);
        checkEpicStatus(epic);
        historyManager.add(epic);
        historyManager.add(newSubTask);
        prioritizedTasks.add(newSubTask);
    }

    @Override
    public void updateSubTask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        checkEpicStatus(getEpicById(subtask.getEpicId()));
        historyManager.add(subtask);
    }

    @Override
    public void deleteSubTask(int subtaskId) {
        Subtask newSubTask = subtasks.get(subtaskId);
        Epic currentEpic = getEpicById(newSubTask.getEpicId());
        subtasks.remove(subtaskId);
        checkEpicStatus(currentEpic);
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
        if (subtasks.containsKey(subtaskId)) {
            Subtask subtask = subtasks.get(subtaskId);
            historyManager.add(subtask);
            return subtask;
        }
        return null;
    }

    public List<Task> getPrioritizedTasks() {
        System.out.println(prioritizedTasks.size());
        return prioritizedTasks.stream().toList();
    }

    public boolean validator(Task task) {
        if (task.getStartTime() == null || task.getEndTime() == null) {
            return true;
        }
        for (Task prioritizedTask : prioritizedTasks) {
            if (task.getStartTime().isBefore(prioritizedTask.getEndTime()) &&
                    task.getEndTime().isAfter(prioritizedTask.getStartTime())) {
                return false;
            }
        }
        return true;
    }
}
