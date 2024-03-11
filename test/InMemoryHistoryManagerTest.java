import ru.yandex.practicum.TaskTracker.TasksManager.InMemoryTaskManager;
import ru.yandex.practicum.TaskTracker.TasksManager.Task;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.TaskTracker.HistoryManager.HistoryManager;
import ru.yandex.practicum.TaskTracker.utils.Managers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    @Test
    void addTest() {
        InMemoryTaskManager taskManager = Managers.getDefault();
        HistoryManager historyManager = Managers.getDefaultHistory();
        Task task = new Task("Test addNewTask");
        historyManager.add(task);
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");
    }
}