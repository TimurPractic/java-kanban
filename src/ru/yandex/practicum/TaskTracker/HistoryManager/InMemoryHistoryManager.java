package ru.yandex.practicum.TaskTracker.HistoryManager;

import java.util.List;
import java.util.ArrayList;
import ru.yandex.practicum.TaskTracker.TasksManager.Task;

public class InMemoryHistoryManager implements HistoryManager {
    private final List<Task> history = new ArrayList<>();

    @Override
    public void add(Task task) {
        if (history.size() >= 10) {
            history.remove(0);
        }
        history.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return history;
    }
}
