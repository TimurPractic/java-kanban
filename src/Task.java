public class Task {
    private TaskStatus status;
    private int id;
    private String title;
    private String description;

    private TaskManager taskManager = new TaskManager();

    public Task(String title) {
        setId(taskManager.generateNewID());
        setStatus(TaskStatus.NEW);
        this.title = title ;
    }

    @Override
    public String toString() {
        return "Task{" +
                "status=" + status +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
