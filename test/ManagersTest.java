import ru.yandex.practicum.tasktracker.manager.HistoryManager;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.tasktracker.utils.Managers;
import ru.yandex.practicum.tasktracker.manager.TaskManager;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ManagersTest {

    @Test
    void shouldReturnNotNullHistoryManager() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        assertNotNull(historyManager);
    }

    @Test
    void shouldReturnNotNullTaskManager() {
        TaskManager taskManager = Managers.getDefault();
        assertNotNull(taskManager);
    }
}