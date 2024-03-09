import org.junit.jupiter.api.Test;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void addNewTaskTest() {
        InMemoryTaskManager taskManager = Managers.getDefault();
        Task task = new Task("Test addNewTask");
        taskManager.addTask(task);
        final Task savedTask = taskManager.getTaskById(task.getId());
        final HashMap<Integer, Task> taskers = taskManager.getTasks();

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");
        assertNotNull(taskers, "Задачи не возвращаются.");
        assertEquals(1, taskers.size(), "Неверное количество задач.");
        assertEquals(task, taskers.get(1), "Задачи не совпадают.");
    }
    @Test
    void equalTasksAreEqualIfIDEqualTest(){
        InMemoryTaskManager taskManager = Managers.getDefault();
        Task task = new Task("Test addNewTask");
        taskManager.addTask(task);
        final HashMap<Integer, Task> taskers = taskManager.getTasks();
        assertEquals(taskers.get(1), taskers.get(1), "Задачи не совпадают.");
    }
}