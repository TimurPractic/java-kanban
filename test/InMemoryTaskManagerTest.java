import ru.yandex.practicum.tasktracker.model.Epic;
import ru.yandex.practicum.tasktracker.manager.InMemoryTaskManager;
import ru.yandex.practicum.tasktracker.model.Subtask;
import ru.yandex.practicum.tasktracker.model.Task;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.tasktracker.utils.Managers;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {


    @Test
    void addTask() {
        InMemoryTaskManager taskManager = Managers.getDefault();
        Task task = new Task();
        taskManager.addTask(task);
        assertNotNull(task, "Задача не найдена.");
        assertEquals(taskManager.getTaskById(1),taskManager.getTaskById(1),"Задачи не одинаковые.");
    }

    @Test
    void deleteTask() {
        InMemoryTaskManager taskManager = Managers.getDefault();
        Task task = new Task();
        taskManager.addTask(task);
        taskManager.deleteTask(task.getId());
        assertNull(taskManager.getTaskById(1));
    }

    @Test
    void deleteAllTasks() {
        InMemoryTaskManager taskManager = Managers.getDefault();
        Task task = new Task();
        taskManager.addTask(task);
        Task task2 = new Task();
        taskManager.addTask(task2);
        taskManager.deleteAllTasks();
        List<Subtask> emptyArray = new ArrayList<>();
        assertEquals(taskManager.getTasks(),emptyArray,"Тут не пусто!");
    }

    @Test
    void addEpic() {
        InMemoryTaskManager taskManager = Managers.getDefault();
        Epic epic = new Epic();
        taskManager.addEpic(epic);
        assertNotNull(epic, "Задача не найдена.");
        assertEquals(taskManager.getEpicById(1),taskManager.getEpicById(1),"Задачи не одинаковые.");
    }

    @Test
    void deleteEpic() {
        InMemoryTaskManager taskManager = Managers.getDefault();
        Epic epic = new Epic();
        taskManager.addEpic(epic);
        taskManager.deleteEpic(epic);
        assertNull(taskManager.getEpicById(1));
    }

    @Test
    void deleteAllEpics() {
        InMemoryTaskManager taskManager = Managers.getDefault();
        Epic epic = new Epic();
        taskManager.addEpic(epic);
        Epic epic2 = new Epic();
        taskManager.addTask(epic2);
        taskManager.deleteAllEpics();
        List<Epic> emptyArray = new ArrayList<>();
        assertEquals(taskManager.getEpics(),emptyArray,"Тут не пусто!");
    }

    @Test
    void addSubTask() {
        InMemoryTaskManager taskManager = Managers.getDefault();
        Epic epic = new Epic();
        taskManager.addEpic(epic);
        Subtask subtask1 = new Subtask();
        taskManager.addSubTask(subtask1);
        assertNotNull(taskManager.getSubtasks());
        assertEquals(taskManager.getSubtasks().size(), 1);
    }

    @Test
    void deleteAllSubTasks() {
        InMemoryTaskManager taskManager = Managers.getDefault();
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
        taskManager.deleteAllSubTasks();
        List<Subtask> emptyArray = new ArrayList<>();
        assertEquals(taskManager.getSubtasks(),emptyArray,"Тут не пусто!");
    }
}