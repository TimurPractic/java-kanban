import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    @Test
    void equalEpicsAreEqualIfIDEqualTest(){
        InMemoryTaskManager taskManager = Managers.getDefault();
        Epic epic = new Epic("Test addNewEpic");
        taskManager.addEpic(epic);
        final HashMap<Integer, Epic> epicers = taskManager.getEpics();
        assertEquals(epicers.get(1), epicers.get(1), "Задачи не совпадают.");
    }
}