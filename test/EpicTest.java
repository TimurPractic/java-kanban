import ru.yandex.practicum.tasktracker.model.Epic;
import ru.yandex.practicum.tasktracker.manager.InMemoryTaskManager;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.tasktracker.model.Subtask;
import ru.yandex.practicum.tasktracker.model.TaskStatus;
import ru.yandex.practicum.tasktracker.utils.Managers;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EpicTest {

    @Test
    void equalEpicsAreEqualIfIDEqualTest(){
        InMemoryTaskManager taskManager = Managers.getDefault();
        Epic epic = new Epic();
        taskManager.addEpic(epic);
        final List<Epic> epicers = taskManager.getEpics();
        assertEquals(epicers.get(0), epicers.get(0), "Задачи не совпадают.");
    }


    @Test
    void addEpicTest() {
        InMemoryTaskManager taskManager = Managers.getDefault();
        Epic epic = new Epic();
        taskManager.addEpic(epic);
        assertNotNull(taskManager.getEpicById(0), "Задача не найдена.");
        assertEquals(epic, taskManager.getEpicById(0), "Задачи не совпадают.");
        final List<Epic> tasks = taskManager.getEpics();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(epic, tasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void epicStatusSubTasksNewTest() {
        InMemoryTaskManager taskManager = Managers.getDefault();
        Epic epic = new Epic();
        epic.setTitle("Epic1");
        epic.setStatus(TaskStatus.NEW);
        epic.setDescription("This is epic");

        epic.setStartTime(LocalDateTime.of(2024,5,14,20,0));
        epic.setDuration(0);
        taskManager.addEpic(epic);

        Subtask subtask1 = new Subtask();
        subtask1.setEpicId(epic.getId());
        subtask1.setTitle("Subtask1");
        subtask1.setStatus(TaskStatus.NEW);
        subtask1.setDescription("This is subtask1");
        subtask1.setStartTime(LocalDateTime.of(2024,5,14,20,0));
        subtask1.setDuration(10);
        taskManager.addSubTask(subtask1);

        Subtask subtask2 = new Subtask();
        subtask2.setEpicId(epic.getId());
        subtask2.setTitle("Subtask2");
        subtask2.setDescription("This is subtask2");
        subtask2.setStartTime(LocalDateTime.of(2024,5,14,20,15));
        subtask2.setDuration(11);
        taskManager.addSubTask(subtask2);
        subtask2.setStatus(TaskStatus.NEW);

        assertEquals(TaskStatus.NEW, epic.getStatus(),"Неверный статус эпика");
    }

    @Test
    void epicStatusSubTaskInProgressTest() {
        InMemoryTaskManager taskManager = Managers.getDefault();
        Epic epic = new Epic();
        epic.setTitle("Epic1");
        epic.setStatus(TaskStatus.NEW);
        epic.setDescription("This is epic");
        epic.setStartTime(LocalDateTime.of(2024,5,14,20,0));
        epic.setDuration(0);
        taskManager.addEpic(epic);

        Subtask subtask1 = new Subtask();
        subtask1.setEpicId(epic.getId());
        subtask1.setTitle("Subtask1");
        subtask1.setStatus(TaskStatus.NEW);
        subtask1.setDescription("This is subtask1");
        subtask1.setStartTime(LocalDateTime.of(2024,5,14,20,0));
        subtask1.setDuration(10);
        taskManager.addSubTask(subtask1);

        Subtask subtask2 = new Subtask();
        subtask2.setEpicId(epic.getId());
        subtask2.setTitle("Subtask2");
        subtask2.setDescription("This is subtask2");
        subtask2.setStartTime(LocalDateTime.of(2024,5,14,20,15));
        subtask2.setDuration(11);
        taskManager.addSubTask(subtask2);
        subtask2.setStatus(TaskStatus.IN_PROGRESS);

        taskManager.updateSubTask(subtask2);

        assertEquals(TaskStatus.IN_PROGRESS,epic.getStatus(), "Неверный статус эпика");
    }

    @Test
    void epicStatusSubTasksDoneTest() {
        InMemoryTaskManager taskManager = Managers.getDefault();
        Epic epic = new Epic();
        epic.setTitle("Epic1");
        epic.setStatus(TaskStatus.NEW);
        epic.setDescription("This is epic");
        epic.setStartTime(LocalDateTime.of(2024,5,14,20,0));
        epic.setDuration(0);
        taskManager.addEpic(epic);

        Subtask subtask1 = new Subtask();
        subtask1.setEpicId(epic.getId());
        subtask1.setTitle("Subtask1");
        subtask1.setDescription("This is subtask1");
        subtask1.setStartTime(LocalDateTime.of(2024,5,14,20,0));
        subtask1.setDuration(10);
        taskManager.addSubTask(subtask1);
        subtask1.setStatus(TaskStatus.DONE);

        Subtask subtask2 = new Subtask();
        subtask2.setEpicId(epic.getId());
        subtask2.setTitle("Subtask2");
        subtask2.setDescription("This is subtask2");
        subtask2.setStartTime(LocalDateTime.of(2024,5,14,20,15));
        subtask2.setDuration(11);
        taskManager.addSubTask(subtask2);
        subtask2.setStatus(TaskStatus.DONE);

        taskManager.updateSubTask(subtask1);
        taskManager.updateSubTask(subtask2);

        assertEquals(TaskStatus.DONE, epic.getStatus(), "Неверный статус эпика");
    }

    @Test
    void epicStatusSubTasksDoneEpicInProgressTest() {
        InMemoryTaskManager taskManager = Managers.getDefault();
        Epic epic = new Epic();
        epic.setTitle("Epic1");
        epic.setStatus(TaskStatus.NEW);
        epic.setDescription("This is epic");
        epic.setStartTime(LocalDateTime.of(2024,5,14,20,0));
        epic.setDuration(0);
        taskManager.addEpic(epic);

        Subtask subtask1 = new Subtask();
        subtask1.setEpicId(epic.getId());
        subtask1.setTitle("Subtask1");
        subtask1.setDescription("This is subtask1");
        subtask1.setStartTime(LocalDateTime.of(2024,5,14,20,0));
        subtask1.setDuration(10);
        taskManager.addSubTask(subtask1);
        taskManager.checkEpicStatus(epic);

        Subtask subtask2 = new Subtask();
        subtask2.setEpicId(epic.getId());
        subtask2.setTitle("Subtask2");
        subtask2.setDescription("This is subtask2");
        subtask2.setStartTime(LocalDateTime.of(2024,5,14,20,15));
        subtask2.setDuration(11);
        taskManager.addSubTask(subtask2);
        subtask2.setStatus(TaskStatus.DONE);
        taskManager.checkEpicStatus(epic);

        taskManager.updateSubTask(subtask1);
        taskManager.updateSubTask(subtask2);
        taskManager.checkEpicStatus(epic);

        assertEquals(TaskStatus.IN_PROGRESS, epic.getStatus(), "Неверный статус эпика");
    }

    @Test
    void checkSizeEpicWithSubTasksTest() {
        InMemoryTaskManager taskManager = Managers.getDefault();
        Epic epic = new Epic();
        epic.setTitle("Epic1");
        epic.setStatus(TaskStatus.NEW);
        epic.setDescription("This is epic");
        epic.setStartTime(LocalDateTime.of(2024,5,14,20,0));
        epic.setDuration(0);
        taskManager.addEpic(epic);

        Subtask subtask1 = new Subtask();
        subtask1.setEpicId(epic.getId());
        subtask1.setTitle("Subtask1");
        subtask1.setDescription("This is subtask1");
        subtask1.setStartTime(LocalDateTime.of(2024,5,14,20,0));
        subtask1.setDuration(10);
        taskManager.addSubTask(subtask1);

        Subtask subtask2 = new Subtask();
        subtask2.setEpicId(epic.getId());
        subtask2.setTitle("Subtask2");
        subtask2.setDescription("This is subtask2");
        subtask2.setStartTime(LocalDateTime.of(2024,5,14,20,15));
        subtask2.setDuration(11);
        taskManager.addSubTask(subtask2);
        subtask2.setStatus(TaskStatus.DONE);

        assertEquals(2, taskManager.getSubtasks().size(), "Подзадача не записывается в HashMap");
        assertEquals(2, epic.getSubtasksIds().size(), "Подзадача не привязывается к Эпику");
    }

    @Test
    void lifespanOfEpicTest() {
        InMemoryTaskManager taskManager = Managers.getDefault();
        Epic epic = new Epic();
        epic.setTitle("Epic1");
        epic.setStatus(TaskStatus.NEW);
        epic.setDescription("This is epic");
        epic.setStartTime(LocalDateTime.of(2024,5,14,20,0));
        epic.setDuration(0);
        taskManager.addEpic(epic);

        Subtask subtask1 = new Subtask();
        subtask1.setEpicId(epic.getId());
        subtask1.setTitle("Subtask1");
        subtask1.setDescription("This is subtask1");
        subtask1.setStartTime(LocalDateTime.of(2024,5,14,20,0));
        subtask1.setDuration(10);
        taskManager.addSubTask(subtask1);
        subtask1.setStatus(TaskStatus.DONE);

        Subtask subtask2 = new Subtask();
        subtask2.setEpicId(epic.getId());
        subtask2.setTitle("Subtask2");
        subtask2.setDescription("This is subtask2");
        subtask2.setStartTime(LocalDateTime.of(2024,5,14,20,15));
        subtask2.setDuration(11);
        taskManager.addSubTask(subtask2);
        subtask2.setStatus(TaskStatus.DONE);
        taskManager.setEpicDuration(epic);

        assertEquals(subtask2.getEndTime(), taskManager.getEpicEndTime(epic), "Неверное время завершения эпика");
        assertEquals(subtask1.getStartTime(), epic.getStartTime(), "Неверное время начала эпика");
        assertEquals(21, epic.getDuration().toMinutes(), "Неверная продолжительность эпика");
    }
}