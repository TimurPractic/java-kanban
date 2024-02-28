public class Main {

    public static void main(String[] args){
        TaskManager taskManager = new TaskManager();

// Создайте две задачи
        System.out.println("Cоздаём одну задачу");
        Task task = new Task("Пройти теорию 4 спринта"); //как правильно
        taskManager.addTask(task);
        Task task2 = new Task("Сдать практику 4 спринта");
        System.out.println("Cоздаём вторую задачу");
        taskManager.addTask(task2);
// создать эпик с двумя подзадачами
        System.out.println("Cоздаём эпик с двумя подзадачами");
        Epic epic = new Epic("Это эпик с 2 подзадачами");
        taskManager.addEpic(epic);
        Subtask subtask1 = new Subtask("Это первая подзадача");
        taskManager.addSubTask(subtask1, epic);
        Subtask subtask2 = new Subtask("Это вторая подзадача");
        taskManager.addSubTask(subtask2, epic);
// создать эпик с одной подзадачей.
        System.out.println("Cоздаём эпик с одной подзадачей");
        Epic epic1 = new Epic("Это эпик с одной подзадачей");
        taskManager.addEpic(epic1);
        Subtask subtask3 = new Subtask("Это единственная подзадача");
        taskManager.addSubTask(subtask3, epic1);
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
        System.out.println("Поменяем статус подзадаче 6"); //эпик 4
        Subtask currentSubTask1 = taskManager.subtasks.get(6);
        Epic currentEpic1 = taskManager.epics.get(4);
        taskManager.updateSubTask(currentSubTask1, TaskStatus.IN_PROGRESS, null, "Тут описание субтаски 6");
        taskManager.checkEpicStatusForProgress(currentEpic1);
        System.out.println("Поменяем статус подзадаче 12"); // эпик 10
        Subtask currentSubTask2 = taskManager.subtasks.get(12);
        Epic currentEpic2 = taskManager.epics.get(10);
        taskManager.updateSubTask(currentSubTask2, TaskStatus.IN_PROGRESS, null, "Тут описание субтаски 12");
        taskManager.checkEpicStatusForProgress(currentEpic2);
        System.out.println(currentEpic1);
        System.out.println(currentSubTask1);
        System.out.println(currentEpic2);
        System.out.println(currentSubTask2);
//завершим эпик перведя позадачи в Done - эпик 4, задачи 6 и 8
        System.out.println("Поменяем статус подзадаче 6"); //эпик 4
        Subtask currentSubTask3 = taskManager.subtasks.get(6);
        Epic currentEpic3 = taskManager.epics.get(4);
        taskManager.updateSubTask(currentSubTask3, TaskStatus.DONE, null, "Тут описание субтаски 6");
        taskManager.checkEpicStatusForDone(currentEpic3);
        Epic checkerEpic = taskManager.epics.get(4);
        System.out.println(checkerEpic);
        System.out.println("Поменяем статус подзадаче 8"); //эпик 4
        Subtask currentSubTask4 = taskManager.subtasks.get(8);
        Epic currentEpic4 = taskManager.epics.get(4);
        taskManager.updateSubTask(currentSubTask4, TaskStatus.DONE, null, "Тут описание субтаски 8");
        taskManager.checkEpicStatusForDone(currentEpic4);
        checkerEpic = taskManager.epics.get(4);
        System.out.println(checkerEpic);
        // здесь я сознательно положил один и тот же эпик в три разных переменных - для точного отслеживания изенения статуса эпика
// удалить одну из задач
        System.out.println("Удалим таску 1");
        currentTask = taskManager.tasks.get(1);
        taskManager.deleteTask(currentTask);
        System.out.println(taskManager.tasks);
// удалить один из эпиков
        System.out.println("Удалим эпик номер 10");
        Epic currentEpic = taskManager.epics.get(10);
        taskManager.deleteEpic(currentEpic);
        System.out.println(taskManager.epics);
    }
}
