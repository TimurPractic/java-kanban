public class Main {

    public static void main(String[] args){
        TaskManager taskManager = new TaskManager();

// Создайте две задачи
        System.out.println("Cоздаём одну задачу");
        taskManager.addTask("Пройти теорию 4 спринта");
        System.out.println("Cоздаём вторую задачу");
        taskManager.addTask("Сдать практику 4 спринта");

        // создать эпик с двумя подзадачами



        // создать эпик с одной подзадачей.



// Распечатайте списки задач
        System.out.println("Распечатаем списки задач");
        System.out.println(taskManager.tasks);
// Распечатайте списки эпиков
        System.out.println("Распечатаем списки эпиков");
        System.out.println(taskManager.epics);
// Распечатайте списки задач
        System.out.println("Распечатаем списки субтасков");
        System.out.println(taskManager.subtasks);
// Измените статусы созданных тасков, распечатайте их.
        System.out.println("Поменяем статус таске 1");
        Task currentTask = taskManager.tasks.get(1);
        taskManager.updateTask(currentTask, TaskStatus.IN_PROGRESS, null, "Описание");
        System.out.println(currentTask);
        // Измените статусы созданных субтасков, распечатайте их.

        // Проверьте, что статус задачи и подзадачи сохранился, а статус эпика рассчитался по статусам подзадач.

// удалить одну из задач
        System.out.println("Удалим таску 0");
        currentTask = taskManager.tasks.get(0);
        taskManager.deleteTask(currentTask.id);
        System.out.println(taskManager.tasks);

   // удалить один из эпиков
        System.out.println("Удалим эпик номер");
        Epic currentEpic = taskManager.epics.get(0);
        taskManager.deleteEpic(currentEpic.id);
    }
}
