import ru.yandex.practicum.tasktracker.manager.InMemoryTaskManager;
import ru.yandex.practicum.tasktracker.model.Task;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.tasktracker.utils.Managers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void addNewTaskTest() {
        InMemoryTaskManager taskManager = Managers.getDefault();
        Task task = new Task();
        taskManager.addTask(task);
        final Task savedTask = taskManager.getTaskById(task.getId());
        final List<Task> taskers = taskManager.getTasks();

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");
        assertNotNull(taskers, "Задачи не возвращаются.");
        assertEquals(1, taskers.size(), "Неверное количество задач.");
        assertEquals(task, taskers.get(0), "Задачи не совпадают.");
    }
    @Test
    void equalTasksAreEqualIfIDEqualTest(){
        InMemoryTaskManager taskManager = Managers.getDefault();
        Task task = new Task();
        taskManager.addTask(task);
        final List<Task> taskers = taskManager.getTasks();
        assertEquals(taskers.get(0), taskers.get(0), "Задачи не совпадают.");
    }
}