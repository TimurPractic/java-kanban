import java.util.List;

public interface HistoryManager {
    void addy(Task task);
    List<Task> getHistory();
    void clearHistory();
}
