package tracker.models;

import java.util.Objects;

public class Task {
    private Integer id;
    private String name;
    private Status status;
    private String description;

    public Task(Integer id, String name, String description, Status status) {
        this.name = name;
        this.status = status;
        this.id = id;
        this.description = description;
    }

    public Task(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.status = Status.NEW;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) && Objects.equals(name, task.name)
                && status == task.status && Objects.equals(description, task.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, status, description);
    }

    @Override
    public String toString() {
        return " id = '" + id + '\'' + ", name = '" + name + '\'' +
                ", status = '" + status + '\'' +
                ", description = '" + description + '\'';
    }
}
