import ru.yandex.practicum.tasktracker.model.Epic;
import ru.yandex.practicum.tasktracker.manager.InMemoryTaskManager;
import ru.yandex.practicum.tasktracker.model.Subtask;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.tasktracker.utils.Managers;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class SubtaskTest {

    @Test
    void equalSubTasksAreEqualIfIDEqualTest(){
        InMemoryTaskManager taskManager = Managers.getDefault();
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
        final List<Subtask> subtaskers = taskManager.getSubtasks();
        assertEquals(subtaskers.get(1), subtaskers.get(1), "Задачи не совпадают.");
        assertNotEquals(subtaskers.get(1), subtaskers.get(0), "Задачи совпадают.");
    }


}