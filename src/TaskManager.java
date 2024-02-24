import java.util.HashMap;
import java.util.Scanner;
public class TaskManager {
    Scanner scanner = new Scanner(System.in);
    HashMap<Integer, Task> tasks;
    HashMap<Integer, Subtask> subtasks;
    HashMap<Integer, Epic> epics;
    static int idSequence;
////////////////////////////////////////////////////////////////////////// нужно сделать методы
// получать задачи по идентификатору;
// выводить списки задач разных типов
// Получение списка всех подзадач определённого эпика. - для эпика

/////////////////////////////////////////////////////////////////// Manager Methods
    static int generateNewID(){
        return idSequence++;
    }
/////////////////////////////////////////////////////////////// Task methods
    public Task addTask(Task newTask){
        newTask.id = generateNewID();
        tasks.put(newTask.id, newTask);
        return newTask;
    }
    public Task updateTask(Task updatedTask){
        Task currentTask = tasks.get(updatedTask.id);
        tasks.put(updatedTask.id, currentTask);
        return updatedTask;
    }
    public Task deleteTask(int id){
        Task currentTask = tasks.get(id);
        tasks.remove(id);
        return currentTask;
    }
    public Task getTask(Task updatedTask){
        Task currentTask = tasks.get(updatedTask.id);
        return currentTask;
    }
    public void deleteAllTasks(){
        tasks.clear();
    }
    //////////////////////////////////////////////////////////////////// Subtask methods
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
    public Epic deleteEpic(int id){
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

}
