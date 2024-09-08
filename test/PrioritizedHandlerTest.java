import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.tasktracker.manager.HttpTaskServer;
import ru.yandex.practicum.tasktracker.manager.InMemoryHistoryManager;
import ru.yandex.practicum.tasktracker.manager.InMemoryTaskManager;
import ru.yandex.practicum.tasktracker.model.Task;
import ru.yandex.practicum.tasktracker.utils.Adapters;
import ru.yandex.practicum.tasktracker.utils.Managers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PrioritizedHandlerTest {

    private static HttpTaskServer httpTaskServer;
    private static final String HOSTNAME = "localhost";
    private static final int PORT = 8080;
    private static InMemoryTaskManager taskManager;
    private static InMemoryHistoryManager historyManager;
    private static Gson gson = new Gson();

    @BeforeAll
    static void setUp() throws IOException {
        taskManager = Managers.getDefault();
        historyManager = Managers.getDefaultHistory();
        httpTaskServer = new HttpTaskServer(taskManager, historyManager);
        taskManager.deleteAllTasks();
        taskManager.deleteAllSubTasks();
        taskManager.deleteAllEpics();
        httpTaskServer.start();

        // Настройка Gson с адаптерами
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new Adapters.LocalDateTimeAdapter())
                .registerTypeAdapter(Duration.class, new Adapters.DurationAdapter())
                .create();
    }

    @AfterAll
    static void tearDown() {
        httpTaskServer.stop();
    }

    @Test
    void getPrioritizedTasksTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        // Создаем несколько задач с разными временами начала
        Task task1 = new Task();
        task1.setDuration(30);
        task1.setStartTime(LocalDateTime.now().plusDays(1));
        task1.setTitle("Task 1");
        task1.setDescription("Description 1");
        taskManager.addTask(task1);

        Task task2 = new Task();
        task2.setDuration(45);
        task2.setStartTime(LocalDateTime.now().plusHours(1));
        task2.setTitle("Task 2");
        task2.setDescription("Description 2");
        taskManager.addTask(task2);

        Task task3 = new Task();
        task3.setDuration(60);
        task3.setStartTime(LocalDateTime.now().plusDays(2));
        task3.setTitle("Task 3");
        task3.setDescription("Description 3");
        taskManager.addTask(task3);

        // Отправляем GET-запрос для получения приоритетных задач
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + PORT + "/prioritized"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode(), "Failed to get prioritized tasks.");

        Task[] tasks = gson.fromJson(response.body(), Task[].class);
        assertNotNull(tasks, "Tasks list is null.");
        assertEquals(3, tasks.length, "Incorrect number of tasks.");

        // Проверяем, что задачи отсортированы по времени начала
        assertEquals(task2.getId(), tasks[0].getId(), "Tasks are not sorted by start time.");
        assertEquals(task1.getId(), tasks[1].getId(), "Tasks are not sorted by start time.");
        assertEquals(task3.getId(), tasks[2].getId(), "Tasks are not sorted by start time.");
    }
}
