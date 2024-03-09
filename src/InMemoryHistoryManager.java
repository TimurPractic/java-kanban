import java.util.List;
import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager{
    private final List<Task> historyList = new ArrayList<>();

    @Override
    public void addy(Task task) {
       // Task copyTask = task;
        if (historyList.size() < 10) {
            historyList.add(task);
        } else {
            while (historyList.size() >= 10) {
                historyList.remove(0);
            }
            historyList.add(task);
        }
    }

    @Override
    public List<Task> getHistory(){
            return historyList;
        }
    @Override
    public void clearHistory(){
        historyList.clear();
    }
}
