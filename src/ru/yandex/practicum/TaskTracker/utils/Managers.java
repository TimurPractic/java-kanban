package ru.yandex.practicum.TaskTracker.utils;

import ru.yandex.practicum.TaskTracker.TasksManager.InMemoryTaskManager;
import ru.yandex.practicum.TaskTracker.HistoryManager.HistoryManager;
import ru.yandex.practicum.TaskTracker.HistoryManager.InMemoryHistoryManager;

public class Managers {
    public static InMemoryTaskManager getDefault() {
        return new InMemoryTaskManager();
    }
    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
