package tracker.manager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tracker.models.Status;
import tracker.models.Task;

import java.util.List;

class InMemoryHistoryManagerTest {
    private HistoryManager historyManager;

    @BeforeEach
    void init() {
        historyManager = Managers.getDefaultHistory();
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
    void shouldGetHistory() {
        // prepare
        for (int i = 1; i <= 15; i++) {
            Task task = new Task("Task " + i, "Description " + i);
            historyManager.add(task);
        }
        // do
        final List<Task> history = historyManager.getHistory();

        // check
        Assertions.assertEquals(10, history.size(), "History is not empty");
        Assertions.assertEquals("Task 6", history.get(0).getName(), "First task should be Task 6");
        Assertions.assertEquals("Task 15", history.get(9).getName(), "Last task should be Task 15");
    }
}