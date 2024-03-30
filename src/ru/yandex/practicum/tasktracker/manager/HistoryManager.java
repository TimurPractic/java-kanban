package ru.yandex.practicum.tasktracker.manager;

import java.util.List;
import ru.yandex.practicum.tasktracker.model.Task;

public interface HistoryManager {
    void add(Task task);

    List<Task> getHistory();

    void remove(int id);
}
