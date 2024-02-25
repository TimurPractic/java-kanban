import java.util.ArrayList;
public class Epic extends Task{
    public ArrayList<Subtask> arrayST;

    public Epic(String title){
        super(title);
        this.id = TaskManager.generateNewID();
        this.status = TaskStatus.NEW;
        this.arrayST = new ArrayList<>();
    }
}