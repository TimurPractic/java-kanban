import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.tasktracker.handlers.EpicHandler;
import ru.yandex.practicum.tasktracker.manager.HttpTaskServer;
import ru.yandex.practicum.tasktracker.manager.InMemoryTaskManager;
import ru.yandex.practicum.tasktracker.model.Epic;
import ru.yandex.practicum.tasktracker.model.Subtask;
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

class EpicHandlerTest {

    private static HttpTaskServer httpTaskServer;
    private static final String HOSTNAME = "localhost";
    private static final int PORT = 8080;
    private static InMemoryTaskManager taskManager;
    private static Gson gson = new Gson();

    @BeforeAll
    static void setUp() throws IOException {
        taskManager = Managers.getDefault();
        httpTaskServer = new HttpTaskServer(taskManager);
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
    void addNewEpicTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        Epic epic = new Epic();
        epic.setTitle("Epic Test");
        epic.setDescription("Epic Description");
        String epicJson = gson.toJson(epic);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + PORT + "/epics"))
                .POST(HttpRequest.BodyPublishers.ofString(epicJson))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Код ответа: " + response.statusCode());
        System.out.println("Тело ответа: " + response.body());

        assertEquals(201, response.statusCode(), "Failed to add new epic.");

        List<Epic> epics = taskManager.getEpics();
        assertNotNull(epics, "Epics list is null.");
        assertEquals(1, epics.size(), "Incorrect number of epics.");
        assertEquals(epic.getTitle(), epics.get(0).getTitle(), "Epic title does not match.");
        assertEquals(epic.getDescription(), epics.get(0).getDescription(), "Epic description does not match.");
    }

    @Test
    void getEpicSubtasksTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        Epic epic = new Epic();
        taskManager.addEpic(epic);
        Subtask subtask = new Subtask();
        subtask.setEpicId(epic.getId());
        taskManager.addSubTask(subtask);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + PORT + "/epics/" + epic.getId() + "/subtasks"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode(), "Failed to get epic subtasks.");

        Subtask[] subtasks = gson.fromJson(response.body(), Subtask[].class);
        assertNotNull(subtasks, "Subtasks list is null.");
        assertEquals(1, subtasks.length, "Incorrect number of subtasks.");
        assertEquals(subtask.getId(), subtasks[0].getId(), "Subtask ID does not match.");
    }

    @Test
    void deleteEpicTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        Epic epic = new Epic();
        taskManager.addEpic(epic);
        int epicId = epic.getId();

        HttpRequest deleteRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + PORT + "/epics/" + epicId))
                .DELETE()
                .build();

        HttpResponse<String> deleteResponse = client.send(deleteRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(204, deleteResponse.statusCode(), "Failed to delete epic.");

        Epic deletedEpic = taskManager.getEpicById(epicId);
        assertNull(deletedEpic, "Epic was not deleted.");
    }
}
