import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    @Test
    void addyTest() {
        InMemoryTaskManager taskManager = Managers.getDefault();
        HistoryManager historyManager = taskManager.getHistoryManager();
        Task task = new Task("Test addNewTask");
        historyManager.add(task);
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");
    }

    @Test
    void clearHistoryTest() {
        InMemoryTaskManager taskManager = Managers.getDefault();
        HistoryManager historyManager = taskManager.getHistoryManager();
        Task task = new Task("Test addNewTask");
        historyManager.add(task);
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        historyManager.clearHistory();
        assertNotNull(history, "История пустая.");
    }

}