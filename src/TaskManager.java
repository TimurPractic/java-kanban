import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {

    HashMap<Integer, Task> tasks = new HashMap<>();
    HashMap<Integer, Subtask> subtasks = new HashMap<>();
    HashMap<Integer, Epic> epics = new HashMap<>();
    private static int idSequence = 0; //этот id должен быть статичным, так как он принадлежит классу, а не экземпляру.
    //в нём мы храним инкремент для проставления номера ID для всех тасков субтасков и эпиков.
    // Их экземплярам он не принадлежит, но он в них и не находится, он в этом классе именно для этого.

/////////////////////////////////////////// Manager Methods/////////////////////////////////////////////////////////
    public int generateNewID() {
        idSequence++;
        return idSequence;
    }
/////////////////////////////////////////// Task methods///////////////////////////////////////////////////////////
    public void addTask(Task newTask) {
        tasks.put(newTask.getId(), newTask);
        System.out.println("Создали таску с номером " + newTask.getId() + " и названием '" + newTask.getTitle() +"'");
    }
    public void deleteTask(Task newTask) {
        tasks.remove(newTask.getId());
        System.out.println("Удалили таску с номером " + newTask.getId());
    }
    public void updateTask(Task task,TaskStatus status, String title, String description) {
        if (status != null) {
            task.setStatus(status);
        }
        if (title != null) {
            task.setTitle(title);
        }
        if (description != null) {
            task.setDescription(description);
        }
    }
    public void deleteAllTasks() {
        tasks.clear();
    }
/////////////////////////////////////////// Epic Methods///////////////////////////////////////////////////////////
    public void addEpic(Epic newEpic) {
        epics.put(newEpic.getId(), newEpic);
        System.out.println("Создали эпик с номером " + newEpic.getId() + " и названием '" + newEpic.getTitle() +"'");
    }
    public void deleteEpic(Epic newEpic) {
        epics.remove(newEpic.getId());
        System.out.println("Удалили эпик с номером " + newEpic.getId());
    }
    public void updateEpic(Epic epic, String title, String description) {
        if (title != null) {
            epic.setTitle(title);
        }
        if (description != null) {
            epic.setDescription(description);
        }
    }
    public void deleteAllEpics(){
        epics.clear();
    }
    public void showAllSubtask(Epic epic){
        System.out.println(epic.getArraySubTask());
    }
    public void checkEpicStatusForDone(Epic epic){
        boolean isReady = true;
        for (Subtask sub : epic.getArraySubTask()){
            if(!sub.getStatus().equals(TaskStatus.DONE)){
                isReady = false;
                break;
            } else {
                isReady = true;
            }
        }
        if(isReady){
            epic.setStatus(TaskStatus.DONE);
        }
    }

    public void checkEpicStatusForProgress(Epic epic){
        boolean isInProgress = false;
        for (Subtask sub : epic.getArraySubTask()){
            if(sub.getStatus().equals(TaskStatus.IN_PROGRESS)){
                isInProgress = true;
                break;
            }
        }
        if(isInProgress){
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }

 //////////////////////////////////////////////////// Subtask methods//////////////////////////////////////////////////
    public void addSubTask(Subtask newSubTask, Epic epic) {
        subtasks.put(newSubTask.getId(), newSubTask);
        ArrayList chosenArray = epic.getArraySubTask();
        chosenArray.add(newSubTask);
        epic.setArraySubTask(chosenArray);
        System.out.println("Создали подзадачу с номером " + newSubTask.getId() + " и названием '" + newSubTask.getTitle() +
                "'. Она принадлежит эпику номер " + epic.getId());
    }

    public void updateSubTask(Subtask subtask,TaskStatus status, String title, String description) {
        if (status != null) {
            subtask.setStatus(status);
        }
        if (title != null) {
            subtask.setTitle(title);
        }
        if (description != null) {
            subtask.setDescription(description);
        }
    }

    public void deleteSubTask(Subtask newSubTask) {
        subtasks.remove(newSubTask);
        System.out.println("Удалили субтаску с номером " + newSubTask.getId());
    }

    public void deleteAllSubTasks(){
        subtasks.clear();
    }
}
