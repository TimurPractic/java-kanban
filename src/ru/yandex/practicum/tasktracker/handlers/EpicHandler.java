package ru.yandex.practicum.tasktracker.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import ru.yandex.practicum.tasktracker.manager.BaseHttpHandler;
import ru.yandex.practicum.tasktracker.manager.TaskManager;
import ru.yandex.practicum.tasktracker.model.Epic;
import ru.yandex.practicum.tasktracker.model.Task;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class EpicHandler extends BaseHttpHandler {
    private final TaskManager taskManager;
    private final Gson gson;

    public EpicHandler(TaskManager taskManager, Gson gson) {
        this.taskManager = taskManager;
        this.gson = gson;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();

        switch (method) {
            case "GET":
                handleGet(exchange);
                break;

            case "DELETE":
                handleDelete(exchange);
                break;

            case "POST":
                handlePost(exchange);
                break;
            default:
                exchange.sendResponseHeaders(405, -1); // Метод не разрешен
                break;
        }
    }

    private void handleGet(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String[] pathParts = path.split("/");
        String response;
        byte[] resp;

        if (pathParts.length == 4 && "subtasks".equals(pathParts[3])) {
            String idStr = pathParts[2];
            try {
                int id = Integer.parseInt(idStr);
                List<Task> subtasks = taskManager.getSubtasksFromEpicById(id);
                response = gson.toJson(subtasks);
                resp = response.getBytes(StandardCharsets.UTF_8);
                exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
                exchange.sendResponseHeaders(200, resp.length);
                exchange.getResponseBody().write(resp);
            } catch (NumberFormatException e) {
                exchange.sendResponseHeaders(400, -1); // Неверный запрос
            }
        } else {
            exchange.sendResponseHeaders(404, -1); // Путь не найден
        }
        exchange.getResponseBody().close();
    }

    private void handleDelete(HttpExchange exchange) throws IOException {
        if ("DELETE".equals(exchange.getRequestMethod())) {
            String path = exchange.getRequestURI().getPath();
            String[] pathParts = path.split("/");
            if (pathParts.length == 3) {
                String idStr = pathParts[2];
                try {
                    int id = Integer.parseInt(idStr);
                    Epic epic = taskManager.getEpicById(id);
                    taskManager.deleteEpic(epic);
                    exchange.sendResponseHeaders(204, -1); // Успешно, нет содержимого
                } catch (NumberFormatException e) {
                    exchange.sendResponseHeaders(400, -1); // Неверный запрос
                }
            } else {
                exchange.sendResponseHeaders(400, -1); // Неверный запрос
            }
        } else {
            exchange.sendResponseHeaders(405, -1); // Метод не разрешен
        }

        exchange.getResponseBody().close();
    }

    private void handlePost(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            InputStream requestBody = exchange.getRequestBody();
            String requestBodyStr = new String(requestBody.readAllBytes(), StandardCharsets.UTF_8);
            Epic epic = gson.fromJson(requestBodyStr, Epic.class);

            taskManager.addEpic(epic);
            String response = gson.toJson(epic);
            byte[] resp = response.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
            exchange.sendResponseHeaders(201, resp.length); // Успешное создание, код 201
            exchange.getResponseBody().write(resp);
            exchange.getResponseBody().close();
        } else {
            exchange.sendResponseHeaders(405, -1); // Метод не разрешен
        }
    }

}
