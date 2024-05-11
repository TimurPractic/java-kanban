package ru.yandex.practicum.tasktracker.manager;

import ru.yandex.practicum.tasktracker.exception.ManagerSaveException;
import ru.yandex.practicum.tasktracker.model.Epic;
import ru.yandex.practicum.tasktracker.model.Subtask;
import ru.yandex.practicum.tasktracker.model.Task;
import ru.yandex.practicum.tasktracker.model.TaskStatus;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private String fileName;
    private Path tasksFile;
    private static final String HAT = "id,type,title,status,description,epic";

    public FileBackedTaskManager() {
        this.fileName = "filename.csv";
        this.tasksFile = Paths.get(fileName);
    }

    @Override
    public List<Task> getTasks() {
        List<Task> tasks = super.getTasks();
        save();
        return tasks;
    }

    @Override
    public List<Subtask> getSubtasks() {
        List<Subtask> subtasks = super.getSubtasks();
        save();
        return subtasks;
    }

    @Override
    public List<Epic> getEpics() {
        List<Epic> epics = super.getEpics();
        save();
        return epics;
    }

    @Override
    public void addTask(Task newTask) {
        super.addTask(newTask);
        save();
    }

    @Override
    public void deleteTask(int taskId) {
        super.deleteTask(taskId);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public Task getTaskById(int taskId) {
        Task t = super.getTaskById(taskId);
        save();
        return t;
    }

    @Override
    public void addEpic(Epic newEpic) {
        super.addEpic(newEpic);
        save();
    }

    @Override
    public void deleteEpic(Epic newEpic) {
        super.deleteEpic(newEpic);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public Epic getEpicById(Integer epicId) {
        Epic e = super.getEpicById(epicId);
        save();
        return e;
    }

    @Override
    public List<Task> getSubtasksFromEpicById(Integer epicId) {
        List<Task> lt2 = super.getSubtasksFromEpicById(epicId);
        save();
        return lt2;
    }

    @Override
    public void addSubTask(Subtask newSubTask) {
        super.addSubTask(newSubTask);
        save();
    }

    @Override
    public void updateSubTask(Subtask subtask) {
        save();
        super.updateSubTask(subtask);
    }

    @Override
    public void deleteSubTask(int subtaskId) {
        save();
        super.deleteSubTask(subtaskId);
    }

    @Override
    public void deleteAllSubTasks() {
        save();
        super.deleteAllSubTasks();
    }

    @Override
    public Subtask getSubtaskById(int subtaskId) {
        save();
        return super.getSubtaskById(subtaskId);
        }

    public void save() {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(tasksFile, StandardCharsets.UTF_8)) {
            bufferedWriter.write(HAT);
            bufferedWriter.write("\n");
            for (Task task : super.getTasks()) {
                bufferedWriter.write(task.toString());
                bufferedWriter.write("\n");
            }
            for (Epic epic : super.getEpics()) {
                bufferedWriter.write(epic.toString());
                bufferedWriter.write("\n");
            }
            for (Subtask subtask : super.getSubtasks()) {
                bufferedWriter.write(subtask.toString());
                bufferedWriter.write("\n");
            }
            bufferedWriter.write("\n");
            bufferedWriter.write(historyToString(historyManager));
        } catch (IOException e) {
            throw new ManagerSaveException(e);
        }
    }

    public static String historyToString(HistoryManager manager) {
        return manager.getHistory().stream()
                .map(Task::getId)
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    public static List<Integer> historyFromString(String value) {
        List<Integer> ids = new ArrayList<>();
        String[] values = value.split(",");
        for (String id : values) {
            ids.add(Integer.valueOf(id));
        }
        return ids;
    }


    public static FileBackedTaskManager loadFromFile(Path path) {
        FileBackedTaskManager fm = new FileBackedTaskManager();
        try (BufferedReader bufferedReader = Files.newBufferedReader((path), StandardCharsets.UTF_8)) {
            bufferedReader.readLine();
            String line = bufferedReader.readLine();
            while (line != null && !line.equals("")) {
                String[] values = line.split(",");
                int id = Integer.parseInt(values[0]);
                String title = values[2];
                TaskStatus status = TaskStatus.valueOf(values[3]);
                String description = values[4];
                switch (values[1]) {
                    case ("TASK"):
                        Task task = new Task();
                        task.setTitle(title);
                        task.setStatus(status);
                        task.setDescription(description);
                        fm.addTask(task);
                        break;
                    case ("EPIC"):
                        Epic epic = new Epic();
                        epic.setId(id);
                        epic.setTitle(title);
                        epic.setStatus(status);
                        epic.setDescription(description);
                        fm.addEpic(epic);
                        break;
                    case ("SUBTASK"):
                        Subtask subtask = new Subtask();
                        int epicId = Integer.parseInt(values[5]);
                        subtask.setId(id);
                        subtask.setTitle(title);
                        subtask.setStatus(status);
                        subtask.setDescription(description);
                        subtask.setEpicId(epicId);
                        fm.addSubTask(subtask);
                        break;
                    default: break;
                }
                line = bufferedReader.readLine();
            }
            String history = bufferedReader.readLine();
            if (history != null && !history.isEmpty()) {
                String[] historyElements = history.split(",");
                for (String historyElement : historyElements) {
                    Integer id = Integer.valueOf(historyElement);
                    if (fm.getTaskById(id) != null) {
                        fm.getTaskById(id);
                    } else if (fm.getEpicById(id) != null) {
                        fm.getEpicById(id);
                    } else {
                        fm.getSubtaskById(id);
                    }
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException(e);
        }
        return fm;
    }
}
