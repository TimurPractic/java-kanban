public class Main {

    public static void main(String[] args){
        TaskManager taskManager = new TaskManager();

// Создайте две задачи
        System.out.println("Cоздаём одну задачу");
        taskManager.addTask("Пройти теорию 4 спринта");
        System.out.println("Cоздаём вторую задачу");
        taskManager.addTask("Сдать практику 4 спринта");
// создать эпик с двумя подзадачами
        System.out.println("Cоздаём эпик с двумя подзадачами");
        taskManager.addEpic("Это эпик с 2 подзадачами");
        taskManager.addSubTask("Это первая подзадача", 3);
        taskManager.addSubTask("Это вторая подзадача", 3);
// создать эпик с одной подзадачей.
        System.out.println("Cоздаём эпик с одной подзадачей");
        taskManager.addEpic("Это эпик с 1 подзадачей");
        taskManager.addSubTask("Это тоже первая подзадача", 9);
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
        System.out.println("Поменяем статус подзадаче 5"); //эпик 3
        Subtask currentSubTask1 = taskManager.subtasks.get(5);
        Epic currentEpic1 = taskManager.epics.get(3);
        taskManager.updateSubTask(currentSubTask1, TaskStatus.IN_PROGRESS, null, "Тут описание субтаски 5");
        taskManager.checkEpicStatusForProgress(currentEpic1);
        System.out.println("Поменяем статус подзадаче 11"); // эпик 9
        Subtask currentSubTask2 = taskManager.subtasks.get(11);
        Epic currentEpic2 = taskManager.epics.get(9);
        taskManager.updateSubTask(currentSubTask2, TaskStatus.IN_PROGRESS, null, "Тут описание субтаски 11");
        taskManager.checkEpicStatusForProgress(currentEpic2);
        System.out.println(currentEpic1);
        System.out.println(currentSubTask1);
        System.out.println(currentEpic2);
        System.out.println(currentSubTask2);
//завершим эпик перведя позадачи в Done - эпик 3, задачи 5 и 7
        System.out.println("Поменяем статус подзадаче 5"); //эпик 3
        Subtask currentSubTask3 = taskManager.subtasks.get(5);
        Epic currentEpic3 = taskManager.epics.get(3);
        taskManager.updateSubTask(currentSubTask3, TaskStatus.DONE, null, "Тут описание субтаски 5");
        taskManager.checkEpicStatusForDone(currentEpic3);
        Epic checkerEpic = taskManager.epics.get(3);
        System.out.println(checkerEpic);
        System.out.println("Поменяем статус подзадаче 7"); //эпик 3
        Subtask currentSubTask4 = taskManager.subtasks.get(7);
        Epic currentEpic4 = taskManager.epics.get(3);
        taskManager.updateSubTask(currentSubTask4, TaskStatus.DONE, null, "Тут описание субтаски 5");
        taskManager.checkEpicStatusForDone(currentEpic4);
        checkerEpic = taskManager.epics.get(3);
        System.out.println(checkerEpic);
        // здесь я сознательно положил один и тот же эпик в три разных переменных - для точного отслеживания изенения статуса эпика
// удалить одну из задач
        System.out.println("Удалим таску 0");
        currentTask = taskManager.tasks.get(0);
        taskManager.deleteTask(currentTask.id);
        System.out.println(taskManager.tasks);
// удалить один из эпиков
        System.out.println("Удалим эпик номер 9");
        Epic currentEpic = taskManager.epics.get(9);
        taskManager.deleteEpic(currentEpic.id);
    }
}
