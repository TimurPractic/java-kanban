import ru.yandex.practicum.tasktracker.model.Task;
import ru.yandex.practicum.tasktracker.model.Epic;
import ru.yandex.practicum.tasktracker.model.Subtask;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import ru.yandex.practicum.tasktracker.model.TaskStatus;
import ru.yandex.practicum.tasktracker.utils.Managers;
import ru.yandex.practicum.tasktracker.manager.InMemoryHistoryManager;
import ru.yandex.practicum.tasktracker.manager.InMemoryTaskManager;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class InMemoryHistoryManagerTest {
    InMemoryTaskManager taskManager;

    @BeforeEach
    public void createManager() {
        taskManager = Managers.getDefault();
    }

    @Test
    void addTest() {
        InMemoryHistoryManager historyManager = Managers.getDefaultHistory();
        Task task = new Task();
        historyManager.add(task);
        final List<Task> history = historyManager.getTasks();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");
    }

    @Test
    void historyListAddWorksOKTest(){
        Task task1 = new Task();
        taskManager.addTask(task1);
        assertEquals(1,taskManager.historyManager.getHistory().size());
        Task task2 = new Task();
        taskManager.addTask(task2);
        assertEquals(2,taskManager.historyManager.getHistory().size());
    }

    @Test
    void historyListRemovalWorksOKTest(){
        Task task1 = new Task();
        taskManager.addTask(task1);
        System.out.println(task1);
        assertEquals(1,taskManager.historyManager.getHistory().size());
        Task task2 = new Task();
        taskManager.addTask(task2);
        System.out.println(task2);
        assertEquals(2,taskManager.historyManager.getHistory().size());
        taskManager.deleteTask(task2.getId());
        assertEquals(1,taskManager.historyManager.getHistory().size());
    }
    @Test
    void ifThreeTaskReceivedSeveralTimesHistoryListShouldContainThreeRecordInRightOrderTest() {
        Task task1 = new Task();
        taskManager.addTask(task1);
        Task task2 = new Task();
        taskManager.addTask(task2);
        Task task3 = new Task();
        taskManager.addTask(task3);
        task2.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateTask(task2);
        ArrayList<Task> rightOrder = new ArrayList<>();
        rightOrder.add(task1);
        rightOrder.add(task3);
        rightOrder.add(task2);
        assertEquals(rightOrder, taskManager.historyManager.getHistory());
    }

    @Test
    void ifThreeSubTasksInOneEpicReceivedHistoryListShouldContainThreeRecordInRightOrderTest() {
        Epic epic = new Epic();
        taskManager.addEpic(epic);
        Subtask subtask1 = new Subtask();
        subtask1.setEpicId(epic.getId());
        taskManager.addSubTask(subtask1);
        Subtask subtask2 = new Subtask();
        subtask2.setEpicId(epic.getId());
        taskManager.addSubTask(subtask2);
        Subtask subtask3 = new Subtask();
        subtask3.setEpicId(epic.getId());
        taskManager.addSubTask(subtask3);
        ArrayList<Task> rightOrder = new ArrayList<>();
        rightOrder.add(subtask1);
        rightOrder.add(subtask2);
        rightOrder.add(epic);
        rightOrder.add(subtask3);
        assertEquals(rightOrder, taskManager.historyManager.getHistory());
    }
}