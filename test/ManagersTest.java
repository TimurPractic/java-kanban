import ru.yandex.practicum.tasktracker.manager.HistoryManager;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.tasktracker.utils.Managers;
import ru.yandex.practicum.tasktracker.manager.TaskManager;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ManagersTests {

    @Test
    void shouldReturnNotNullHistoryManagerWhenInvokeGetDefaultHistory() {
        HistoryManager historyManager = Managers.getDefaultHistory();

        assertNotNull(historyManager);
    }

    @Test
    void shouldReturnNotNullHistoryManagerWhenInvokeGetDefault() {
        TaskManager taskManager = Managers.getDefault();

        assertNotNull(taskManager);
    }
}