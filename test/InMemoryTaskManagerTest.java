import ru.yandex.practicum.tasktracker.model.Epic;
import ru.yandex.practicum.tasktracker.manager.InMemoryTaskManager;
import ru.yandex.practicum.tasktracker.model.Subtask;
import ru.yandex.practicum.tasktracker.model.Task;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.tasktracker.utils.Managers;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryTaskManagerTest {


    @Test
    void addTaskTest() {
        InMemoryTaskManager taskManager = Managers.getDefault();
        Task task = new Task();
        taskManager.addTask(task);
        assertNotNull(task, "Задача не найдена.");
        assertEquals(taskManager.getTaskById(0),taskManager.getTaskById(0),"Задачи не одинаковые.");
    }

    @Test
    void deleteTaskTest() {
        InMemoryTaskManager taskManager = Managers.getDefault();
        Task task = new Task();
        taskManager.addTask(task);
        taskManager.deleteTask(task.getId());
        List list = new ArrayList();
        assertEquals(list, taskManager.getTasks());
    }

    @Test
    void deleteAllTasksTest() {
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
    void addEpicTest() {
        InMemoryTaskManager taskManager = Managers.getDefault();
        Epic epic = new Epic();
        taskManager.addEpic(epic);
        assertNotNull(epic, "Задача не найдена.");
        assertEquals(taskManager.getEpicById(1),taskManager.getEpicById(1),"Задачи не одинаковые.");
    }

    @Test
    void deleteEpicTest() {
        InMemoryTaskManager taskManager = Managers.getDefault();
        Epic epic = new Epic();
        taskManager.addEpic(epic);
        taskManager.deleteEpic(epic);
        assertNull(taskManager.getEpicById(1));
    }

    @Test
    void deleteAllEpicsTest() {
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
    void addSubTaskTest() {
        InMemoryTaskManager taskManager = Managers.getDefault();
        Epic epic = new Epic();
        taskManager.addEpic(epic);
        Subtask subtask1 = new Subtask();
        taskManager.addSubTask(subtask1);
        assertNotNull(taskManager.getSubtasks());
        assertEquals(1, taskManager.getSubtasks().size());
    }

    @Test
    void deleteAllSubTasksTest() {
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

    @Test
    void deletedSubtaskShouldNotMadeIdFreeToUseTest(){
        InMemoryTaskManager taskManager = Managers.getDefault();
        Epic epic = new Epic();
        taskManager.addEpic(epic);
        Subtask subtask1 = new Subtask();
        taskManager.addSubTask(subtask1);
        subtask1.setEpicId(epic.getId());
        Subtask subtask2 = new Subtask();
        taskManager.addSubTask(subtask2);
        subtask2.setEpicId(epic.getId());
        Subtask subtask3 = new Subtask();
        taskManager.addSubTask(subtask3);
        subtask3.setEpicId(epic.getId());
        taskManager.deleteSubTask(subtask2.getId());
        Subtask subtask4 = new Subtask();
        taskManager.addSubTask(subtask4);
        subtask4.setEpicId(epic.getId());
        assertEquals(4,subtask4.getId());
    }
}