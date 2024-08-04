import org.w3c.dom.ls.LSOutput;
import tracker.manager.TaskManager;
import tracker.models.Epic;
import tracker.models.Subtask;
import tracker.models.Status;
import tracker.models.Task;

public class Main {

    public static void main(String[] args) {

        System.out.println("Поехали!");
        TaskManager taskManager = new TaskManager();
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

        System.out.println(taskManager.getEpicById(epicOne.getId()));

        taskManager.deleteTaskById(taskOne.getId());
        System.out.println(taskManager.getAllTasks());

        taskManager.deleteEpicById(epicTwo.getId());
        System.out.println(taskManager.getAllEpics());
    }
}
