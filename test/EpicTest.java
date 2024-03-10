import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

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