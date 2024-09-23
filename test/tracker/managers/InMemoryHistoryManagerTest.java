package tracker.managers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tracker.models.Status;
import tracker.models.Task;

import java.util.List;

class InMemoryHistoryManagerTest {
    private HistoryManager historyManager;
    private TaskManager taskManager;

    @BeforeEach
    void init() {
        historyManager = Managers.getDefaultHistory();
        taskManager = Managers.getDefault();
    }

    @Test
    void shouldAddTasksAtHistoryList() {
        // prepare
        Task task = new Task("Task one", "Do something", Status.NEW);

        // do
        historyManager.add(task);
        final List<Task> history = historyManager.getHistory();

        // check
        Assertions.assertNotNull(history, "History is not empty");
        Assertions.assertEquals(1, history.size(), "History is not empty");
    }

    @Test
    void shouldAddNewTaskAtTheEndOfHistoryList() {
        // prepare
        Task taskOne = new Task("Task one", "Do something", Status.NEW);
        Task taskTwo = new Task("Task two", "Do something else", Status.NEW);

        // do
        historyManager.add(taskOne);
        historyManager.add(taskTwo);
        final List<Task> history = historyManager.getHistory();

        // check
        Assertions.assertEquals(taskTwo, history.get(history.size() - 1), "Task is the last in the history");
    }

    @Test
    void shouldAddTheSameTaskJustOneTimeAtHistoryList() {
        // prepare
        Task taskOne = new Task("Task one", "Do something", Status.NEW);

        // do
        historyManager.add(taskOne);
        historyManager.add(taskOne);
        historyManager.add(taskOne);
        final List<Task> history = historyManager.getHistory();

        // check
        Assertions.assertEquals(1, history.size(), "Task wasn't duplicated in the history");
    }

    @Test
    void whenTaskHasDeletedShouldDeleteItFromHistoryList() {
        // prepare
        Task taskOne = new Task("Task one", "Do something", Status.NEW);
        taskManager.addNewTask(taskOne);
        taskManager.getTaskById(taskOne.getId());

        // do
        taskManager.deleteTaskById(taskOne.getId());
        final List<Task> history = historyManager.getHistory();

        // check
        Assertions.assertEquals(0, history.size(), "Task wasn't removed from the history");
    }
}