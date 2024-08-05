package tracker.models;

public class Subtask extends Task {
    private Integer epicId;

    public Subtask(Integer epicId, String name, String description) {
        super(name, description);
        this.epicId = epicId;
    }

    public Subtask(Integer epicId, String name, String description, Status status) {
        super(name, description, status);
        this.epicId = epicId;
    }

    public Integer getEpicId() {
        return epicId;
    }
}

