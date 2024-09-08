package ru.yandex.practicum.tasktracker.manager;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import com.sun.net.httpserver.HttpHandler;
import ru.yandex.practicum.tasktracker.model.HttpMethod;
import ru.yandex.practicum.tasktracker.utils.Managers;

public abstract class BaseHttpHandler implements HttpHandler {

    TaskManager taskManager = Managers.getDefault();
    protected Gson gson = new Gson();

    @Override
    public abstract void handle(HttpExchange exchange) throws IOException;

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

    protected void sendErrorResponse(HttpExchange exchange, int statusCode) throws IOException {
        exchange.sendResponseHeaders(statusCode, -1);
    }

    protected Integer getIdFromPath(HttpExchange exchange) {
        String path = exchange.getRequestURI().getPath();
        String[] pathParts = path.split("/");
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