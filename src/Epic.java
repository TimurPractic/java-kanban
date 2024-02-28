import java.util.ArrayList;
public class Epic extends Task{
    private ArrayList<Subtask> arraySubTask;

    private TaskManager taskManager = new TaskManager();

    public Epic(String title){
        super(title);
        setId(taskManager.generateNewID());
        setStatus(TaskStatus.NEW);
        this.arraySubTask = new ArrayList<>();
    }

    public ArrayList<Subtask> getArraySubTask() {
        return arraySubTask;
    }

    public void setArraySubTask(ArrayList<Subtask> arraySubTask) {
        this.arraySubTask = arraySubTask;
    }
}