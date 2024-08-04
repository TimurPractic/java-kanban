package ru.yandex.practicum.tasktracker.manager;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import com.sun.net.httpserver.HttpHandler;
import ru.yandex.practicum.tasktracker.model.HttpMethod;
import ru.yandex.practicum.tasktracker.utils.Managers;

public class BaseHttpHandler implements HttpHandler {

    TaskManager taskManager = Managers.getDefault();
    protected Gson gson = new Gson();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        // получаем запрос, но ничего не отправляем в ответ
    }

    public void sendText(HttpExchange h, String text) throws IOException {
        byte[] resp = text.getBytes(StandardCharsets.UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        h.sendResponseHeaders(200, resp.length);
        h.getResponseBody().write(resp);
        h.close();
    }

    public void sendNotFound(HttpExchange h, String text) throws IOException {
        byte[] resp = text.getBytes(StandardCharsets.UTF_8);
        h.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        h.sendResponseHeaders(404, resp.length);
        h.getResponseBody().write(resp);
        h.close();
    }

    public HttpMethod parseHttpMethod(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        try {
            return HttpMethod.valueOf(method);
        } catch (IllegalArgumentException e) {
            exchange.sendResponseHeaders(405, -1); // Метод не разрешен
            exchange.close();
            return null;
        }
    }

    public Integer parseId(String[] pathParts) {
        if (pathParts.length == 3) {
            try {
                return Integer.parseInt(pathParts[2]);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }
}