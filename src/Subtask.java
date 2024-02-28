public class Subtask extends Task{
    private int epicId;

    private TaskManager taskManager = new TaskManager();

    public Subtask(String title){
        super(title);
        setId(taskManager.generateNewID());
        setStatus(TaskStatus.NEW);
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }
}