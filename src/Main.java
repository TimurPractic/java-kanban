public class Main {

    public static void main(String[] args)
    {
// Создайте две задачи
        TaskManager taskManager = new TaskManager();
        System.out.println("Cоздаём одну задачу");
        taskManager.addTask("Пройти теорию 4 спринта");
        System.out.println("Cоздаём вторую задачу");
        taskManager.addTask("Сдать практику 4 спринта");
// Распечатайте списки задач
        System.out.println(taskManager.tasks);
// Измените статусы созданных объектов, распечатайте их.
        System.out.println("Поменяем статус таске 1");
        Task currentTask = taskManager.tasks.get(1);
        taskManager.updateTask(currentTask, TaskStatus.IN_PROGRESS, null, "Тут описание");
        System.out.println(currentTask);
// удалить одну из задач
        System.out.println("Удалим таску 0");
        currentTask = taskManager.tasks.get(0);
        taskManager.deleteTask(currentTask.id);
        System.out.println(taskManager.tasks);


       // создать эпик с двумя подзадачами
       // создатьэпик с одной подзадачей.
       // Распечатайте списки эпиков, задач и подзадач через System.out.println(..).
       // Проверьте, что статус задачи и подзадачи сохранился, а статус эпика рассчитался по статусам подзадач.
       // удалить один из эпиков
    }
}
