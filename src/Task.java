public class Task {
    public TaskStatus status;
    public int id;
    public String title;

    public Task(int id, String title, TaskStatus status){
        this.id = id;
        this.status = status;
        this.title = title;
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
}
