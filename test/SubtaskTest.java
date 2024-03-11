import ru.yandex.practicum.TaskTracker.TasksManager.Epic;
import ru.yandex.practicum.TaskTracker.TasksManager.InMemoryTaskManager;
import ru.yandex.practicum.TaskTracker.TasksManager.Subtask;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.TaskTracker.utils.Managers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {

    @Test
    void equalSubTasksAreEqualIfIDEqualTest(){
        InMemoryTaskManager taskManager = Managers.getDefault();
        Epic epic = new Epic("Test addNewEpic");
        taskManager.addEpic(epic);
        Subtask subtask1 = new Subtask("Это первая подзадача", epic.getId());
        taskManager.addSubTask(subtask1);
        Subtask subtask2 = new Subtask("Это вторая подзадача", epic.getId());
        taskManager.addSubTask(subtask2);
        final List<Subtask> subtaskers = taskManager.getSubtasks();
        assertEquals(subtaskers.get(1), subtaskers.get(1), "Задачи не совпадают.");
        assertNotEquals(subtaskers.get(1), subtaskers.get(0), "Задачи совпадают.");
    }


}