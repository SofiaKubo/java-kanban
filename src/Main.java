import tracker.managers.Managers;
import tracker.managers.TaskManager;
import tracker.models.Epic;
import tracker.models.Status;
import tracker.models.Subtask;
import tracker.models.Task;

public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");

        TaskManager taskManager = Managers.getDefault();
        Task taskOne = taskManager.addNewTask(new Task("Задача 1", "Помыть пол во всех комнатах"));
        Task taskTwo = taskManager.addNewTask(new Task("Задача 2", "Купить продукты к ужину"));

        Epic epicOne = taskManager.addNewEpic(new Epic("Эпик 1", "Переезд"));
        Epic epicTwo = taskManager.addNewEpic(new Epic("Эпик 2", "Подготовка к экзамену"));

        Subtask subTaskOneOfEpicOne = taskManager.addNewSubtask(new Subtask(epicOne.getId(), "Подзадача 1 к Эпику 1",
                "Заказать перевозку вещей", Status.NEW));
        Subtask subTaskTwoOfEpicOne = taskManager.addNewSubtask(new Subtask(epicOne.getId(), "Подзадача 2 к Эпику 1",
                "Сделать уборку в старой квартире", Status.NEW));
        Subtask subTaskOneOfEpicTwo = taskManager.addNewSubtask(new Subtask(epicTwo.getId(), "Подзадача 1 к Эпику 2",
                "Повторить формулы", Status.NEW));
        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpics());
        System.out.println(taskManager.getAllSubtasks());

        Task newTask = new Task(taskOne.getId(), "Задача 1", "Помыть пол во всех комнатах", Status.IN_PROGRESS);
        System.out.println(taskManager.updateTask(newTask));

        Subtask updatedSubTaskOne = new Subtask(epicOne.getId(), "Подзадача 1 к Эпику 1", "Заказать перевозку вещей", Status.DONE);
        updatedSubTaskOne.setId(subTaskOneOfEpicOne.getId());
        taskManager.updateSubtask(updatedSubTaskOne);

        Subtask updatedSubTaskTwo = new Subtask(epicOne.getId(), "Подзадача 2 к Эпику 1", "Сделать уборку в старой квартире", Status.DONE);
        updatedSubTaskTwo.setId(subTaskTwoOfEpicOne.getId());
        taskManager.updateSubtask(updatedSubTaskTwo);

        /*taskManager.deleteTaskById(taskOne.getId());
        System.out.println(taskManager.getAllTasks());*/

       /* taskManager.deleteEpicById(epicTwo.getId());
        System.out.println(taskManager.getAllEpics());
*/
        taskManager.getTaskById(taskOne.getId());
        taskManager.getEpicById(epicOne.getId());
        taskManager.getSubtaskById(subTaskOneOfEpicOne.getId());
        System.out.println("История:");
        for (Task task : taskManager.getHistory()) {
            System.out.println(task);
        }
        taskManager.getEpicById(epicOne.getId());
        taskManager.getSubtaskById(subTaskOneOfEpicOne.getId());
        taskManager.getTaskById(taskOne.getId());
        System.out.println("История:");
        for (Task task : taskManager.getHistory()) {
            System.out.println(task);
        }

        taskManager.deleteTaskById(taskOne.getId());
        System.out.println("История:");
        for (Task task : taskManager.getHistory()) {
            System.out.println(task);
        }
        taskManager.deleteEpicById(epicOne.getId());
        System.out.println("История:");
        for (Task task : taskManager.getHistory()) {
            System.out.println(task);
        }

        printAllTasks(taskManager);
        taskManager.getHistory();
    }

    public static void printAllTasks(TaskManager manager) {
        System.out.println("Задачи:");
        for (Task task : manager.getAllTasks()) {
            System.out.println(task);
        }
        System.out.println("Эпики:");
        for (Epic epic : manager.getAllEpics()) {
            System.out.println(epic);

            for (Subtask subtask : manager.getSubtasksOfEpic(epic.getId())) {
                System.out.println("--> " + subtask);
            }
        }
        System.out.println("Подзадачи:");
        for (Subtask subtask : manager.getAllSubtasks()) {
            System.out.println(subtask);
        }

        System.out.println("История:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
    }
}


