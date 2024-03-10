import java.util.List;
import java.util.Map;

public interface TaskManager {
    List<Task> getTasks();
    List<Subtask> getSubtasks();
    List<Epic> getEpics();
    void addTask(Task newTask);
    void deleteTask(Task newTask);
    void updateTask(Task task, TaskStatus status, String title, String description);
    void deleteAllTasks();
    Task getTaskById(int id);
    void addEpic(Epic newEpic);
    void deleteEpic(Epic newEpic);
    void updateEpic(Epic epic, String title, String description);
    void deleteAllEpics();
    Epic getEpicById(Integer id);
    void addSubTask(Subtask newSubTask);
    void updateSubTask(Subtask subtask, TaskStatus status, String title, String description);
    void deleteOneSubTask(int id);
    void deleteAllSubTasks();
    Subtask getSubtaskById(int id);
    List<Task> getSubtaskFromEpicById(Integer id);
}