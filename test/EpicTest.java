import ru.yandex.practicum.tasktracker.model.Epic;
import ru.yandex.practicum.tasktracker.manager.InMemoryTaskManager;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.tasktracker.utils.Managers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EpicTest {

    @Test
    void equalEpicsAreEqualIfIDEqualTest(){
        InMemoryTaskManager taskManager = Managers.getDefault();
        Epic epic = new Epic();
        taskManager.addEpic(epic);
        final List<Epic> epicers = taskManager.getEpics();
        assertEquals(epicers.get(0), epicers.get(0), "Задачи не совпадают.");
    }
}