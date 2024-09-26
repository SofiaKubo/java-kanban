package tracker.managers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import tracker.models.Epic;
import tracker.models.Status;
import tracker.models.Subtask;
import tracker.models.Task;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tracker.managers.FileBackedTaskManager.loadFromFile;

class FileBackedTaskManagerTest extends AbstractTaskManagerTest<FileBackedTaskManager> {
    private File tempFile;

    @Override
    protected FileBackedTaskManager createTaskManager() {
        try {
            tempFile = File.createTempFile("test", ".csv");
            if (tempFile == null) {
                throw new IllegalStateException("Temporary file wasn't created.");
            }
            System.out.println("Task manager created with file: " +
                    tempFile.getAbsolutePath());
        } catch (IOException e) {
            throw new IllegalStateException("Error creating temporary file.", e);
        }
        return Managers.getFileBackedTaskManager(tempFile);
    }

    @AfterEach
    void tearDown() {
        if (tempFile != null && tempFile.exists()) {
            tempFile.delete();
        }
    }

    @Test
    void saveAndLoadEmptyFile() {
        // prepare
        FileBackedTaskManager managerEmpty = createTaskManager();

        // do
        managerEmpty.save();
        FileBackedTaskManager loadedManager = Managers.getFileBackedTaskManager(tempFile);

        // check
        assertTrue(loadedManager.getAllTasks()
                .isEmpty(), "Loaded manager must be empty");
        assertTrue(loadedManager.getAllEpics()
                .isEmpty(), "Loaded manager must be empty");
        assertTrue(loadedManager.getAllSubtasks()
                .isEmpty(), "Loaded manager must be empty");
    }

    @Test
    void shouldSaveAndLoadMultipleTasks() {
        // prepare
        FileBackedTaskManager manager = createTaskManager();
        Task taskOne = new Task("Task one", "Do something");
        manager.addNewTask(taskOne);
        Task taskTwo = new Task("Task two", "Do something else");
        manager.addNewTask(taskTwo);
        Epic epicOne = new Epic("Epic one", "Do something");
        manager.addNewEpic(epicOne);
        Subtask subtaskOne = new Subtask(epicOne.getId(), "Subtask one", "Do something", Status.NEW);
        manager.addNewSubtask(subtaskOne);

        // do
        manager.save();
        TaskManager loadedManager = loadFromFile(tempFile);

        List<Task> loadedTasks = loadedManager.getAllTasks();
        List<Epic> loadedEpics = loadedManager.getAllEpics();
        List<Subtask> loadedSubTasks = loadedManager.getAllSubtasks();

        // check
        assertEquals(2, loadedTasks.size(), "Must be 2 tasks");
        assertEquals(1, loadedEpics.size(), "Must be 1 epic");
        assertEquals(1, loadedSubTasks.size(), "Must be 1 subtask");

        assertTrue(loadedTasks.contains(taskOne), "Task one must be loaded");
        assertTrue(loadedTasks.contains(taskTwo), "Task two must be loaded");
        assertTrue(loadedEpics.contains(epicOne), "Epic one must be loaded");
        assertTrue(loadedSubTasks.contains(subtaskOne), "Subtask one must be loaded");
    }

    @Test
    void shouldDeleteTasksCorrectly() {
        // prepare
        FileBackedTaskManager manager = createTaskManager();
        Task task = new Task("Task one", "Do something");
        manager.addNewTask(task);

        // do
        manager.deleteTaskById(task.getId());
        manager.save();

        // check
        TaskManager loadedManager = loadFromFile(tempFile);
        assertTrue(loadedManager.getAllTasks()
                .isEmpty(), "All tasks should be deleted");
    }
}





