package tracker.models;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subtasksId = new ArrayList<>();

    public ArrayList<Integer> getSubtasksId() {
        return subtasksId;
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

    public void setSubtasksId(ArrayList<Integer> subtasksId) {
        this.subtasksId = subtasksId;
    }

    public Epic(String name, String description) {
        super(name, description, Status.NEW);
    }

    public Epic(String name, String description, Status status) {
        super(name, description, status);
    }

    public Epic(Integer id, String name, String description) {
        super(id, name, description);
    }
}
