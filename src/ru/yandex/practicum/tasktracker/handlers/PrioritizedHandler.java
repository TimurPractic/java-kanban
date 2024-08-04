package ru.yandex.practicum.tasktracker.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import ru.yandex.practicum.tasktracker.manager.BaseHttpHandler;
import ru.yandex.practicum.tasktracker.manager.TaskManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PrioritizedHandler extends BaseHttpHandler {
    private final TaskManager taskManager;
    private final Gson gson;

    public PrioritizedHandler(TaskManager taskManager, Gson gson) {
        this.taskManager = taskManager;
        this.gson = gson;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            try {
                Object history = taskManager.getPrioritizedTasks();
                sendJsonResponse(exchange,200,history);
            } catch (Exception e) {
                exchange.sendResponseHeaders(500, -1); // Внутренняя ошибка сервера
            } finally {
                exchange.close();
            }
        } else {
            exchange.sendResponseHeaders(405, -1); // Метод не разрешен
            exchange.close();
        }
    }

    public void sendJsonResponse(HttpExchange exchange, int statusCode, Object responseObj) throws IOException {
        String response = gson.toJson(responseObj);
        byte[] resp = response.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        exchange.sendResponseHeaders(statusCode, resp.length);
        exchange.getResponseBody().write(resp);
    }
}