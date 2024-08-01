import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.tasktracker.manager.HttpTaskServer;
import ru.yandex.practicum.tasktracker.manager.InMemoryHistoryManager;
import ru.yandex.practicum.tasktracker.manager.InMemoryTaskManager;
import ru.yandex.practicum.tasktracker.model.Epic;
import ru.yandex.practicum.tasktracker.model.Subtask;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskHandlerTest {

    private static HttpTaskServer httpTaskServer;
    private static final String HOSTNAME = "localhost";
    private static final int PORT = 8080;
    private static InMemoryTaskManager taskManager;
    private static InMemoryHistoryManager historyManager;
    private static Gson gson = new Gson();
    Subtask subtask = new Subtask();
    Epic epic = new Epic();

    @BeforeEach
    void setUp() throws IOException {
        taskManager = Managers.getDefault();
        historyManager = Managers.getDefaultHistory();
        httpTaskServer = new HttpTaskServer(taskManager, historyManager);
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new Adapters.LocalDateTimeAdapter())
                .registerTypeAdapter(Duration.class, new Adapters.DurationAdapter())
                .create();


        httpTaskServer.start();

        epic.setTitle("Epic1");
        epic.setStatus(TaskStatus.NEW);
        epic.setDescription("This is epic");
        epic.setStartTime(LocalDateTime.of(2024,5,14,20,0));
        epic.setDuration(0);
        taskManager.addEpic(epic);

        subtask.setEpicId(epic.getId());
        subtask.setTitle("Subtask1");
        subtask.setStatus(TaskStatus.NEW);
        subtask.setDescription("This is subtask1");
        subtask.setStartTime(LocalDateTime.of(2024,5,14,20,0));
        subtask.setDuration(10);
        taskManager.addSubTask(subtask);
    }

    @AfterEach
    void tearDown() {
        httpTaskServer.stop();
        taskManager.deleteAllTasks();
        taskManager.deleteAllSubTasks();
        taskManager.deleteAllEpics();
    }

    @Test
    void addNewSubtaskTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        String subtaskJson = gson.toJson(subtask);
        System.out.println(epic);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + PORT + "/subtasks"))
                .POST(HttpRequest.BodyPublishers.ofString(subtaskJson))
                .version(HttpClient.Version.HTTP_1_1)
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Код ответа: " + response.statusCode());
        System.out.println("Тело ответа: " + response.body());

        assertEquals(201, response.statusCode(), "Failed to add new subtask.");

        List<Subtask> subtasks = taskManager.getSubtasks();
        System.out.println(subtasks);
        assertNotNull(subtasks, "Subtasks list is null.");
        assertEquals(2, subtasks.size(), "Incorrect number of subtasks.");
        assertEquals(subtask, subtasks.get(0), "Subtask does not match.");
    }

    @Test
    void getSubtasksTest() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + PORT + "/subtasks"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode(), "Failed to get subtasks.");

        Subtask[] subtasks = gson.fromJson(response.body(), Subtask[].class);
        assertNotNull(subtasks, "Subtasks list is null.");
        assertEquals(1, subtasks.length, "Incorrect number of subtasks.");
        assertEquals(subtask.getId(), subtasks[0].getId(), "Subtask ID does not match.");
        assertEquals(subtask.getTitle(), subtasks[0].getTitle(), "Subtask title does not match.");
        assertEquals(subtask.getDescription(), subtasks[0].getDescription(), "Subtask description does not match.");
        assertEquals(subtask.getStatus(), subtasks[0].getStatus(), "Subtask status does not match.");
        assertEquals(subtask.getDuration(), subtasks[0].getDuration(), "Subtask duration does not match.");
        assertEquals(subtask.getStartTime(), subtasks[0].getStartTime(), "Subtask start time does not match.");
    }

    @Test
    void deleteSubtaskTest() throws IOException, InterruptedException {
        Subtask subtask = new Subtask();
        subtask.setId(11);
        subtask.setTitle("Test Subtask");
        subtask.setDescription("Testing subtask");
        subtask.setStatus(TaskStatus.NEW);
        subtask.setDuration(5);
        subtask.setStartTime(LocalDateTime.now().withNano(0));
        taskManager.addSubTask(subtask);
        int subtaskId = subtask.getId();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest deleteRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + PORT + "/subtasks/" + subtaskId))
                .DELETE()
                .build();

        HttpResponse<String> deleteResponse = client.send(deleteRequest, HttpResponse.BodyHandlers.ofString());

        assertEquals(204, deleteResponse.statusCode(), "Failed to delete subtask.");

        Subtask deletedSubtask = taskManager.getSubtaskById(subtaskId);
        assertNull(deletedSubtask, "Subtask was not deleted.");
    }
}
