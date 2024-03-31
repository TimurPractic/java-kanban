import ru.yandex.practicum.tasktracker.model.Task;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.tasktracker.utils.Managers;
import ru.yandex.practicum.tasktracker.manager.InMemoryHistoryManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    @Test
    void addTest() {
        InMemoryHistoryManager historyManager = Managers.getDefaultHistory();
        Task task = new Task();
        historyManager.add(task);
        final List<Task> history = historyManager.getTasks();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");
    }
}