import ru.yandex.practicum.tasktracker.model.Epic;
import ru.yandex.practicum.tasktracker.manager.InMemoryTaskManager;
import ru.yandex.practicum.tasktracker.model.Subtask;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.tasktracker.utils.Managers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {

    @Test
    void equalSubTasksAreEqualIfIDEqualTest(){
        InMemoryTaskManager taskManager = Managers.getDefault();
        Epic epic = new Epic();
        taskManager.addEpic(epic);
        Subtask subtask1 = new Subtask();
        taskManager.addSubTask(subtask1);
        Subtask subtask2 = new Subtask();
        taskManager.addSubTask(subtask2);
        final List<Subtask> subtaskers = taskManager.getSubtasks();
        assertEquals(subtaskers.get(1), subtaskers.get(1), "Задачи не совпадают.");
        assertNotEquals(subtaskers.get(1), subtaskers.get(0), "Задачи совпадают.");
    }


}