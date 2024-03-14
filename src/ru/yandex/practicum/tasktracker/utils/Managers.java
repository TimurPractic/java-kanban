package ru.yandex.practicum.tasktracker.utils;

import ru.yandex.practicum.tasktracker.manager.InMemoryTaskManager;
import ru.yandex.practicum.tasktracker.manager.HistoryManager;
import ru.yandex.practicum.tasktracker.manager.InMemoryHistoryManager;

public class Managers {
    public static InMemoryTaskManager getDefault() {
        return new InMemoryTaskManager();
    }
    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
