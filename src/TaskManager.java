import java.util.HashMap;

public class TaskManager {
    HashMap<Task> tasks;
    HashMap<Subtask> subtasks;
    HashMap<Epic> epics;

    public createTask(String title, Date dueDate){} //- метод для создания новой задачи (Task) с указанием названия и даты дедлайна.
    public createEpic(String title, Date dueDate) //- метод для создания нового Epic с указанием названия и даты дедлайна
    public createSubtask(String title, Date dueDate) //- метод для создания новой подзадачи (Subtask) с указанием названия и даты дедлайна.
    public addSubtaskToEpic(Epic epic, Subtask subtask) //- метод для добавления подзадачи к Epic.
    public getTasks() //- метод для получения списка всех задач.
    public getEpics() //- метод для получения списка всех Epic.
    public getSubtasks() //- метод для получения списка всех подзадач.
    public completeTask(Task task) //- метод для пометки задачи как выполненной.
    public completeSubtask(Subtask subtask) //- метод для пометки подзадачи как выполненной.
    public updateTask(Task task, String newTitle, Date newDueDate) //- метод для обновления информации о задаче.
    public updateEpic(Epic epic, String newTitle, Date newDueDate) //- метод для обновления информации о Epic.
    public updateSubtask(Subtask subtask, String newTitle, Date newDueDate) //- метод для обновления информации о подзадаче.
}
