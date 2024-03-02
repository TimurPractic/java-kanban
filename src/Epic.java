import java.util.ArrayList;
public class Epic extends Task{
    private ArrayList<Subtask> arraySubTask;

    public Epic(String title){
        super(title);
        this.arraySubTask = new ArrayList<>();
    }

    public ArrayList<Subtask> getArraySubTask() {
        return arraySubTask;
    }

    public void setArraySubTask(ArrayList<Subtask> arraySubTask) {
        this.arraySubTask = arraySubTask;
    }
}