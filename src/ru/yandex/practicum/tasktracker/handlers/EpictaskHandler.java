package ru.yandex.practicum.tasktracker.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import ru.yandex.practicum.tasktracker.manager.BaseHttpHandler;
import ru.yandex.practicum.tasktracker.manager.TaskManager;

import java.io.IOException;

public class EpictaskHandler extends BaseHttpHandler {
    private final TaskManager taskManager;
    private final Gson gson;

    public EpictaskHandler(TaskManager taskManager, Gson gson) {
        this.taskManager = taskManager;
        this.gson = gson;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            String response = gson.toJson(taskManager.getTasks());
            sendText(exchange, response);
        } else {
            exchange.sendResponseHeaders(405, -1); // Метод не разрешен
        }
    }
}
