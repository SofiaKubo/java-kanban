import tracker.managers.Managers;
import tracker.managers.TaskManager;
import tracker.models.Epic;
import tracker.models.Status;
import tracker.models.Subtask;
import tracker.models.Task;

public class Main {

    public static void main(String[] args) {
        System.out.println("Let's go!");

        TaskManager taskManager = Managers.getDefault();
        Task taskOne = taskManager.addNewTask(new Task("Task 1", "Clean the floors in all rooms"));
        Task taskTwo = taskManager.addNewTask(new Task("Task 2", "Buy groceries for dinner"));

        Epic epicOne = taskManager.addNewEpic(new Epic("Epic 1", "Moving"));
        Epic epicTwo = taskManager.addNewEpic(new Epic("Epic 2", "Exam preparation"));

        Subtask subTaskOneOfEpicOne = taskManager.addNewSubtask(new Subtask(epicOne.getId(), "Subtask 1 of Epic 1",
                "Order the transportation of items", Status.NEW));
        Subtask subTaskTwoOfEpicOne = taskManager.addNewSubtask(new Subtask(epicOne.getId(), "Subtask 2 of Epic 1",
                "Clean up the previous apartment", Status.NEW));
        Subtask subTaskThreeOfEpicOne = taskManager.addNewSubtask(new Subtask(epicOne.getId(), "Subtask 3 of Epic 1",
                "Return the apartment keys", Status.NEW));

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
        System.out.println("History:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
    }
}


