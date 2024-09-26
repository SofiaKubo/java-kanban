package tracker.models;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subtasksId = new ArrayList<>();

    public ArrayList<Integer> getSubtasksId() {
        return new ArrayList<>(subtasksId);
    }

    public void addSubtaskIds(Integer id) {
        subtasksId.add(id);
    }

    public void clearSubtaskIds() {
        subtasksId.clear();
    }

    public void deleteSubtaskId(Integer subtaskId) {
        subtasksId.remove(subtaskId);
    }

    public Epic(String name, String description) {
        super(name, description, Status.NEW);
        this.setType(Type.EPIC);
    }

    public Epic(Integer id, String name, String description, Status status) {
        super(id, name, description, Status.NEW);
        this.setType(Type.EPIC);
    }
}
