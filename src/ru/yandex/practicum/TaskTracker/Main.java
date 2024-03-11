package ru.yandex.practicum.TaskTracker;

import ru.yandex.practicum.TaskTracker.HistoryManager.HistoryManager;
import ru.yandex.practicum.TaskTracker.TasksManager.InMemoryTaskManager;
import ru.yandex.practicum.TaskTracker.utils.Managers;
import ru.yandex.practicum.TaskTracker.TasksManager.*;

public class Main {

    public static void main(String[] args){
        InMemoryTaskManager taskManager= Managers.getDefault();
        HistoryManager historyManager = Managers.getDefaultHistory();

// Создайте две задачи
        System.out.println("Cоздаём одну задачу");
        Task task = new Task("Пройти теорию 4 спринта"); //как правильно
        taskManager.addTask(task);
        Task task2 = new Task("Сдать практику 4 спринта");
        System.out.println("Cоздаём вторую задачу");
        taskManager.addTask(task2);
// создать эпик с двумя подзадачами
        System.out.println("Cоздаём эпик с двумя подзадачами");
        Epic epic = new Epic("Это эпик с 2 подзадачами");
        taskManager.addEpic(epic);
        Subtask subtask1 = new Subtask("Это первая подзадача", epic.getId());
        taskManager.addSubTask(subtask1);
        Subtask subtask2 = new Subtask("Это вторая подзадача", epic.getId());
        taskManager.addSubTask(subtask2);
// создать эпик с одной подзадачей.
        System.out.println("Cоздаём эпик с одной подзадачей");
        Epic epic1 = new Epic("Это эпик с одной подзадачей");
        taskManager.addEpic(epic1);
        Subtask subtask3 = new Subtask("Это единственная подзадача", epic1.getId());
        taskManager.addSubTask(subtask3);
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
        System.out.println("Поменяем статус таске 1");
        Task currentTask = taskManager.getTaskById(1);
        taskManager.updateTask(currentTask);
        System.out.println(currentTask);
// удалить одну из задач
        System.out.println("Удалим таску 1");
        currentTask = taskManager.getTasks().get(1);
        taskManager.deleteTask(currentTask);
        System.out.println(taskManager.getTasks());
// удалить один из эпиков
        System.out.println("Удалим эпик номер 3");
        Epic currentEpic = taskManager.getEpics().get(0);
        taskManager.deleteEpic(currentEpic);
        System.out.println(taskManager.getEpics());
    }
}
