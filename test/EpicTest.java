import ru.yandex.practicum.TaskTracker.TasksManager.Epic;
import ru.yandex.practicum.TaskTracker.TasksManager.InMemoryTaskManager;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.TaskTracker.utils.Managers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    @Test
    void equalEpicsAreEqualIfIDEqualTest(){
        InMemoryTaskManager taskManager = Managers.getDefault();
        Epic epic = new Epic("Test addNewEpic");
        taskManager.addEpic(epic);
        final List<Epic> epicers = taskManager.getEpics();
        assertEquals(epicers.get(0), epicers.get(0), "Задачи не совпадают.");
    }
}