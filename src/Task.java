public class Task {
    public TaskStatus status;
    public int id;
    public String title;
    public String description;

    public Task(String title){
        this.id = TaskManager.generateNewID();
        this.status = TaskStatus.NEW;
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
}
