import ru.yandex.practicum.tasktracker.manager.FileBackedTaskManager;
import ru.yandex.practicum.tasktracker.model.Task;
import ru.yandex.practicum.tasktracker.model.Epic;
import ru.yandex.practicum.tasktracker.model.Subtask;
import org.junit.jupiter.api.Test;

import ru.yandex.practicum.tasktracker.model.TaskStatus;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FileBackedTaskManagerTest {
    FileBackedTaskManager taskManager = new FileBackedTaskManager();

    @Test
    void checkSaveAndLoadNorm() {
        String fileName = "filename.csv";
        Path tasksFile = Paths.get(fileName);

        Task task = new Task();
        task.setTitle("Task1");
        task.setStatus(TaskStatus.NEW);
        task.setDescription("This is task");
        taskManager.addTask(task);
        task.setStartTime(LocalDateTime.of(2024,05,14,15,00));
        task.setDuration(5);
        System.out.println(task);

        Epic epic = new Epic();
        epic.setTitle("Epic1");
        epic.setStatus(TaskStatus.NEW);
        epic.setDescription("This is epic");
        //epic.setDuration(0);
        taskManager.addEpic(epic);
        System.out.println(epic);

        Subtask subtask1 = new Subtask();
        subtask1.setEpicId(epic.getId());
        subtask1.setTitle("Subtask1");
        subtask1.setStatus(TaskStatus.NEW);
        subtask1.setDescription("This is subtask1");
        taskManager.addSubTask(subtask1);
        subtask1.setStartTime(LocalDateTime.of(2024,5,14,20,0));
        subtask1.setDuration(10);
        epic.setStartTime(subtask1.getStartTime());
        System.out.println(subtask1);
        System.out.println(epic);

        Subtask subtask2 = new Subtask();
        subtask2.setEpicId(epic.getId());
        subtask2.setTitle("Subtask2");
        subtask2.setStatus(TaskStatus.NEW);
        subtask2.setDescription("This is subtask2");
        taskManager.addSubTask(subtask2);
        subtask2.setStartTime(LocalDateTime.of(2024,5,14,20,15));
        subtask2.setDuration(11);

        Subtask subtask3 = new Subtask();
        subtask3.setEpicId(epic.getId());
        subtask3.setTitle("Subtask3");
        subtask3.setStatus(TaskStatus.NEW);
        subtask3.setDescription("This is subtask3");
        taskManager.addSubTask(subtask3);
        subtask3.setStartTime(LocalDateTime.of(2024,5,14,20,30));
        subtask3.setDuration(12);
        taskManager.setEpicDuration(epic);

        taskManager = FileBackedTaskManager.loadFromFile(tasksFile);
        List<Task> listLoadTask = taskManager.getTasks();
        List<Epic> listLoadEpic = taskManager.getEpics();
        List<Subtask> listLoadSubtask = taskManager.getSubtasks();

        assertEquals(subtask1.getTitle(), listLoadSubtask.get(0).getTitle(), "Поля title у задачи не совпадают.");
        assertEquals(subtask1.getId(), listLoadSubtask.get(0).getId(), "Поля id у задачи не совпадают.");
        assertEquals(subtask1.getEpicId(), listLoadSubtask.get(0).getEpicId(), "Поля epicId у задачи не совпадают.");
        assertEquals(subtask1.getStatus(), listLoadSubtask.get(0).getStatus(), "Поля status у задачи не совпадают.");
        assertEquals(subtask1.getDescription(), listLoadSubtask.get(0).getDescription(), "Поля description у задачи не совпадают.");
        assertEquals(subtask1.getStartTime(), listLoadSubtask.get(0).getStartTime(), "Поля startTime у задачи не совпадают.");
        assertEquals(subtask1.getDuration(), listLoadSubtask.get(0).getDuration(), "Поля duration у задачи не совпадают.");
        assertEquals(subtask1.getEndTime(), listLoadSubtask.get(0).getEndTime(), "Поля endTime у задачи не совпадают.");

        assertEquals(task.getTitle(), listLoadTask.get(0).getTitle(), "Поля title у задачи не совпадают.");
        assertEquals(task.getId(), listLoadTask.get(0).getId(), "Поля id у задачи не совпадают.");
        assertEquals(task.getStatus(), listLoadTask.get(0).getStatus(), "Поля status у задачи не совпадают.");
        assertEquals(task.getDescription(), listLoadTask.get(0).getDescription(), "Поля description у задачи не совпадают.");
        assertEquals(task.getStartTime(), listLoadTask.get(0).getStartTime(), "Поля startTime у задачи не совпадают.");
        assertEquals(task.getDuration(), listLoadTask.get(0).getDuration(), "Поля duration у задачи не совпадают.");
        assertEquals(task.getEndTime(), listLoadTask.get(0).getEndTime(), "Поля endTime у задачи не совпадают.");

        assertEquals(epic.getTitle(), listLoadEpic.get(0).getTitle(), "Поля title у задачи не совпадают.");
        assertEquals(epic.getId(), listLoadEpic.get(0).getId(), "Поля id у задачи не совпадают.");
        assertEquals(epic.getStatus(), listLoadEpic.get(0).getStatus(), "Поля status у задачи не совпадают.");
        assertEquals(epic.getDescription(), listLoadEpic.get(0).getDescription(), "Поля description у задачи не совпадают.");
        assertEquals(epic.getSubtasksIds(), listLoadEpic.get(0).getSubtasksIds(), "Поля subTaskIds у задачи не совпадают.");
        assertEquals(epic.getStartTime(), listLoadEpic.get(0).getStartTime(), "Поля startTime у задачи не совпадают.");
        assertEquals(epic.getDuration(), listLoadEpic.get(0).getDuration(), "Поля duration у задачи не совпадают.");
        assertEquals(epic.getEndTime(), listLoadEpic.get(0).getEndTime(), "Поля endTime у задачи не совпадают.");

        Epic checkEpic = listLoadEpic.get(0);
        assertNotNull(checkEpic, "Задача не существует.");
        List<Integer> epicSubtaskId = checkEpic.getSubtasksIds();

        assertEquals(3, epicSubtaskId.size(), "Подзадачи не совпадают.");
        assertEquals(epicSubtaskId.get(0), subtask1.getId(), "Задачи не совпадают.");
        assertEquals(epicSubtaskId.get(1), subtask2.getId(), "Задачи не совпадают.");
        assertEquals(epicSubtaskId.get(2), subtask3.getId(), "Задачи не совпадают.");

    }
}