package ru.yandex.practicum.TaskTracker.HistoryManager;

import java.util.List;
import ru.yandex.practicum.TaskTracker.TasksManager.Task;

public interface HistoryManager {
    void add(Task task);
    List<Task> getHistory();
}
