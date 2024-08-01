package ru.yandex.practicum.tasktracker.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import ru.yandex.practicum.tasktracker.manager.BaseHttpHandler;
import ru.yandex.practicum.tasktracker.manager.TaskManager;
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

        if (pathParts.length == 3) {
            String idStr = pathParts[2];
            try {
                int id = Integer.parseInt(idStr);
                Task task = taskManager.getTaskById(id);
                if (task == null) {
                    exchange.sendResponseHeaders(404, -1); // Задача не найдена
                } else {
                    response = gson.toJson(task);
                    resp = response.getBytes(StandardCharsets.UTF_8);
                    exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
                    exchange.sendResponseHeaders(200, resp.length);
                    exchange.getResponseBody().write(resp);
                }
            } catch (NumberFormatException e) {
                exchange.sendResponseHeaders(400, -1); // Неверный запрос
            }
        } else {
            List<Task> tasks = taskManager.getTasks(); // Предположим, что у вас есть список задач
            response = gson.toJson(tasks); // Преобразование списка в JSON строку
            resp = response.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
            exchange.sendResponseHeaders(200, resp.length);
            exchange.getResponseBody().write(resp);
        }
        exchange.getResponseBody().close();
    }

    private void handleDelete(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String[] pathParts = path.split("/");
        String response;
        byte[] resp;

        if (pathParts.length == 3) {
            // Извлечение идентификатора задачи из URI
            String idStr = pathParts[2];
            try {
                int id = Integer.parseInt(idStr);
                taskManager.deleteTask(id);
                String itsDeleted = "Удалено!";
                response = gson.toJson(itsDeleted);
                resp = response.getBytes(StandardCharsets.UTF_8);
                exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
                exchange.sendResponseHeaders(204, resp.length);
                exchange.getResponseBody().write(resp);
                exchange.getResponseBody().close();
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
        String response;
        byte[] resp;

        InputStream requestBody = exchange.getRequestBody();
        String requestBodyStr = new String(requestBody.readAllBytes(), StandardCharsets.UTF_8);
        Task task = gson.fromJson(requestBodyStr, Task.class);

        if (pathParts.length == 3) {
            String idStr = pathParts[2];
            try {
                int id = Integer.parseInt(idStr);
                Task task2 = taskManager.getTaskById(id);
                taskManager.updateTask(task2);
                response = gson.toJson(task2);
                resp = response.getBytes(StandardCharsets.UTF_8);
                exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
                exchange.sendResponseHeaders(200, resp.length); // Успешное обновление, код 200
                exchange.getResponseBody().write(resp);
            } catch (NumberFormatException e) {
                exchange.sendResponseHeaders(400, -1); // Неверный запрос
            }
        } else if (pathParts.length == 2) {
            taskManager.addTask(task);
            response = gson.toJson(task);
            resp = response.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
            exchange.sendResponseHeaders(201, resp.length); // Успешное создание, код 201
            exchange.getResponseBody().write(resp);
        } else {
            exchange.sendResponseHeaders(400, -1); // Неверный запрос
        }
        exchange.getResponseBody().close();
    }
}