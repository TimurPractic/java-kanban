package ru.yandex.practicum.tasktracker.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import ru.yandex.practicum.tasktracker.manager.BaseHttpHandler;
import ru.yandex.practicum.tasktracker.manager.TaskManager;
import ru.yandex.practicum.tasktracker.model.HttpMethod;
import ru.yandex.practicum.tasktracker.model.Task;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class TaskHandler extends BaseHttpHandler {
    private final TaskManager taskManager;
    private final Gson gson;

    public TaskHandler(TaskManager taskManager, Gson gson) {
        this.taskManager = taskManager;
        this.gson = gson;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        HttpMethod httpMethod = parseHttpMethod(exchange);
        if (httpMethod == null) {
            return;
        }

        switch (httpMethod)  {
            case GET:
                handleGet(exchange);
                break;

            case DELETE:
                handleDelete(exchange);
                break;

            case POST:
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

        if (pathParts.length == 3) {
            String idStr = pathParts[2];
            try {
                int id = Integer.parseInt(idStr);
                Task task = taskManager.getTaskById(id);
                if (task == null) {
                    exchange.sendResponseHeaders(404, -1); // Задача не найдена
                } else {
                    sendJsonResponse(exchange, 200, task);
                }
            } catch (NumberFormatException e) {
                exchange.sendResponseHeaders(400, -1); // Неверный запрос
            }
        } else {
            List<Task> tasks = taskManager.getTasks();
            sendJsonResponse(exchange, 200, tasks);
        }
        exchange.getResponseBody().close();
    }

    private void handleDelete(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String[] pathParts = path.split("/");

        if (pathParts.length == 3) {
            String idStr = pathParts[2];
            try {
                int id = Integer.parseInt(idStr);
                taskManager.deleteTask(id);
                String itsDeleted = "Удалено!";
                sendJsonResponse(exchange, 204, itsDeleted);
            } catch (NumberFormatException e) {
                exchange.sendResponseHeaders(400, -1); // Неверный запрос
            }
        } else {
            exchange.sendResponseHeaders(400, -1); // Неверный запрос
        }
    }

    private void handlePost(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String[] pathParts = path.split("/");

        InputStream requestBody = exchange.getRequestBody();
        String requestBodyStr = new String(requestBody.readAllBytes(), StandardCharsets.UTF_8);
        Task task = gson.fromJson(requestBodyStr, Task.class);

        if (pathParts.length == 3) {
            String idStr = pathParts[2];
            try {
                int id = Integer.parseInt(idStr);
                Task task2 = taskManager.getTaskById(id);
                taskManager.updateTask(task2);
                sendJsonResponse(exchange, 201, task2);
            } catch (NumberFormatException e) {
                exchange.sendResponseHeaders(400, -1); // Неверный запрос
            }
        } else if (pathParts.length == 2) {
            taskManager.addTask(task);
            sendJsonResponse(exchange, 201, task);
        } else {
            exchange.sendResponseHeaders(400, -1); // Неверный запрос
        }
        exchange.getResponseBody().close();
    }

    public void sendJsonResponse(HttpExchange exchange, int statusCode, Object responseObj) throws IOException {
        String response = gson.toJson(responseObj);
        byte[] resp = response.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        exchange.sendResponseHeaders(statusCode, resp.length);
        exchange.getResponseBody().write(resp);
    }
}