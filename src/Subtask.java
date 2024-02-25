public class Subtask extends Task{
    public int epicId;

    public Subtask(String title, int epicID){
        super(title);
        this.id = TaskManager.generateNewID();
        this.status = TaskStatus.NEW;
        this.epicId = epicID;
    }
}