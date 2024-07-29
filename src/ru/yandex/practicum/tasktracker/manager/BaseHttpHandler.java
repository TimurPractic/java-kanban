package ru.yandex.practicum.tasktracker.manager;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import com.sun.net.httpserver.HttpHandler;

public class BaseHttpHandler implements HttpHandler {

    FileBackedTaskManager taskManager = new FileBackedTaskManager();

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

    public void sendHasInteractions(HttpExchange h, String text) throws IOException {
        //для отправки ответа, если при создании или обновлении задача пересекается с уже существующими
    }





}