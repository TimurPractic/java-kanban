import org.junit.jupiter.api.BeforeEach;
import ru.yandex.practicum.tasktracker.model.Epic;
import ru.yandex.practicum.tasktracker.manager.InMemoryTaskManager;
import ru.yandex.practicum.tasktracker.model.Subtask;
import ru.yandex.practicum.tasktracker.model.Task;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.tasktracker.utils.Managers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InMemoryTaskManagerTest {

    InMemoryTaskManager taskManager;


    @BeforeEach
    void setUp() {
        taskManager = Managers.getDefault();
    }

    @Test
    void addTaskTest() {
        Task task = new Task();
        taskManager.addTask(task);
        assertNotNull(task, "Задача не найдена.");
        assertEquals(taskManager.getTaskById(0),taskManager.getTaskById(0),"Задачи не одинаковые.");
    }

    @Test
    void deleteTaskTest() {
        Task task = new Task();
        taskManager.addTask(task);
        taskManager.deleteTask(task.getId());
        List list = new ArrayList();
        assertEquals(list, taskManager.getTasks());
    }

    @Test
    void deleteAllTasksTest() {
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
        Epic epic = new Epic();
        taskManager.addEpic(epic);
        assertNotNull(epic, "Задача не найдена.");
        assertEquals(taskManager.getEpicById(1),taskManager.getEpicById(1),"Задачи не одинаковые.");
    }

    @Test
    void deleteEpicTest() {
        Epic epic = new Epic();
        taskManager.addEpic(epic);
        taskManager.deleteEpic(epic);
        assertNull(taskManager.getEpicById(1));
    }

    @Test
    void deleteAllEpicsTest() {
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
        Epic epic = new Epic();
        taskManager.addEpic(epic);
        Subtask subtask1 = new Subtask();
        subtask1.setStartTime(LocalDateTime.of(2024,5,14,20,10));
        subtask1.setDuration(10);
        taskManager.addSubTask(subtask1);
        assertNotNull(taskManager.getSubtasks());
        assertEquals(1, taskManager.getSubtasks().size());
    }

    @Test
    void deleteAllSubTasksTest() {
        Epic epic = new Epic();
        taskManager.addEpic(epic);
        Subtask subtask1 = new Subtask();
        subtask1.setStartTime(LocalDateTime.of(2024,5,14,20,10));
        subtask1.setDuration(10);
        taskManager.addSubTask(subtask1);
        Subtask subtask2 = new Subtask();
        subtask2.setStartTime(LocalDateTime.of(2024,5,14,20,25));
        subtask2.setDuration(11);
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
        Epic epic = new Epic();
        taskManager.addEpic(epic);

        Subtask subtask1 = new Subtask();
        subtask1.setTitle("Subtask1");
        subtask1.setDescription("This is subtask1");
        subtask1.setStartTime(LocalDateTime.of(2024,5,14,20,0));
        subtask1.setDuration(10);
        taskManager.addSubTask(subtask1);
        subtask1.setEpicId(epic.getId());
        taskManager.checkEpicStatus(epic);
        taskManager.setEpicDuration(epic);

        Subtask subtask2 = new Subtask();
        subtask2.setTitle("Subtask2");
        subtask2.setDescription("This is subtask2");
        subtask2.setStartTime(LocalDateTime.of(2024,5,14,20,11));
        subtask2.setDuration(10);
        taskManager.addSubTask(subtask2);
        subtask2.setEpicId(epic.getId());
        taskManager.checkEpicStatus(epic);
        taskManager.setEpicDuration(epic);

        Subtask subtask3 = new Subtask();
        subtask3.setTitle("Subtask3");
        subtask3.setDescription("This is subtask3");
        subtask3.setStartTime(LocalDateTime.of(2024,5,14,20,22));
        subtask3.setDuration(10);
        taskManager.addSubTask(subtask3);
        subtask3.setEpicId(epic.getId());
        taskManager.checkEpicStatus(epic);
        taskManager.setEpicDuration(epic);

        taskManager.deleteSubTask(subtask2.getId());
        taskManager.checkEpicStatus(epic);

        Subtask subtask4 = new Subtask();
        subtask4.setStartTime(LocalDateTime.of(2024,5,15,20,22));
        subtask4.setDuration(10);
        taskManager.addSubTask(subtask4);
        subtask4.setEpicId(epic.getId());
        assertEquals(4,subtask4.getId());
    }

    @Test
    void newSubtaskShouldBeCheckedForTimeTest(){
        Epic epic = new Epic();
        taskManager.addEpic(epic);

        Subtask subtask1 = new Subtask();
        subtask1.setTitle("Subtask1");
        subtask1.setDescription("This is subtask1");
        subtask1.setStartTime(LocalDateTime.of(2024,5,14,20,0));
        subtask1.setDuration(10);
        taskManager.addSubTask(subtask1);
        subtask1.setEpicId(epic.getId());
        taskManager.checkEpicStatus(epic);
        taskManager.setEpicDuration(epic);

        Subtask subtask2 = new Subtask();
        subtask2.setTitle("Subtask2");
        subtask2.setDescription("This is subtask2");
        subtask2.setStartTime(LocalDateTime.of(2024,5,14,20,11));
        subtask2.setDuration(10);
        taskManager.addSubTask(subtask2);
        subtask2.setEpicId(epic.getId());
        taskManager.checkEpicStatus(epic);
        taskManager.setEpicDuration(epic);

        Subtask subtask3 = new Subtask();
        subtask3.setTitle("Subtask3");
        subtask3.setDescription("This is subtask3");
        subtask3.setStartTime(LocalDateTime.of(2024,5,14,20,19));
        subtask3.setDuration(10);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            taskManager.addSubTask(subtask3);
        });
        String expectedMessage = "Новый субтаск не может быть в тот же период что и уже существующие";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

        subtask3.setEpicId(epic.getId());
        taskManager.checkEpicStatus(epic);
        taskManager.setEpicDuration(epic);
    }
}