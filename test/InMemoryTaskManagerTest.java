import ru.yandex.practicum.TaskTracker.TasksManager.Epic;
import ru.yandex.practicum.TaskTracker.TasksManager.InMemoryTaskManager;
import ru.yandex.practicum.TaskTracker.TasksManager.Subtask;
import ru.yandex.practicum.TaskTracker.TasksManager.Task;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.TaskTracker.utils.Managers;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {


    @Test
    void addTask() {
        InMemoryTaskManager taskManager = Managers.getDefault();
        Task task = new Task("Test addNewTask");
        taskManager.addTask(task);
        assertNotNull(task, "Задача не найдена.");
        assertEquals(taskManager.getTaskById(1),taskManager.getTaskById(1),"Задачи не одинаковые.");
    }

    @Test
    void deleteTask() {
        InMemoryTaskManager taskManager = Managers.getDefault();
        Task task = new Task("Test addNewTask");
        taskManager.addTask(task);
        taskManager.deleteTask(task);
        assertNull(taskManager.getTaskById(1));
    }

    @Test
    void deleteAllTasks() {
        InMemoryTaskManager taskManager = Managers.getDefault();
        Task task = new Task("Test addNewTask");
        taskManager.addTask(task);
        Task task2 = new Task("Test addNewTask");
        taskManager.addTask(task2);
        taskManager.deleteAllTasks();
        List<Subtask> emptyArray = new ArrayList<>();
        assertEquals(taskManager.getTasks(),emptyArray,"Тут не пусто!");
    }

    @Test
    void addEpic() {
        InMemoryTaskManager taskManager = Managers.getDefault();
        Epic epic = new Epic("Test addNewTask");
        taskManager.addEpic(epic);
        assertNotNull(epic, "Задача не найдена.");
        assertEquals(taskManager.getEpicById(1),taskManager.getEpicById(1),"Задачи не одинаковые.");
    }

    @Test
    void deleteEpic() {
        InMemoryTaskManager taskManager = Managers.getDefault();
        Epic epic = new Epic("Test addNewTask");
        taskManager.addEpic(epic);
        taskManager.deleteEpic(epic);
        assertNull(taskManager.getEpicById(1));
    }

    @Test
    void deleteAllEpics() {
        InMemoryTaskManager taskManager = Managers.getDefault();
        Epic epic = new Epic("Test addNewTask");
        taskManager.addEpic(epic);
        Epic epic2 = new Epic("Test addNewTask");
        taskManager.addTask(epic2);
        taskManager.deleteAllEpics();
        List<Subtask> emptyArray = new ArrayList<>();
        assertEquals(taskManager.getEpics(),emptyArray,"Тут не пусто!");
    }

    @Test
    void addSubTask() {
        InMemoryTaskManager taskManager = Managers.getDefault();
        Epic epic = new Epic("Test addNewTask");
        taskManager.addEpic(epic);
        Subtask subtask1 = new Subtask("Это 1 подзадача", epic.getId());
        taskManager.addSubTask(subtask1);
        assertNotNull(taskManager.getSubtasks());
        assertEquals(taskManager.getSubtasks().size(), 1);
    }

    @Test
    void deleteAllSubTasks() {
        InMemoryTaskManager taskManager = Managers.getDefault();
        Epic epic = new Epic("Test addNewTask");
        taskManager.addEpic(epic);
        Subtask subtask1 = new Subtask("Это 1 подзадача", epic.getId());
        taskManager.addSubTask(subtask1);
        Subtask subtask2 = new Subtask("Это 2 подзадача", epic.getId());
        taskManager.addSubTask(subtask2);
        taskManager.deleteAllSubTasks();
        List<Subtask> emptyArray = new ArrayList<>();
        assertEquals(taskManager.getSubtasks(),emptyArray,"Тут не пусто!");
    }
}