package ru.yandex.practicum.tasktracker;

import ru.yandex.practicum.tasktracker.manager.InMemoryTaskManager;
import ru.yandex.practicum.tasktracker.model.Epic;
import ru.yandex.practicum.tasktracker.model.Subtask;
import ru.yandex.practicum.tasktracker.model.Task;
import ru.yandex.practicum.tasktracker.model.TaskStatus;
import ru.yandex.practicum.tasktracker.utils.Managers;
import ru.yandex.practicum.tasktracker.manager.FileBackedTaskManager;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
       // InMemoryTaskManager taskManager = Managers.getDefault();
        Path file = Paths.get(System.getProperty("user.dir"), "data", "data.csv");
        FileBackedTaskManager taskManager = new FileBackedTaskManager();
// Создайте две задачи
        Task task = new Task();
        taskManager.addTask(task);
        Task task2 = new Task();
        taskManager.addTask(task2);
// создать эпик с тремя подзадачами
        Epic epic = new Epic();
        taskManager.addEpic(epic);
        Subtask subtask1 = new Subtask();
        subtask1.setEpicId(epic.getId());
        taskManager.addSubTask(subtask1);
        Subtask subtask2 = new Subtask();
        subtask2.setEpicId(epic.getId());
        taskManager.addSubTask(subtask2);
        Subtask subtask3 = new Subtask();
        subtask3.setEpicId(epic.getId());
        taskManager.addSubTask(subtask3);
// создать эпик без подзадач.
        Epic epic1 = new Epic();
        taskManager.addEpic(epic1);
// Измените статусы созданных тасков, распечатайте их.
// Запросите созданные задачи несколько раз в разном порядке.
// После каждого запроса выведите историю и убедитесь, что в ней нет повторов.
        Task currentTask = taskManager.getTaskById(0);
        currentTask.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateTask(currentTask);
        currentTask = taskManager.getTaskById(1);
        currentTask.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateTask(currentTask);
        currentTask = taskManager.getSubtaskById(3);
        currentTask.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateTask(currentTask);
// удалить одну из задач  которая есть в истории, и проверьте, что при печати она не будет выводиться
        currentTask = taskManager.getTasks().get(1);
        taskManager.deleteTask(currentTask.getId());
// удалить один из эпиков с тремя подзадачами и убедитесь,
// что из истории удалился как сам эпик, так и все его подзадачи.
        Epic currentEpic = taskManager.getEpics().get(0);
        taskManager.deleteEpic(currentEpic);
    }
}
