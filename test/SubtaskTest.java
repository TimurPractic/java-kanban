import org.junit.jupiter.api.Test;

import java.util.HashMap;

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
        final HashMap<Integer, Subtask> subtaskers = taskManager.getSubtasks();
        assertEquals(subtaskers.get(2), subtaskers.get(2), "Задачи не совпадают.");
        assertNotEquals(subtaskers.get(2), subtaskers.get(3), "Задачи совпадают.");
    }


}