package tracker.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    private Task taskOne;
    private Task taskTwo;

    @Test
    public void shouldReturnTrueWhenTasksHaveTheSameId() {
        taskOne = new Task("Task one","Do something", Status.NEW);
        taskTwo = new Task("Task two","Do something else", Status.NEW);
        taskOne.setId(1);
        taskTwo.setId(1);
        assertTrue(taskOne.equals(taskTwo), "Tasks with the same id should be equal");
    }
}