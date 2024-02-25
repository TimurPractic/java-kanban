import java.util.HashMap;
import java.util.Scanner;
public class TaskManager {
    Scanner scanner = new Scanner(System.in);
    HashMap<Integer, Task> tasks = new HashMap<>();
    //  HashMap<Integer, Subtask> subtasks = new HashMap<>();
    //  HashMap<Integer, Epic> epics = new HashMap<>();
    static int idSequence;
////////////////////////////////////////////////////////////////////////// нужно сделать методы

// выводить списки задач разных типов
// Получение списка всех подзадач определённого эпика. - для эпика

    /////////////////////////////////////////////////////////////////// Manager Methods
    static int generateNewID() {
        return idSequence++;
    }
/////////////////////////////////////////////////////////////// Task methods

    public void addTask(String title) {
        Task newTask = new Task(title); // Создаем новый объект Task
        tasks.put(newTask.id, newTask); // Добавляем объект Task в HashMap с использованием ID в качестве ключа
        System.out.println("Создали таску с номером " + newTask.id);
    }
    public void deleteTask(int id) {
        Task deletedTask = tasks.get(id);
        tasks.remove(id);
    }

    public Task updateTask(Task task,TaskStatus status, String title, String description) {
        if (status != null) {
            task.status = status;
        }
        if (title != null) {
            task.title = title;
        }
        if (description != null) {
            task.description = description;
        }
        return task;
    }

    public void deleteAllTasks() {
        tasks.clear();
    }
}
  /*  //////////////////////////////////////////////////////////////////// Subtask methods
    public Subtask addSubTask(Subtask newTask){
        newTask.id = generateNewID();
        subtasks.put(newTask.id, newTask);
        return newTask;
    }
    public Subtask updateSubTask(Subtask updatedTask){
        Subtask currentTask = subtasks.get(updatedTask.id);
        subtasks.put(updatedTask.id, currentTask);
        return updatedTask;
    }
    public Subtask deleteSubTask(int id){
        Subtask currentTask = subtasks.get(id);
        subtasks.remove(id);
        return currentTask;
    }
    public Subtask getSubTask(Subtask updatedTask){
        Subtask currentTask = subtasks.get(updatedTask.id);
        return currentTask;
    }
    public void deleteAllSubTasks(){
        subtasks.clear();
    }
    ////////////////////////////////////////////////////////////////////// Epic Methods
    public Epic addEpic(Epic newTask){
        newTask.id = generateNewID();
        epics.put(newTask.id, newTask);
        return newTask;
    }
    /*public Epic updateEpic(Epic updatedTask){
        Epic currentTask = epics.get(updatedTask.id);                  Пользователь не должен иметь возможности поменять статус эпика
        epics.put(updatedTask.id, currentTask);
        return updatedTask;
    }*/
    /*public Epic deleteEpic(int id){
        Epic currentTask = epics.get(id);
        epics.remove(id);
        return currentTask;
    }
    public Epic getEpic(Epic updatedTask){
        Epic currentTask = epics.get(updatedTask.id);
        return currentTask;
    }
    public void deleteAllEpics(){
        epics.clear();
    }

}*/
