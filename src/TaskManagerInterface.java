import java.util.ArrayList;
import java.util.HashMap;

public interface TaskManagerInterface {
    HashMap<Integer, Task> getTasks();
    HashMap<Integer, Subtask> getSubtasks();
    HashMap<Integer, Epic> getEpics();
    /////////////////////////////////////////// Task methods///////////////////////////////////////////////////////////
    void addTask(Task newTask);
    void deleteTask(Task newTask);
    void updateTask(Task task, TaskStatus status, String title, String description);
    void deleteAllTasks();
    Task getTaskById(int id);
    /////////////////////////////////////////// Epic Methods///////////////////////////////////////////////////////////
    void addEpic(Epic newEpic);
    void deleteEpic(Epic newEpic);
    void updateEpic(Epic epic, String title, String description);
    void deleteAllEpics();
    Epic getEpicById(Integer id);
    //////////////////////////////////////////////////// Subtask methods//////////////////////////////////////////////////
    void addSubTask(Subtask newSubTask);
    void updateSubTask(Subtask subtask, TaskStatus status, String title, String description);
    void deleteOneSubTask(int id);
    void deleteAllSubTasks();
    Subtask getSubtaskById(int id);
    ArrayList<Task> getSubtaskFromEpicById(Integer id);
}