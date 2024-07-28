package ru.yandex.practicum.tasktracker.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import ru.yandex.practicum.tasktracker.manager.BaseHttpHandler;
import ru.yandex.practicum.tasktracker.manager.TaskManager;
import ru.yandex.practicum.tasktracker.model.Task;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static java.lang.Integer.parseInt;

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
        List tasks;
        String response;
        byte[] resp;

        switch (method) {
            case "GET":
                handleGet(exchange);
                break;

            case "DELETE":
                handleDelete(exchange);
                break;

//            case "POST":
//                handlePost(exchange);
//                break;
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
            // Извлечение идентификатора задачи из URI
            String idStr = pathParts[2];
            try {
                int id = Integer.parseInt(idStr);
                Task task = taskManager.getTaskById(id); // Предположим, что у вас есть метод для получения задачи по ID
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
                taskManager.deleteTask(id); // Предположим, что у вас есть список задач
                String itsDeleted = "Удалено!";
                response = gson.toJson(itsDeleted); // Преобразование списка в JSON строку
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




//                try {
//                    int id = parseInt(idStr);
//                    exchange.sendResponseHeaders(204, -1); // Успешно, нет содержимого
//                } catch (NumberFormatException e) {
//                    exchange.sendResponseHeaders(400, -1); // Неверный запрос
//                }
//

//                exchange.getResponseBody().close();
//                break;






    }

//    @Override
//    public void handle(HttpExchange exchange) throws IOException {
//        String method = exchange.getRequestMethod();
//        List tasks;
//        String response;
//        byte[] resp;
//
//        switch (method) {
//            case "GET":
//                // Извлечение идентификатора задачи из URI
//                String path = exchange.getRequestURI().getPath();
//                String[] pathParts = path.split("/");
//                if (pathParts.length != 3) {
//                    tasks = taskManager.getTasks(); // Предположим, что у вас есть список задач
//                    response = gson.toJson(tasks); // Преобразование списка в JSON строку
//                    resp = response.getBytes(StandardCharsets.UTF_8);
//                    exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
//                    exchange.sendResponseHeaders(200, resp.length);
//                    exchange.getResponseBody().write(resp);
//                    exchange.getResponseBody().close();
//                    break;
//                }
//                try {
//                    String idStr = pathParts[2];
//                    int id = parseInt(idStr);
//                    Task task = taskManager.getTaskById(id); // Предположим, что у вас есть список задач
//                    response = gson.toJson(task); // Преобразование списка в JSON строку
//                    resp = response.getBytes(StandardCharsets.UTF_8);
//                    exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
//                    exchange.sendResponseHeaders(200, resp.length);
//                    exchange.getResponseBody().write(resp);
//                    exchange.getResponseBody().close();
//                } catch (NumberFormatException e) {
//                    exchange.sendResponseHeaders(400, -1); // Неверный запрос
//                }
//                break;
////            case "POST":
////                tasks = taskManager.addTask(); // Предположим, что у вас есть список задач
////                response = gson.toJson(tasks); // Преобразование списка в JSON строку
////                byte[] resp = response.getBytes(StandardCharsets.UTF_8);
////                exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
////                exchange.sendResponseHeaders(201, resp.length);
////                exchange.getResponseBody().write(resp);
////                exchange.getResponseBody().close();
//            case "DELETE":
//                // Извлечение идентификатора задачи из URI
//                String path = exchange.getRequestURI().getPath();
//                String[] pathParts = path.split("/");
//                if (pathParts.length != 3) {
//                    exchange.sendResponseHeaders(400, -1); // Неверный запрос
//                    break;
//                }
//                String idStr = pathParts[2];
//                try {
//                    int id = parseInt(idStr);
//                    exchange.sendResponseHeaders(204, -1); // Успешно, нет содержимого
//                } catch (NumberFormatException e) {
//                    exchange.sendResponseHeaders(400, -1); // Неверный запрос
//                }
//
//                tasks = taskManager.deleteTask(id); // Предположим, что у вас есть список задач
//                response = gson.toJson(tasks); // Преобразование списка в JSON строку
//                resp = response.getBytes(StandardCharsets.UTF_8);
//                exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
//                exchange.sendResponseHeaders(200, resp.length);
//                exchange.getResponseBody().write(resp);
//                exchange.getResponseBody().close();
//                break;
//
//            default:
//                exchange.sendResponseHeaders(404, -1); // Метод не разрешен
//                break;
//        }
//    }
}