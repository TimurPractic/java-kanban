import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.tasktracker.manager.HttpTaskServer;
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

import static org.junit.jupiter.api.Assertions.*;

class TaskHandlerTest {

    private static HttpTaskServer httpTaskServer;
    private static final String HOSTNAME = "localhost";
    private static final int PORT = 8080;
    private static InMemoryTaskManager taskManager;
    private static Gson gson = new Gson();

    @BeforeEach
    void setUp() throws IOException {
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

    @AfterEach
    void tearDown() {
        httpTaskServer.stop();
    }

    @Test
    void addNewTaskTest() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        Task task = new Task();
        task.setDuration(5);
        task.setStartTime(LocalDateTime.now().withNano(0));
        task.setStatus(TaskStatus.NEW);
        task.setTitle("Test");
        task.setDescription("Description");
        String taskJson = gson.toJson(task);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + PORT + "/tasks"))
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .version(HttpClient.Version.HTTP_1_1)
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode(), "Failed to add new task.");

        Task[] tasks = taskManager.getTasks().toArray(new Task[0]);
        assertNotNull(tasks, "Tasks list is null.");
        assertEquals(1, tasks.length, "Incorrect number of tasks.");
        assertEquals(task, tasks[0], "Task does not match.");
        assertEquals(task.getTitle(), tasks[0].getTitle(), "Task title does not match.");
        assertEquals(task.getDescription(), tasks[0].getDescription(), "Task description does not match.");
        assertEquals(task.getStatus(), tasks[0].getStatus(), "Task status does not match.");
        assertEquals(task.getDuration(), tasks[0].getDuration(), "Task duration does not match.");
        assertEquals(task.getStartTime(), tasks[0].getStartTime(), "Task start time does not match.");
    }

    @Test
    void getTasksTest() throws IOException, InterruptedException {
        Task task = new Task();
        taskManager.addTask(task);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + PORT + "/tasks"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode(), "Failed to get tasks.");

        Task[] tasks = gson.fromJson(response.body(), Task[].class);
        assertNotNull(tasks, "Tasks list is null.");
        assertEquals(1, tasks.length, "Incorrect number of tasks.");
    }

    @Test
    void deleteTaskTest() throws IOException, InterruptedException {
        Task task = new Task();
        taskManager.addTask(task);
        int taskId = task.getId();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest deleteRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + PORT + "/tasks/" + taskId))
                .DELETE()
                .build();

        HttpResponse<String> deleteResponse = client.send(deleteRequest, HttpResponse.BodyHandlers.ofString());

        assertEquals(204, deleteResponse.statusCode(), "Failed to delete task.");

        Task deletedTask = taskManager.getTaskById(taskId);
        assertNull(deletedTask, "Task was not deleted.");
    }
}