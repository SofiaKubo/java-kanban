package tracker.managers;

import tracker.models.Epic;
import tracker.models.Status;
import tracker.models.Subtask;
import tracker.models.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, Subtask> subtasks = new HashMap<>();

    private HistoryManager historyManager;

    public InMemoryTaskManager() {
        this.historyManager = Managers.getDefaultHistory();
    }

    private int id = 1;

    private int generateNewId() {
        return id++;
    }

    // *********** методы для взаимодействия с Задачами ***********

    @Override
    public Task addNewTask(Task newTask) {
        int newTaskId = generateNewId();
        newTask.setId(newTaskId);
        tasks.put(newTask.getId(), newTask);
        System.out.println("Task added");
        return newTask;
    }

    @Override
    public Task updateTask(Task updatedTask) {
        int id = updatedTask.getId();
        if (tasks.containsKey(id)) {
            tasks.put(id, updatedTask);
            System.out.println("Task updated");
            return updatedTask;
        } else {
            System.out.println("Task not found");
            return null;
        }
    }

    @Override
    public Task getTaskById(int id) {
        if (tasks.containsKey(id)) {
            historyManager.add(tasks.get(id));
            return tasks.get(id);
        }
        System.out.println("Task not found");
        return null;
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public void deleteTaskById(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
            historyManager.remove(id);
        } else {
            System.out.println("Task not found");
        }
    }

    @Override
    public void deleteAllTasks() {
        for (Integer taskId : tasks.keySet()) {
            historyManager.remove(taskId);
        }
        tasks.clear();
    }

    // *********** методы для взаимодействия с Эпиками ***********

    private void updateEpicStatus(int epicId) {
        Epic updatedEpic = epics.get(epicId);

        int counterNew = 0;
        int counterDone = 0;

        if (updatedEpic.getSubtasksId().isEmpty()) {
            updatedEpic.setStatus(Status.NEW);
            return;
        }
        for (int id : updatedEpic.getSubtasksId()) {
            if (subtasks.get(id).getStatus() == Status.NEW) {
                counterNew++;
            } else if (subtasks.get(id).getStatus() == Status.DONE) {
                counterDone++;
            }
        }
        if ((counterNew == updatedEpic.getSubtasksId().size())) {
            updatedEpic.setStatus(Status.NEW);
        } else if (counterDone == updatedEpic.getSubtasksId().size()) {
            updatedEpic.setStatus(Status.DONE);
        } else {
            updatedEpic.setStatus(Status.IN_PROGRESS);
        }
    }

    @Override
    public Epic addNewEpic(Epic newEpic) {
        if (newEpic == null) {
            System.out.println("Input error");
            return null;
        }
        int newEpicId = generateNewId();
        newEpic.setId(newEpicId);
        epics.put(newEpicId, newEpic);
        updateEpicStatus(newEpicId);
        System.out.println("Epic added");
        return newEpic;
    }

    @Override
    public Epic updateEpic(Epic updatedEpic) {
        int id = updatedEpic.getId();
        if (epics.containsKey(id)) {
            Epic changedEpic = epics.get(id);
            changedEpic.setName(updatedEpic.getName());
            changedEpic.setDescription(updatedEpic.getDescription());
            System.out.println("Epic updated");
            return updatedEpic;
        } else {
            System.out.println("Epic not found");
            return null;
        }
    }

    @Override
    public Epic getEpicById(int id) {
        if (epics.containsKey(id)) {
            historyManager.add(epics.get(id));
            return epics.get(id);
        }
        System.out.println("Epic not found");
        return null;
    }

    @Override
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public void deleteEpicById(int id) {
        if (epics.containsKey(id)) {
            Epic deletedEpic = epics.get(id);
            for (Integer subtaskId : deletedEpic.getSubtasksId()) {
                subtasks.remove(subtaskId);
                historyManager.remove(subtaskId);
            }
            epics.remove(id);
            historyManager.remove(id);
        } else {
            System.out.println("Epic not found");
        }
    }

    @Override
    public void deleteAllEpics() {
        for (Subtask subtask : subtasks.values()) {
            historyManager.remove(subtask.getId());
        }
        for (Integer epicId : epics.keySet()) {
            historyManager.remove(epicId);
        }
        epics.clear();
        subtasks.clear();
    }

    // *********** методы для взаимодействия с подзадачами ***********

    @Override
    public Subtask addNewSubtask(Subtask newSubtask) {
        int epicId = newSubtask.getEpicId();
        if (!epics.containsKey(epicId)) {
            System.out.println("Epic not found");
            return null;
        }
        int newSubtaskId = generateNewId();
        newSubtask.setId(newSubtaskId);
        subtasks.put(newSubtaskId, newSubtask);
        Epic relatedEpic = epics.get(epicId);
        relatedEpic.addSubtaskIds(newSubtaskId);
        updateEpicStatus(epicId);
        System.out.println("Subtask added");
        return newSubtask;
    }

    @Override
    public Subtask updateSubtask(Subtask subtask) {
        Subtask existingSubtask = subtasks.get(subtask.getId());
        if (existingSubtask == null) {
            System.out.println("Subtask not found");
            return null;
        }
        Epic epic = epics.get(existingSubtask.getEpicId());
        if (epic == null) {
            System.out.println("Epic not found");
            return null;
        }
        if (!existingSubtask.getEpicId().equals(subtask.getEpicId())) {
            System.out.println("Can't change the EpicId of the subtask");
            return null;
        }
        subtasks.put(subtask.getId(), subtask);
        updateEpicStatus(epic.getId());
        return subtask;
    }

    @Override
    public ArrayList<Subtask> getSubtasksOfEpic(int epicId) {
        if (epics.containsKey(epicId)) {
            Epic relatedEpic = epics.get(epicId);
            ArrayList<Integer> subtasksId = relatedEpic.getSubtasksId();
            ArrayList<Subtask> subtasksOfEpic = new ArrayList<>();
            for (int id : subtasksId) {
                subtasksOfEpic.add(subtasks.get(id));
            }
            return subtasksOfEpic;
        }
        System.out.println("Subtasks not found");
        return null;
    }

    @Override
    public Subtask getSubtaskById(int id) {
        if (subtasks.containsKey(id)) {
            historyManager.add(subtasks.get(id));
            return subtasks.get(id);
        }
        System.out.println("Subtask not found");
        return null;
    }

    @Override
    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public void deleteSubtaskById(int id) {
        Subtask subtask = subtasks.remove(id);
        if (subtask == null) {
            return;
        }
        Epic epic = epics.get(subtask.getEpicId());
        epic.deleteSubtaskId(id);
        historyManager.remove(id);
        updateEpicStatus(epic.getId());
    }

    @Override
    public void deleteAllSubtasks() {
        for (Integer subtaskId : subtasks.keySet()) {
            historyManager.remove(subtaskId);
        }
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.clearSubtaskIds();
            updateEpicStatus(epic.getId());
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
}