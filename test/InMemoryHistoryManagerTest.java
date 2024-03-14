import ru.yandex.practicum.tasktracker.model.Task;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.tasktracker.manager.HistoryManager;
import ru.yandex.practicum.tasktracker.utils.Managers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    @Test
    void addTest() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        Task task = new Task();
        historyManager.add(task);
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");
    }
}