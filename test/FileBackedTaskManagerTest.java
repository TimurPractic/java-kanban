import ru.yandex.practicum.tasktracker.manager.FileBackedTaskManager;
import ru.yandex.practicum.tasktracker.model.Task;
import ru.yandex.practicum.tasktracker.model.Epic;
import ru.yandex.practicum.tasktracker.model.Subtask;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import ru.yandex.practicum.tasktracker.model.TaskStatus;
import ru.yandex.practicum.tasktracker.utils.Managers;
import ru.yandex.practicum.tasktracker.manager.InMemoryHistoryManager;
import ru.yandex.practicum.tasktracker.manager.InMemoryTaskManager;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FileBackedTaskManagerTest {
    FileBackedTaskManager taskManager = new FileBackedTaskManager();

    @Test
    public void checkSaveAndLoadNorm() {
        String fileName = "filename.csv";
        Path tasksFile = Paths.get(fileName);

        Task task = new Task();
        task.setTitle("Таск1");
        task.setStatus(TaskStatus.NEW);
        task.setDescription("String");
        taskManager.addTask(task);

        Epic epic = new Epic();
        epic.setTitle("Таск1");
        epic.setStatus(TaskStatus.NEW);
        epic.setDescription("String");
        taskManager.addEpic(epic);

        Subtask subtask1 = new Subtask();
        subtask1.setEpicId(epic.getId());
        subtask1.setTitle("Субтаск1");
        subtask1.setStatus(TaskStatus.NEW);
        subtask1.setDescription("String");
        taskManager.addSubTask(subtask1);

        Subtask subtask2 = new Subtask();
        subtask2.setEpicId(epic.getId());
        subtask2.setTitle("Субтаск2");
        subtask2.setStatus(TaskStatus.NEW);
        subtask2.setDescription("String");
        taskManager.addSubTask(subtask2);

        taskManager = FileBackedTaskManager.loadFromFile(tasksFile);
        List<Task> listLoadTask = taskManager.getTasks();
        List<Epic> listLoadEpic = taskManager.getEpics();
        List<Subtask> listLoadSubtask = taskManager.getSubtasks();

        assertEquals(subtask1.getTitle(), listLoadSubtask.get(0).getTitle(), "Поля title у задачи не совпадают.");
        assertEquals(subtask1.getId(), listLoadSubtask.get(0).getId(), "Поля id у задачи не совпадают.");
        assertEquals(subtask1.getEpicId(), listLoadSubtask.get(0).getEpicId(), "Поля epicId у задачи не совпадают.");
        assertEquals(subtask1.getStatus(), listLoadSubtask.get(0).getStatus(), "Поля status у задачи не совпадают.");
        assertEquals(subtask1.getDescription(), listLoadSubtask.get(0).getDescription(), "Поля description у задачи не совпадают.");

        assertEquals(task.getTitle(), listLoadTask.get(0).getTitle(), "Поля title у задачи не совпадают.");
        assertEquals(task.getId(), listLoadTask.get(0).getId(), "Поля id у задачи не совпадают.");
        assertEquals(task.getStatus(), listLoadTask.get(0).getStatus(), "Поля status у задачи не совпадают.");
        assertEquals(task.getDescription(), listLoadTask.get(0).getDescription(), "Поля description у задачи не совпадают.");

        assertEquals(epic.getTitle(), listLoadEpic.get(0).getTitle(), "Поля title у задачи не совпадают.");
        assertEquals(epic.getId(), listLoadEpic.get(0).getId(), "Поля id у задачи не совпадают.");
        assertEquals(epic.getStatus(), listLoadEpic.get(0).getStatus(), "Поля status у задачи не совпадают.");
        assertEquals(epic.getDescription(), listLoadEpic.get(0).getDescription(), "Поля description у задачи не совпадают.");
        assertEquals(epic.getSubtasksIds(), listLoadEpic.get(0).getSubtasksIds(), "Поля subTaskIds у задачи не совпадают.");

        Epic checkEpic = listLoadEpic.get(0);
        assertNotNull(checkEpic, "Задача не существует.");
        List<Integer> epicSubtaskId = checkEpic.getSubtasksIds();

        assertEquals(epicSubtaskId.size(), 2, "Подзадачи не совпадают.");
        assertEquals(epicSubtaskId.get(0), subtask1.getId(), "Задачи не совпадают.");
        assertEquals(epicSubtaskId.get(1), subtask2.getId(), "Задачи не совпадают.");

    }
}