import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

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