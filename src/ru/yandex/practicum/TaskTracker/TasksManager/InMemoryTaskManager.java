package ru.yandex.practicum.TaskTracker.TasksManager;

import ru.yandex.practicum.TaskTracker.HistoryManager.HistoryManager;
import ru.yandex.practicum.TaskTracker.utils.Managers;

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
        Task task1 = new Task(task.getTitle());
        task1.setId(task.getId());
        tasks.put(task1.getId(), task1);
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        historyManager.add(task);
        System.out.println(task);
        return task;
    }

    @Override
    public void addEpic(Epic newEpic) {
        int newID = idSequence++;
        newEpic.setId(newID);
        newEpic.setStatus(TaskStatus.NEW);
        epics.put(newEpic.getId(), newEpic);
    }

    @Override
    public void deleteEpic(Epic newEpic) {
        List<Subtask> currentSubtasks = newEpic.getSubtasks();
        int count = currentSubtasks.size();
        for (Subtask subtask : currentSubtasks) {
            subtasks.remove(subtask.getId());
        }
        epics.remove(newEpic.getId());
    }

    @Override
    public void updateEpic(Epic epic) {
        Epic epic1 = new Epic(epic.getTitle());
        epic1.setId(epic.getId());
        epics.put(epic1.getId(), epic1);
    }

    @Override
    public void deleteAllEpics(){
        subtasks.clear();
        epics.clear();
    }

    private void checkEpicStatusForDone(Epic epic) {
        boolean isReady = true;
        for (Subtask sub : epic.getSubtasks()) {
            if(!sub.getStatus().equals(TaskStatus.DONE)){
                isReady = false;
                break;
            } else {
                isReady = true;
            }
        }
        if(isReady){
            epic.setStatus(TaskStatus.DONE);
        }
    }

    private void checkEpicStatusForProgress(Epic epic) {
        boolean isInProgress = false;
        for (Subtask sub : epic.getSubtasks()){
            if(sub.getStatus().equals(TaskStatus.IN_PROGRESS)){
                isInProgress = true;
                break;
            }
        }
        if(isInProgress){
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }

    @Override
    public Epic getEpicById(Integer id) {
        if (epics.containsKey(id)) {
            historyManager.add(epics.get(id));
            return epics.get(id);
        } else {
            System.out.println("Такого номера эпика не существует.");
        }
        return null;
    }

    @Override
    public ArrayList<Task> getSubtaskFromEpicById(Integer id) {
        if (epics.containsKey(id)) {
            Epic epic = epics.get(id);
            ArrayList <Task> sbt = new ArrayList<>();
            for (Subtask subtask : epic.getSubtasks()) {
                sbt.add(subtask);
            }
                return sbt;
        } else {
            System.out.println("Такого номера эпика не существует.");
            return null;
        }
    }

    @Override
    public void addSubTask(Subtask newSubTask) {
        int newID = idSequence++;
        newSubTask.setId(newID);
        newSubTask.setStatus(TaskStatus.NEW);
        subtasks.put(newSubTask.getId(), newSubTask);
        Epic assignedEpic = getEpicById(newSubTask.getEpicId());
        List<Subtask> chosenArray = assignedEpic.getSubtasks();
        chosenArray.add(newSubTask);
        assignedEpic.setSubtasks(chosenArray);
    }

    @Override
    public void updateSubTask(Subtask subtask) {
        Subtask subtask1 = new Subtask(subtask.getTitle(),subtask.getEpicId());
        subtask1.setId(subtask.getId());
        subtasks.put(subtask1.getId(), subtask1);
        Epic currEpic = getEpicById(subtask.getEpicId());
        checkEpicStatusForProgress(currEpic);
        checkEpicStatusForDone(currEpic);
    }

    @Override
    public void deleteOneSubTask(int id) {
        Subtask newSubTask = subtasks.get(id);
        //удалиться из эпика
        Epic currEpic = getEpicById(newSubTask.getEpicId());
        subtasks.remove(id);
        //пересчитать статус эпика
        checkEpicStatusForProgress(currEpic);
        checkEpicStatusForDone(currEpic);
    }

    @Override
    public void deleteAllSubTasks() {
        //удалиться из эпика
        ArrayList<Epic> epicsToDelete = new ArrayList<>();
        for (Subtask subtask : subtasks.values()){
            epicsToDelete.add(getEpicById(subtask.getEpicId()));
        }
        subtasks.clear();
        //пересчитать статус эпика
        for (Epic epic : epicsToDelete){
            checkEpicStatusForProgress(epic);
            checkEpicStatusForDone(epic);
        }
    }

    @Override
    public Subtask getSubtaskById(int id) {
        historyManager.add(subtasks.get(id));
        return subtasks.get(id);
    }
}
