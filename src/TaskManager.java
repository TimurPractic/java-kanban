import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private int idSequence = 0;

    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    public HashMap<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }



/////////////////////////////////////////// Manager Methods/////////////////////////////////////////////////////////
    private int generateNewID() {
        idSequence++;
        return idSequence;
    }
/////////////////////////////////////////// Task methods///////////////////////////////////////////////////////////
    public void addTask(Task newTask) {
        int newID = generateNewID();
        newTask.setId(newID);
        newTask.setStatus(TaskStatus.NEW);
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

    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        System.out.println(task);
        return task;
    }
/////////////////////////////////////////// Epic Methods///////////////////////////////////////////////////////////
    public void addEpic(Epic newEpic) {
        int newID = generateNewID();
        newEpic.setId(newID);
        newEpic.setStatus(TaskStatus.NEW);
        epics.put(newEpic.getId(), newEpic);
        System.out.println("Создали эпик с номером " + newEpic.getId() + " и названием '" + newEpic.getTitle() +"'");
    }
    public void deleteEpic(Epic newEpic) {
        ArrayList<Subtask> currentSubtasks = newEpic.getArraySubTask();
        int count = currentSubtasks.size();
        for (Subtask subtask : currentSubtasks) {
            subtasks.remove(subtask.getId());
        }
        epics.remove(newEpic.getId());
        System.out.println("Удалили эпик с номером " + newEpic.getId() + " и все его субтаски в количестве " + count + " штук.");
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
        subtasks.clear();// так как у нас не может существовать субтаски без эпиков,
        // то удаление всех эпиков означает удаление всех субтасков
        epics.clear();
    }
    private void showAllSubtaskOfOneEpic(Epic epic){
        System.out.println(epic.getArraySubTask());
    }
    private void checkEpicStatusForDone(Epic epic){
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

    private void checkEpicStatusForProgress(Epic epic){
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

    public Epic getEpicById(Integer id) {
        if (epics.containsKey(id)) {
            return epics.get(id);
        } else {
            System.out.println("Такого номера эпика не существует.");
        }
        return null;
    }

 //////////////////////////////////////////////////// Subtask methods//////////////////////////////////////////////////
    public void addSubTask(Subtask newSubTask) {
        int newID = generateNewID();
        newSubTask.setId(newID);
        newSubTask.setStatus(TaskStatus.NEW);
        subtasks.put(newSubTask.getId(), newSubTask);
        Epic assignedEpic = getEpicById(newSubTask.getEpicId());
        ArrayList chosenArray = assignedEpic.getArraySubTask();
        chosenArray.add(newSubTask);
        assignedEpic.setArraySubTask(chosenArray);

        System.out.println("Создали подзадачу с номером " + newSubTask.getId() + " и названием '" + newSubTask.getTitle() +
                "'. Она принадлежит эпику номер " + assignedEpic.getId());
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
        Epic currEpic = getEpicById(subtask.getEpicId());
        checkEpicStatusForProgress(currEpic);
        checkEpicStatusForDone(currEpic);
    }

    public void deleteOneSubTask(int id) {
        Subtask newSubTask = getSubtaskById(id);
        //удалиться из эпика
        Epic currEpic = getEpicById(newSubTask.getEpicId());
        subtasks.remove(newSubTask);
        //пересчитать статус эпика
        checkEpicStatusForProgress(currEpic);
        checkEpicStatusForDone(currEpic);

        System.out.println("Удалили субтаску с номером " + id);
    }

    public void deleteAllSubTasks(){
        //удалиться из эпика
        ArrayList<Epic> epicsToDelete = new ArrayList<>();
        for (Subtask subtask : subtasks.values()){
            epicsToDelete.add(getEpicById(subtask.getEpicId()));
        }
        subtasks.clear();
        //пересчитать статус эпика
        for (Epic epic : epicsToDelete){
            checkEpicStatusForProgress(epic);
            checkEpicStatusForDone(epic);
        }
    }

    public Subtask getSubtaskById(int id) {
        return subtasks.get(id);
    }
}
