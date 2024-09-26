package tracker.managers;

import tracker.exceptions.ManagerLoadException;
import tracker.exceptions.ManagerSaveException;
import tracker.models.Epic;
import tracker.models.Status;
import tracker.models.Subtask;
import tracker.models.Task;
import tracker.models.Type;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    @Override
    public Task addNewTask(Task newTask) {
        Task addedTask = super.addNewTask(newTask);
        save();
        return addedTask;
    }

    @Override
    public Task updateTask(Task updatedTask) {
        Task refreshedTask = super.updateTask(updatedTask);
        save();
        return refreshedTask;
    }

    @Override
    public void deleteTaskById(int id) {
        super.deleteTaskById(id);
        save();
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public Epic addNewEpic(Epic newEpic) {
        Epic addedEpic = super.addNewEpic(newEpic);
        save();
        return addedEpic;
    }

    @Override
    public Epic updateEpic(Epic updatedEpic) {
        Epic refreshedEpic = super.updateEpic(updatedEpic);
        save();
        return refreshedEpic;
    }

    @Override
    public void deleteEpicById(int id) {
        super.deleteEpicById(id);
        save();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public Subtask addNewSubtask(Subtask newSubtask) {
        Subtask addedSubtask = super.addNewSubtask(newSubtask);
        save();
        return addedSubtask;
    }

    @Override
    public Subtask updateSubtask(Subtask updatedSubtask) {
        Subtask refreshedSubtask = super.updateSubtask(updatedSubtask);
        save();
        return refreshedSubtask;
    }

    @Override
    public void deleteSubtaskById(int id) {
        super.deleteSubtaskById(id);
        save();
    }

    @Override
    public void deleteAllSubtasks() {
        super.deleteAllSubtasks();
        save();
    }

    public void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            List<Task> allTasks = getAllTasks();
            List<Epic> allEpics = getAllEpics();
            List<Subtask> allSubtasks = getAllSubtasks();

            bw.write("ID,TYPE,NAME,DESCRIPTION,STATUS,EPICID");
            bw.newLine();

            for (Task task : allTasks) {
                bw.write(toCSV(task));
                bw.newLine();
            }

            for (Epic epic : allEpics) {
                bw.write(toCSV(epic));
                bw.newLine();
            }

            for (Subtask subtask : allSubtasks) {
                bw.write(toCSV(subtask));
                bw.newLine();
            }
        } catch (IOException e) {
            throw new ManagerSaveException(
                    "Error saving to file" + e.getMessage());
        }
    }

    public static TaskManager loadFromFile(File file) {
        FileBackedTaskManager taskManager = new FileBackedTaskManager(file);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String title = br.readLine();

            String line;
            int maxId = 0;

            while ((line = br.readLine()) != null) {
                Task task = fromString(line);

                Type type = task.getType();

                if (task.getId() > maxId) {
                    maxId = task.getId();
                }
                switch (type) {
                    case EPIC:
                        taskManager.addNewEpic((Epic) task);
                        break;
                    case SUBTASK:
                        taskManager.addNewSubtask((Subtask) task);
                        break;
                    case TASK:
                        taskManager.addNewTask(task);
                        break;
                    default:
                        throw new IllegalArgumentException(
                                "Unknown task type: " + type);
                }
            }
            taskManager.restoreId(maxId);
            return taskManager;
        } catch (IOException e) {
            throw new ManagerLoadException(
                    "Error reading file" + e.getMessage());
        }
    }

    public String toCSV(Task task) {
        if (task instanceof Subtask) {
            Subtask subtask = (Subtask) task;
            return String.format("%s,%s,%s,%s,%s,%s", task.getId(), task.getType(), task.getName(), task.getDescription(), task.getStatus(), subtask.getEpicId());
        } else {
            return String.format("%s,%s,%s,%s,%s,", task.getId(), task.getType(), task.getName(), task.getDescription(), task.getStatus());
        }
    }

    public static Task fromString(String line) {
        String[] contents = line.split(",");
        int id;
        int epicId = -1;
        String name;
        String description;
        Status status;
        Type type;

        try {
            id = Integer.parseInt(contents[0]);
            name = contents[2];
            description = contents[3];
            status = Status.valueOf(contents[4]);
            type = Type.valueOf(contents[1]);

            if (type == Type.SUBTASK && contents.length > 5) {
                epicId = Integer.parseInt(contents[5]);
            }
        } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Error processing line: " + line, e);
        }

        switch (type) {
            case TASK:
                return new Task(id, name, description, status);
            case EPIC:
                return new Epic(id, name, description, status);
            case SUBTASK:
                return new Subtask(epicId, id, name, description, status);
            default:
                throw new IllegalArgumentException(
                        "Unknown task type: " + type);
        }
    }

    public void restoreId(int maxId) {
        setId(maxId);
    }

    public static void main(String[] args) {
        System.out.println("Let's go!");

        File file = new File("src/resources/data.csv");
        TaskManager taskManager;

        // File existence test
        if (file.exists()) {
            // If file exists, load the data
            taskManager = Managers.getFileBackedTaskManager(file);
            System.out.println("Data loaded from " + file.getName());
        } else {
            // If file doesn't exist, create new manager
            taskManager = Managers.getFileBackedTaskManager(file);
            System.out.println(
                    "New task manager created. File " + file.getName() +
                            " will be used to save the data.");
        }

        Task taskOne = taskManager.addNewTask(new Task("Задача 1", "Clean the floors in all rooms"));
        Task taskTwo = taskManager.addNewTask(new Task("Задача 2", "Buy groceries for dinner"));

        Epic epicOne = taskManager.addNewEpic(new Epic("Epic 1", "Moving"));
        Epic epicTwo = taskManager.addNewEpic(new Epic("Epic 2", "Exam preparation"));

        Subtask subTaskOneOfEpicOne = taskManager.addNewSubtask(new Subtask(epicOne.getId(), "Subtask 1 of Epic 1", "Order the transportation of items", Status.NEW));
        Subtask subTaskTwoOfEpicOne = taskManager.addNewSubtask(new Subtask(epicOne.getId(), "Subtask 2 of Epic 1", "Clean up the previous apartment", Status.NEW));
        Subtask subTaskThreeOfEpicOne = taskManager.addNewSubtask(new Subtask(epicOne.getId(), "Subtask 3 of Epic 1", "Return the apartment keys", Status.NEW));

        printAllTasks(taskManager);
    }

    public static void printAllTasks(TaskManager manager) {
        System.out.println("Tasks:");
        for (Task task : manager.getAllTasks()) {
            System.out.println(task);
        }
        System.out.println("Epics:");
        for (Task epic : manager.getAllEpics()) {
            System.out.println(epic);

            for (Task task : manager.getSubtasksOfEpic(epic.getId())) {
                System.out.println("--> " + task);
            }
        }
        System.out.println("Subtasks:");
        for (Task subtask : manager.getAllSubtasks()) {
            System.out.println(subtask);
        }
    }
}
