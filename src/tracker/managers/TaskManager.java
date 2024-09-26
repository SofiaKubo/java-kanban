package tracker.managers;

import tracker.models.Epic;
import tracker.models.Subtask;
import tracker.models.Task;

import java.util.List;

public interface TaskManager {
    Task addNewTask(Task newTask);

    Task updateTask(Task updatedTask);

    Task getTaskById(int id);

    List<Task> getAllTasks();

    void deleteTaskById(int id);

    void deleteAllTasks();

    Epic addNewEpic(Epic newEpic);

    Epic updateEpic(Epic updatedEpic);

    Epic getEpicById(int id);

    List<Epic> getAllEpics();

    void deleteEpicById(int id);

    void deleteAllEpics();

    Subtask addNewSubtask(Subtask newSubtask);

    Subtask updateSubtask(Subtask subtask);

    List<Subtask> getSubtasksOfEpic(int epicId);

    Subtask getSubtaskById(int id);

    List<Subtask> getAllSubtasks();

    void deleteSubtaskById(int id);

    void deleteAllSubtasks();

    List<Task> getHistory();

}
