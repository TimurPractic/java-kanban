import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.tasktracker.handlers.HistoryHandler;
import ru.yandex.practicum.tasktracker.manager.HttpTaskServer;
import ru.yandex.practicum.tasktracker.manager.InMemoryHistoryManager;
import ru.yandex.practicum.tasktracker.manager.InMemoryTaskManager;
import ru.yandex.practicum.tasktracker.model.Task;
import ru.yandex.practicum.tasktracker.model.TaskStatus;
import ru.yandex.practicum.tasktracker.utils.Adapters;
import ru.yandex.practicum.tasktracker.utils.Managers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class HistoryHandlerTest {

    private static HttpTaskServer httpTaskServer;
    private static final String HOSTNAME = "localhost";
    private static final int PORT = 8080;
    private static InMemoryTaskManager taskManager;
    private static InMemoryHistoryManager historyManager;
    private static Gson gson = new Gson();

    @BeforeEach
    void setUp() throws IOException {
        taskManager = Managers.getDefault();
        historyManager = Managers.getDefaultHistory();
        httpTaskServer = new HttpTaskServer(taskManager);

        // Настройка Gson с адаптерами
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new Adapters.LocalDateTimeAdapter())
                .registerTypeAdapter(Duration.class, new Adapters.DurationAdapter())
                .create();

        httpTaskServer.start();
    }

    @AfterEach
    void tearDown() {
        httpTaskServer.stop();
    }

    @Test
    void getHistoryTest() throws IOException, InterruptedException {
        // Создаем и добавляем задачу в историю
        Task task = new Task();
        task.setId(1);
        task.setTitle("Test Task");
        task.setDescription("Testing task");
        task.setStatus(TaskStatus.NEW);
        task.setDuration(5);
        task.setStartTime(LocalDateTime.now().withNano(0));
        historyManager.add(task);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + PORT + "/history"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode(), "Failed to get history.");

        Task[] tasks = gson.fromJson(response.body(), Task[].class);
        assertNotNull(tasks, "History list is null.");
        assertEquals(1, tasks.length, "Incorrect number of tasks in history.");
        assertEquals(task.getId(), tasks[0].getId(), "Task ID does not match.");
        assertEquals(task.getTitle(), tasks[0].getTitle(), "Task title does not match.");
        assertEquals(task.getDescription(), tasks[0].getDescription(), "Task description does not match.");
        assertEquals(task.getStatus(), tasks[0].getStatus(), "Task status does not match.");
        assertEquals(task.getDuration(), tasks[0].getDuration(), "Task duration does not match.");
        assertEquals(task.getStartTime(), tasks[0].getStartTime(), "Task start time does not match.");
    }
}
