package tracker.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {
    private Subtask subTaskOne;
    private Subtask subTaskTwo;

    @Test
    public void shouldReturnTrueWhenEpicsHaveTheSameId() {
        subTaskOne = new Subtask(7, "Subtask one", "Do something", Status.NEW);
        subTaskTwo = new Subtask(7, "Subtask two", "Do something else", Status.NEW);
        subTaskOne.setId(1);
        subTaskTwo.setId(1);
        assertTrue(subTaskOne.equals(subTaskTwo), "Subtasks with the same id should be equal");
    }
}