package ru.yandex.practicum.tasktracker.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpServer;
import ru.yandex.practicum.tasktracker.handlers.EpicHandler;
import ru.yandex.practicum.tasktracker.handlers.HistoryHandler;
import ru.yandex.practicum.tasktracker.handlers.PrioritizedHandler;
import ru.yandex.practicum.tasktracker.handlers.SubtaskHandler;
import ru.yandex.practicum.tasktracker.handlers.TaskHandler;
import ru.yandex.practicum.tasktracker.utils.Adapters;
import ru.yandex.practicum.tasktracker.utils.Managers;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.time.LocalDateTime;

public class HttpTaskServer {

    public static void main(String[] args) {
        InMemoryTaskManager taskManager = Managers.getDefault();
        HistoryManager historyManager = Managers.getDefaultHistory();
        HttpTaskServer server = new HttpTaskServer(taskManager);

        server.start();
    }

    private Gson gson;
    private InMemoryTaskManager taskManager;
    private HistoryManager historyManager;
    private HttpServer httpServer;
    private static final String HOSTNAME = "localhost";
    private static final int PORT = 8080;

    public HttpTaskServer(InMemoryTaskManager taskManager) {
        this.taskManager = taskManager;
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new Adapters.LocalDateTimeAdapter())
                .registerTypeAdapter(Duration.class, new Adapters.DurationAdapter())
                .create();
        try {
            httpServer = HttpServer.create(new InetSocketAddress(HOSTNAME, PORT), 0);
            httpServer.createContext("/tasks", new TaskHandler(taskManager, gson));
            httpServer.createContext("/subtasks", new SubtaskHandler(taskManager, gson));
            httpServer.createContext("/epics", new EpicHandler(taskManager, gson));
            httpServer.createContext("/history", new HistoryHandler(historyManager, gson));
            httpServer.createContext("/prioritized", new PrioritizedHandler(taskManager, gson));
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
