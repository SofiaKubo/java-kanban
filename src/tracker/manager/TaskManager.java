package tracker.manager;

import tracker.models.Epic;
import tracker.models.Subtask;
import tracker.models.Task;

import java.util.ArrayList;

public interface TaskManager {
    Task addNewTask(Task newTask);

    Task updateTask(Task updatedTask);

    Task getTaskById(int id);

    ArrayList<Task> getAllTasks();

    void deleteTaskById(int id);

    void deleteAllTasks();

    Epic addNewEpic(Epic newEpic);

    Epic updateEpic(Epic updatedEpic);

    Epic getEpicById(int id);

    ArrayList<Epic> getAllEpics();

    void deleteEpicById(int id);

    void deleteAllEpics();

    Subtask addNewSubtask(Subtask newSubtask);

    Subtask updateSubtask(Subtask subtask);

    ArrayList<Subtask> getSubtasksOfEpic(int epicId);

    Subtask getSubtaskById(int id);

    ArrayList<Subtask> getAllSubtasks();

    void deleteSubtaskById(int id);

    void deleteAllSubtasks();

    HistoryManager getHistoryManager();
}
