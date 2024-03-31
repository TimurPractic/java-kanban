package ru.yandex.practicum.tasktracker;

import ru.yandex.practicum.tasktracker.manager.InMemoryTaskManager;
import ru.yandex.practicum.tasktracker.model.Epic;
import ru.yandex.practicum.tasktracker.model.Subtask;
import ru.yandex.practicum.tasktracker.model.Task;
import ru.yandex.practicum.tasktracker.model.TaskStatus;
import ru.yandex.practicum.tasktracker.utils.Managers;

public class Main {

    public static void main(String[] args) {
        InMemoryTaskManager taskManager = Managers.getDefault();

// Создайте две задачи
        System.out.println("Cоздаём одну задачу");
        Task task = new Task();
        taskManager.addTask(task);
        System.out.println("Cоздаём вторую задачу");
        Task task2 = new Task();
        taskManager.addTask(task2);
// создать эпик с тремя подзадачами
        System.out.println("Cоздаём эпик с тремя подзадачами");
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
        System.out.println("Cоздаём эпик без подзадач");
        Epic epic1 = new Epic();
        taskManager.addEpic(epic1);
// Распечатайте списки задач
        System.out.println("Распечатаем списки задач");
        System.out.println(taskManager.getTasks());
// Распечатайте списки эпиков
        System.out.println("Распечатаем списки эпиков");
        System.out.println(taskManager.getEpics());
// Распечатайте списки задач
        System.out.println("Распечатаем списки субтасков");
        System.out.println(taskManager.getSubtasks());
// Измените статусы созданных тасков, распечатайте их.
// Запросите созданные задачи несколько раз в разном порядке.
// После каждого запроса выведите историю и убедитесь, что в ней нет повторов.
        System.out.println("Поменяем статус таске 1");
        Task currentTask = taskManager.getTaskById(0);
        currentTask.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateTask(currentTask);
        System.out.println(currentTask);
        System.out.println("Посмотрим историю");
        System.out.println(taskManager.historyManager.getHistory());
        System.out.println("Поменяем статус таске 2");
        currentTask = taskManager.getTaskById(1);
        currentTask.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateTask(currentTask);
        System.out.println(currentTask);
        System.out.println("Посмотрим историю");
        System.out.println(taskManager.historyManager.getHistory());
        System.out.println("Поменяем статус субтаске 3");
        currentTask = taskManager.getSubtaskById(3);
        currentTask.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateTask(currentTask);
        System.out.println(currentTask);
        System.out.println("Посмотрим историю");
        System.out.println(taskManager.historyManager.getHistory());
// удалить одну из задач  которая есть в истории, и проверьте, что при печати она не будет выводиться
        System.out.println("Удалим таску 1");
        currentTask = taskManager.getTasks().get(1);
        taskManager.deleteTask(currentTask.getId());
        System.out.println(taskManager.getTasks());
        System.out.println("Посмотрим историю");
        System.out.println(taskManager.historyManager.getHistory());
// удалить один из эпиков с тремя подзадачами и убедитесь,
// что из истории удалился как сам эпик, так и все его подзадачи.
        System.out.println("Удалим эпик номер 2 с тремя подзадачами");
        Epic currentEpic = taskManager.getEpics().get(0);
        taskManager.deleteEpic(currentEpic);
        System.out.println(taskManager.getEpics());
        System.out.println("Посмотрим историю");
        System.out.println(taskManager.historyManager.getHistory());
    }
}
