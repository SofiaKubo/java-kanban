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
        this.setType(Type.SUBTASK);
    }

    public Subtask(Integer epicId, Integer id, String name, String description, Status status) {
        super(id, name, description, Status.NEW);
        this.epicId = epicId;
        this.setType(Type.SUBTASK);
    }

    public Integer getEpicId() {
        return epicId;
    }
}

