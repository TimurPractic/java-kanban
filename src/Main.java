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
        Subtask subtask1 = new Subtask("Это первая подзадача", epic.getId());
        taskManager.addSubTask(subtask1);
        Subtask subtask2 = new Subtask("Это вторая подзадача", epic.getId());
        taskManager.addSubTask(subtask2);
// создать эпик с одной подзадачей.
        System.out.println("Cоздаём эпик с одной подзадачей");
        Epic epic1 = new Epic("Это эпик с одной подзадачей");
        taskManager.addEpic(epic1);
        Subtask subtask3 = new Subtask("Это единственная подзадача", epic1.getId());
        taskManager.addSubTask(subtask3);
// Распечатайте списки задач
        System.out.println("Распечатаем списки задач");
        System.out.println(taskManager.getTasks());
// Распечатайте списки эпиков
        System.out.println("Распечатаем списки эпиков");
        System.out.println(taskManager.getEpics());
// Распечатайте списки задач
        System.out.println("Распечатаем списки субтасков");
        System.out.println(taskManager.getSubtasks());
// Измените статусы созданных тасков, распечатайте их.
        System.out.println("Поменяем статус таске 1");
        Task currentTask = taskManager.getTasks().get(1);
        taskManager.updateTask(currentTask, TaskStatus.IN_PROGRESS, null, "Описание");
        System.out.println(currentTask);
// Измените статусы созданных субтасков, распечатайте их.
        System.out.println("Поменяем статус подзадаче 4"); //эпик 3
        Subtask currentSubTask1 = taskManager.getSubtasks().get(4);
        Epic currentEpic1 = taskManager.getEpics().get(3);
        taskManager.updateSubTask(currentSubTask1, TaskStatus.IN_PROGRESS, null, "Тут описание субтаски 6");
        System.out.println("Поменяем статус подзадаче 7"); // эпик 6
        Subtask currentSubTask2 = taskManager.getSubtasks().get(7);
        Epic currentEpic2 = taskManager.getEpics().get(6);
        taskManager.updateSubTask(currentSubTask2, TaskStatus.IN_PROGRESS, null, "Тут описание субтаски 12");
        System.out.println(currentEpic1);
        System.out.println(currentSubTask1);
        System.out.println(currentEpic2);
        System.out.println(currentSubTask2);
//завершим эпик перведя позадачи в Done - эпик 3, задачи 4 и 5, эпик 6, задача 7
        System.out.println("Поменяем статус подзадаче 4"); //эпик 3
        Subtask currentSubTask3 = taskManager.getSubtasks().get(4);
        taskManager.updateSubTask(currentSubTask3, TaskStatus.DONE, null, "Тут описание субтаски 6");
        Epic checkerEpic = taskManager.getEpics().get(3);
        System.out.println(checkerEpic);
        System.out.println("Поменяем статус подзадаче 7"); //эпик 6
        Subtask currentSubTask4 = taskManager.getSubtasks().get(7);
        taskManager.updateSubTask(currentSubTask4, TaskStatus.DONE, null, "Тут описание субтаски 8");
        checkerEpic = taskManager.getEpics().get(6);
        System.out.println(checkerEpic);
// удалить одну из задач
        System.out.println("Удалим таску 1");
        currentTask = taskManager.getTasks().get(1);
        taskManager.deleteTask(currentTask);
        System.out.println(taskManager.getTasks());
// удалить один из эпиков
        System.out.println("Удалим эпик номер 3");
        Epic currentEpic = taskManager.getEpics().get(3);
        taskManager.deleteEpic(currentEpic);
        System.out.println(taskManager.getEpics());
    }
}
