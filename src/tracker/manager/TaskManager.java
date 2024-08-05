package tracker.manager;

import tracker.models.Epic;
import tracker.models.Status;
import tracker.models.Subtask;
import tracker.models.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TaskManager {
    private Map<Integer, Task> tasks = new HashMap<>();
    private Map<Integer, Epic> epics = new HashMap<>();
    private Map<Integer, Subtask> subtasks = new HashMap<>();

    private int id = 1;

    private int generateNewId() {
        return id++;
    }

    // *********** методы для взаимодействия с Задачами ***********

    public Task addNewTask(Task newTask) {
        int newTaskId = generateNewId();
        newTask.setId(newTaskId);
        tasks.put(newTask.getId(), newTask);
        System.out.println("Задача добавлена");
        return newTask;
    }

    public Task updateTask(Task updatedTask) {
        int id = updatedTask.getId();
        if (tasks.containsKey(id)) {
            tasks.put(id, updatedTask);
            System.out.println("Задача обновлена");
            return updatedTask;
        } else {
            System.out.println("Задача отсутствует");
            return null;
        }
    }

    public Task getTaskById(int id) {
        if (tasks.containsKey(id)) {
            return tasks.get(id);
        }
        System.out.println("Задача отсутствует");
        return null;
    }

    public ArrayList<Task> getAllTasks() {
        if (!tasks.isEmpty()) {
            return new ArrayList<>(tasks.values());
        }
        System.out.println("Задачи отсутствуют");
        return null;
    }

    public void deleteTaskById(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        } else {
            System.out.println("Задача отсутствует");
        }
    }

    public void deleteAllTasks() {
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

    public Epic addNewEpic(Epic newEpic) {
        if (newEpic == null) {
            System.out.println("Ошибка ввода");
            return null;
        }
        int newEpicId = generateNewId();
        newEpic.setId(newEpicId);
        epics.put(newEpicId, newEpic);
        updateEpicStatus(newEpicId);
        System.out.println("Эпик добавлен");
        return newEpic;
    }

    public Epic updateEpic(Epic updatedEpic) {
        int id = updatedEpic.getId();
        if (epics.containsKey(id)) {
            Epic changedEpic = epics.get(id);
            changedEpic.setName(updatedEpic.getName());
            changedEpic.setDescription(updatedEpic.getDescription());
            System.out.println("Эпик обновлен");
            return updatedEpic;
        } else {
            System.out.println("Эпик отсутствует");
            return null;
        }
    }

    public Epic getEpicById(int id) {
        if (epics.containsKey(id)) {
            return epics.get(id);
        }
        System.out.println("Эпик отсутствует");
        return null;
    }

    public ArrayList<Epic> getAllEpics() {
        if (!epics.isEmpty()) {
            return new ArrayList<>(epics.values());
        }
        System.out.println("Эпики отсутствуют");
        return null;
    }

    public void deleteEpicById(int id) {
        if (epics.containsKey(id)) {
            Epic deletedEpic = epics.get(id);
            for (Integer subtaskId : deletedEpic.getSubtasksId()) {
                subtasks.remove(subtaskId);
            }
            epics.remove(id);
        } else {
            System.out.println("Эпик отсутствует");
        }
    }

    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    // *********** методы для взаимодействия с подзадачами ***********

    public Subtask addNewSubtask(Subtask newSubtask) {
        int epicId = newSubtask.getEpicId();
        if (!epics.containsKey(epicId)) {
            System.out.println("Эпик отсутствует");
            return null;
        }
        int newSubtaskId = generateNewId();
        newSubtask.setId(newSubtaskId);
        subtasks.put(newSubtaskId, newSubtask);
        Epic relatedEpic = epics.get(epicId);
        relatedEpic.addSubtaskIds(newSubtaskId);
        updateEpicStatus(epicId);
        System.out.println("Подзадача добавлена");
        return newSubtask;
    }

    public void updateSubtask(Subtask subtask) {
        Subtask updatedSubtask = subtasks.get(subtask.getId());
        if (updatedSubtask == null) {
            System.out.println("Подзадача отсутствует");
            return;
        }
        Epic epic = epics.get(subtask.getEpicId());
        if (epic == null) {
            System.out.println("Эпик отсутствует");
            return;
        }
        if (!updatedSubtask.getEpicId().equals(subtask.getEpicId())) {
            System.out.println("Нельзя обновить подзадачу");
            return;
        }
        subtasks.put(subtask.getId(), subtask);
        updateEpicStatus(epic.getId());
    }

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
        System.out.println("Подзадачи отсутствуют");
        return null;
    }

    public Subtask getSubtaskById(int id) {
        return subtasks.get(id);
    }

    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public void deleteSubtaskById(int id) {
        Subtask subtask = subtasks.remove(id);
        if (subtask == null) {
            return;
        }
        Epic epic = epics.get(subtask.getEpicId());
        epic.deleteSubtaskId(id);
        updateEpicStatus(epic.getId());
    }

    public void deleteAllSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.clearSubtaskIds();
            updateEpicStatus(epic.getId());
        }
    }
}

