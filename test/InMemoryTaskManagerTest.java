import org.junit.jupiter.api.Test;

import java.util.HashMap;
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
    void updateTask() {
        InMemoryTaskManager taskManager = Managers.getDefault();
        Task task = new Task("Test addNewTask");
        taskManager.addTask(task);
        Task task2 = new Task("Test addNewTask");
        taskManager.addTask(task2);
        taskManager.updateTask(task,null, "Updated task", null);
        assertNotEquals(taskManager.getTaskById(1),taskManager.getTaskById(2),"Задачи не одинаковые.");
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
    void updateEpic() {
        InMemoryTaskManager taskManager = Managers.getDefault();
        Epic epic = new Epic("Test addNewTask");
        taskManager.addEpic(epic);
        Epic epic2 = new Epic("Test addNewTask");
        taskManager.addEpic(epic2);
        taskManager.updateEpic(epic,null, "Updated task");
        assertNotEquals(taskManager.getEpicById(1),taskManager.getEpicById(2),"Задачи не одинаковые.");
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
    void getSubtaskFromEpicById() {
        InMemoryTaskManager taskManager = Managers.getDefault();
        Epic epic = new Epic("Test addNewTask");
        taskManager.addEpic(epic);
        Subtask subtask1 = new Subtask("Это 1 подзадача", epic.getId());
        taskManager.addSubTask(subtask1);
        Subtask subtask2 = new Subtask("Это 2 подзадача", epic.getId());
        taskManager.addSubTask(subtask2);
        assertEquals(taskManager.getSubtaskFromEpicById(1).size(), 2);
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
    void updateSubTask() {
        InMemoryTaskManager taskManager = Managers.getDefault();
        Epic epic = new Epic("Test addNewEpic");
        taskManager.addEpic(epic);
        Subtask subtask1 = new Subtask("Это 1 подзадача", epic.getId());
        taskManager.addSubTask(subtask1);
        Subtask subtask2 = new Subtask("Это 2 подзадача", epic.getId());
        taskManager.addSubTask(subtask2);
        taskManager.updateSubTask(subtask1,null,"Updated task", null);
        assertNotEquals(taskManager.getSubtaskById(1),taskManager.getSubtaskById(2),"Задачи не одинаковые.");
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