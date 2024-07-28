package ru.yandex.practicum.tasktracker.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpServer;
import ru.yandex.practicum.tasktracker.handlers.EpictaskHandler;
import ru.yandex.practicum.tasktracker.handlers.HistoryHandler;
//import ru.yandex.practicum.tasktracker.handlers.PrioritizedHandler;
import ru.yandex.practicum.tasktracker.handlers.SubtaskHandler;
import ru.yandex.practicum.tasktracker.handlers.TaskHandler;
import ru.yandex.practicum.tasktracker.model.Task;
import ru.yandex.practicum.tasktracker.utils.Adapters;
import ru.yandex.practicum.tasktracker.utils.Managers;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class HttpTaskServer {

    public static void main(String[] args) {
        InMemoryTaskManager taskManager = Managers.getDefault();
        HistoryManager historyManager = Managers.getDefaultHistory();
        HttpTaskServer server = new HttpTaskServer(taskManager);

//        Task task = new Task();
//        taskManager.addTask(task);
//        final Task savedTask = taskManager.getTaskById(task.getId());
//        final List<Task> taskers = taskManager.getTasks();

        server.start();
    }

    private Gson gson;
    private TaskManager taskManager;
    private HistoryManager historyManager;
    private HttpServer httpServer;
    private static final String HOSTNAME = "localhost";
    private static final int PORT = 8080;

    public HttpTaskServer(TaskManager taskManager) {
        this.taskManager = taskManager;
        gson = new GsonBuilder()
                .registerTypeAdapter(Duration.class, new Adapters().new DurationAdapter())
                .registerTypeAdapter(LocalDateTime.class, new Adapters().new LocalDateTimeAdapter())
                .create();
        try {
            httpServer = HttpServer.create(new InetSocketAddress(HOSTNAME, PORT), 0);
            httpServer.createContext("/tasks", new TaskHandler(taskManager, gson));
//            httpServer.createContext("/subtasks", new SubtaskHandler(taskManager, gson));
//            httpServer.createContext("/epics", new EpictaskHandler(taskManager, gson));
//            httpServer.createContext("/history", new HistoryHandler(historyManager, gson));
//            httpServer.createContext("/prioritized", new PrioritizedHandler(historyManager, gson));
        } catch (IOException e) {
            throw new RuntimeException("FAIL: Ошибка создания http-сервера на порте " + PORT + ".", e);
        }
    }

    public void start() {
        httpServer.start();
        System.out.println("OK: Сервер на порте " + PORT + " запущен.");
    }

    public void stop() {
        httpServer.stop(0);
        System.out.println("OK: Сервер на порте " + PORT + " остановлен.");
    }
}
