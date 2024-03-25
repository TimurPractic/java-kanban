package ru.yandex.practicum.tasktracker;

import ru.yandex.practicum.tasktracker.manager.InMemoryTaskManager;
import ru.yandex.practicum.tasktracker.model.Epic;
import ru.yandex.practicum.tasktracker.model.Subtask;
import ru.yandex.practicum.tasktracker.model.Task;
import ru.yandex.practicum.tasktracker.utils.Managers;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args){
        InMemoryTaskManager taskManager= Managers.getDefault();

// Создайте две задачи
        System.out.println("Cоздаём одну задачу");
        Task task = new Task(); //как правильно
        taskManager.addTask(task);
        Task task2 = new Task();
        System.out.println("Cоздаём вторую задачу");
        taskManager.addTask(task2);
// создать эпик с двумя подзадачами
        System.out.println("Cоздаём эпик с двумя подзадачами");
        Epic epic = new Epic();
        taskManager.addEpic(epic);
        Subtask subtask1 = new Subtask();
        taskManager.addSubTask(subtask1);
        Subtask subtask2 = new Subtask();
        taskManager.addSubTask(subtask2);
        subtask1.setEpicId(epic.getId());
        subtask2.setEpicId(epic.getId());
        List<Integer> epicSubtasksIds = new ArrayList<>();
        epicSubtasksIds.add(subtask1.getId());
        epicSubtasksIds.add(subtask2.getId());
        epic.setSubtasksIds(epicSubtasksIds);
// создать эпик с одной подзадачей.
        System.out.println("Cоздаём эпик с одной подзадачей");
        Epic epic1 = new Epic();
        taskManager.addEpic(epic1);
        Subtask subtask3 = new Subtask();
        taskManager.addSubTask(subtask3);
        subtask3.setEpicId(epic1.getId());
        List<Integer> epicSubtasksIds2 = new ArrayList<>();
        epicSubtasksIds.add(subtask3.getId());
        epic.setSubtasksIds(epicSubtasksIds2);
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
        taskManager.deleteTask(currentTask.getId());
        System.out.println(taskManager.getTasks());
// удалить один из эпиков
        System.out.println("Удалим эпик номер 3");
        Epic currentEpic = taskManager.getEpics().get(0);
        taskManager.deleteEpic(currentEpic);
        System.out.println(taskManager.getEpics());
    }
}
