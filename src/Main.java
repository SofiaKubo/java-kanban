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
        Subtask subTaskThreeOfEpicOne = taskManager.addNewSubtask(new Subtask(epicOne.getId(), "Подзадача 3 к Эпику 1",
                "Повторить формулы", Status.NEW));

        taskManager.getTaskById(taskOne.getId());
        taskManager.getTaskById(taskTwo.getId());
        taskManager.getEpicById(epicOne.getId());
        taskManager.getSubtaskById(subTaskOneOfEpicOne.getId());
        taskManager.getSubtaskById(subTaskTwoOfEpicOne.getId());
        taskManager.getSubtaskById(subTaskThreeOfEpicOne.getId());
        taskManager.getEpicById(epicTwo.getId());

        printHistory(taskManager);

        taskManager.getSubtaskById(subTaskOneOfEpicOne.getId());
        taskManager.getTaskById(taskTwo.getId());
        taskManager.getEpicById(epicTwo.getId());
        taskManager.getTaskById(taskOne.getId());
        taskManager.getSubtaskById(subTaskThreeOfEpicOne.getId());
        taskManager.getEpicById(epicOne.getId());
        taskManager.getSubtaskById(subTaskTwoOfEpicOne.getId());

        printHistory(taskManager);

        taskManager.deleteTaskById(taskOne.getId());
        printHistory(taskManager);

        taskManager.deleteEpicById(epicOne.getId());
        printHistory(taskManager);
    }

    public static void printHistory(TaskManager manager) {
        System.out.println("История:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
    }
}


