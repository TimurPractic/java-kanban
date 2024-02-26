import java.util.HashMap;
//import java.util.Scanner;
public class TaskManager {
    //Scanner scanner = new Scanner(System.in);// в текущей реализации не используется,
                                                // но нужен для корректной работы проета в целом, когда у него будет фронтенд
    HashMap<Integer, Task> tasks = new HashMap<>();
    HashMap<Integer, Subtask> subtasks = new HashMap<>();
    HashMap<Integer, Epic> epics = new HashMap<>();
    static int idSequence;

/////////////////////////////////////////// Manager Methods/////////////////////////////////////////////////////////
    static int generateNewID() {
        return idSequence++;
    }
/////////////////////////////////////////// Task methods///////////////////////////////////////////////////////////
    public void addTask(String title) {
        Task newTask = new Task(title);
        tasks.put(newTask.id, newTask);
        System.out.println("Создали таску с номером " + newTask.id + " и названием '" + newTask.title +"'");
    }
    public void deleteTask(int id) {
        Task deletedTask = tasks.get(id);
        tasks.remove(id);
        System.out.println("Удалили таску с номером " + deletedTask.id);
    }
    public void updateTask(Task task,TaskStatus status, String title, String description) {
        if (status != null) {
            task.status = status;
        }
        if (title != null) {
            task.title = title;
        }
        if (description != null) {
            task.description = description;
        }
    }
    public void deleteAllTasks() {
        tasks.clear();
    }
/////////////////////////////////////////// Epic Methods///////////////////////////////////////////////////////////
    public void addEpic(String title) {
        Epic newEpic = new Epic(title);
        epics.put(newEpic.id, newEpic);
        System.out.println("Создали эпик с номером " + newEpic.id + " и названием '" + newEpic.title +"'");
    }
    public void deleteEpic(int id) {
        Epic deletedEpic = epics.get(id);
        epics.remove(id);
        System.out.println("Удалили таску с номером " + deletedEpic.id);
    }
    public void updateEpic(Epic epic, String title, String description) {
        if (title != null) {
            epic.title = title;
        }
        if (description != null) {
            epic.description = description;
        }
    }
    public void deleteAllEpics(){
        epics.clear();
    }
    public void showAllSubtask(Epic epic){
        Epic chosenEpic = epics.get(epic.id);
        System.out.println(chosenEpic.arrayST);
    }
    public void checkEpicStatusForDone(Epic epic){
        Epic chosenEpic = epics.get(epic.id);
        boolean isReady = true;
        for (Subtask sub : chosenEpic.arrayST){
            if(!sub.status.equals(TaskStatus.DONE)){
                isReady = false;
                break;
            } else {
                isReady = true;
            }
        }
        if(isReady){
            chosenEpic.status = TaskStatus.DONE;
        }
    }

    public void checkEpicStatusForProgress(Epic epic){
        Epic chosenEpic = epics.get(epic.id);
        boolean isInProgress = false;
        for (Subtask sub : chosenEpic.arrayST){
            if(sub.status.equals(TaskStatus.IN_PROGRESS)){
                isInProgress = true;
                break;
            }
        }
        if(isInProgress){
            chosenEpic.status = TaskStatus.IN_PROGRESS;
        }
    }

 //////////////////////////////////////////////////// Subtask methods//////////////////////////////////////////////////
    public void addSubTask(String title,int epicID) {
        Subtask newSubtask = new Subtask(title, epicID);
        subtasks.put(newSubtask.id, newSubtask);
        Epic chosenEpic = epics.get(epicID);
        chosenEpic.arrayST.add(newSubtask);
        System.out.println("Создали подзадачу с номером " + newSubtask.id + " и названием '" + newSubtask.title +
                "'. Она принадлежит эпику номер " + epicID);
    }
    public void updateSubTask(Subtask subtask,TaskStatus status, String title, String description) {
        if (status != null) {
            subtask.status = status;
        }
        if (title != null) {
            subtask.title = title;
        }
        if (description != null) {
            subtask.description = description;
        }
    }
    public void deleteSubTask(int id) {
        Task deletedSubTask = subtasks.get(id);
        subtasks.remove(id);
        System.out.println("Удалили таску с номером " + deletedSubTask.id);
    }

    public void deleteAllSubTasks(){
        subtasks.clear();
    }
}
