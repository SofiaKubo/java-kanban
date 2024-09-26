package tracker.managers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tracker.models.Epic;
import tracker.models.Status;
import tracker.models.Subtask;
import tracker.models.Task;
import tracker.models.Type;

import java.util.List;

abstract class AbstractTaskManagerTest<T extends TaskManager> {
    protected T taskManager;
    protected HistoryManager historyManager;
    protected Task task;
    protected Epic epic;
    protected Subtask subtask;

    @BeforeEach
    void init() {
        taskManager = createTaskManager();
        historyManager = Managers.getDefaultHistory();
        task = new Task("Task one", "Do something", Status.NEW, Type.TASK);
        epic = new Epic("Epic one", "Do something");
    }

    protected abstract T createTaskManager();

    @Test
    void shouldAddNewTask() {
        // prepare
        Task expectedTask = new Task(1, "Task one", "Do something", Status.NEW, Type.TASK);

        // do
        Task actualTask = taskManager.addNewTask(task);

        // check
        Assertions.assertNotNull(actualTask);
        Assertions.assertNotNull(actualTask.getId());
        Assertions.assertEquals(expectedTask, actualTask);
    }

    @Test
    public void tasksWithGeneratedAndAssignedIdsDoNotConflict() {
        // prepare
        Task taskOne = new Task(1, "Task one", "Do something", Status.NEW, Type.TASK);
        Task taskTwo = new Task("Task two", "Do something else", Status.IN_PROGRESS, Type.TASK);

        // do
        taskManager.addNewTask(taskOne);
        taskManager.addNewTask(taskTwo);

        //check
        Assertions.assertNotEquals(taskOne.getId(), taskTwo.getId(), "Task ids should be unique");
    }

    @Test
    public void taskPermanenceWhenAddedToManager() {
        // prepare
        taskManager.addNewTask(task);

        // do
        Task actualTask = taskManager.getTaskById(1);

        // check
        Assertions.assertEquals(task.getId(), actualTask.getId());
        Assertions.assertEquals(task.getName(), actualTask.getName());
        Assertions.assertEquals(task.getStatus(), actualTask.getStatus());
    }

    @Test
    void shouldUpdateTask() {
        // prepare
        Task expectedTask = new Task(1, "Task one", "Do something", Status.IN_PROGRESS, Type.TASK);

        // do
        Task addedTask = taskManager.addNewTask(task);
        Task updatedTask = new Task(addedTask.getId(), "Task one", "Do something updated", Status.IN_PROGRESS, Type.TASK);
        taskManager.updateTask(updatedTask);

        Task actualTask = taskManager.getTaskById(addedTask.getId());

        // check
        Assertions.assertEquals(expectedTask, actualTask);
        Assertions.assertEquals(updatedTask.getStatus(), actualTask.getStatus());
    }

    @Test
    void shouldGetTaskById() {
        // prepare
        Task expectedTask = new Task(1, "Task one", "Do something", Status.NEW, Type.TASK);

        // do
        Task savedTask = taskManager.addNewTask(task);
        Task actualTask = taskManager.getTaskById(savedTask.getId());

        // check
        Assertions.assertEquals(expectedTask, actualTask);
    }

    @Test
    void shouldGetAllTasks() {
        // prepare
        Task taskOne = new Task("Task one", "Do something", Status.NEW, Type.TASK);
        Task taskTwo = new Task("Task two", "Do something else", Status.IN_PROGRESS, Type.TASK);
        Task taskThree = new Task("Task three", "Do another thing", Status.DONE, Type.TASK);

        // do
        taskManager.addNewTask(taskOne);
        taskManager.addNewTask(taskTwo);
        taskManager.addNewTask(taskThree);
        final List<Task> tasks = taskManager.getAllTasks();

        // check
        Assertions.assertNotNull(tasks, "List should not be null");
        Assertions.assertEquals(3, tasks.size());
    }

    @Test
    void shouldDeleteTaskById() {
        // prepare
        Task taskToDelete = taskManager.addNewTask(task);

        // do
        taskManager.deleteTaskById(taskToDelete.getId());
        Task deletedTask = taskManager.getTaskById(taskToDelete.getId());

        // check
        Assertions.assertNull(deletedTask);
    }

    @Test
    void shouldDeleteAllTasks() {
        // prepare
        Task taskOne = new Task("Task one", "Do something", Status.NEW, Type.TASK);
        Task taskTwo = new Task("Task two", "Do something else", Status.IN_PROGRESS, Type.TASK);
        Task taskThree = new Task("Task three", "Do another thing", Status.DONE, Type.TASK);

        taskManager.addNewTask(taskOne);
        taskManager.addNewTask(taskTwo);
        taskManager.addNewTask(taskThree);

        // do
        taskManager.deleteAllTasks();
        final List<Task> tasks = taskManager.getAllTasks();

        // check
        Assertions.assertNotNull(tasks, "List should not be null");
        Assertions.assertTrue(tasks.isEmpty(), "List should be empty");
    }

    @Test
    void shouldAddNewEpic() {
        // prepare
        Epic expectedEpic = new Epic(1, "Epic one", "Do something", Status.NEW);

        // do
        Epic actualEpic = taskManager.addNewEpic(epic);

        // check
        Assertions.assertNotNull(actualEpic);
        Assertions.assertNotNull(actualEpic.getId());
        Assertions.assertEquals(expectedEpic, actualEpic);
    }

    @Test
    public void epicsWithGeneratedAndAssignedIdsDoNotConflict() {
        // prepare
        Epic epicOne = new Epic(1, "Epic one", "Do something", Status.NEW);
        Epic epicTwo = new Epic("Epic two", "Do something else");

        // do
        taskManager.addNewEpic(epicOne);
        taskManager.addNewEpic(epicTwo);

        //check
        Assertions.assertNotEquals(epicOne.getId(), epicTwo.getId(), "Epic ids should be unique");
    }

    @Test
    public void epicPermanenceWhenAddedToManager() {
        // prepare
        taskManager.addNewEpic(epic);

        // do
        Epic actualEpic = taskManager.getEpicById(1);

        // check
        Assertions.assertEquals(epic.getId(), actualEpic.getId());
        Assertions.assertEquals(epic.getName(), actualEpic.getName());
        Assertions.assertEquals(epic.getStatus(), actualEpic.getStatus());
    }

    @Test
    void shouldUpdateEpic() {
        // prepare
        Epic expectedEpic = new Epic(1, "Epic one", "Do something else", Status.NEW);

        // do
        Epic addedEpic = taskManager.addNewEpic(epic);
        Epic updatedEpic = new Epic(1, "Epic one", "Do something else", Status.NEW);
        taskManager.updateEpic(updatedEpic);

        Epic actualEpic = taskManager.getEpicById(addedEpic.getId());

        // check
        Assertions.assertEquals(expectedEpic, actualEpic);
    }

    @Test
    void shouldGetEpicById() {
        // prepare
        Epic expectedEpic = new Epic(1, "Epic one", "Do something", Status.NEW);

        // do
        Epic savedEpic = taskManager.addNewEpic(epic);
        Epic actualEpic = taskManager.getEpicById(savedEpic.getId());

        // check
        Assertions.assertEquals(expectedEpic, actualEpic);
    }

    @Test
    void shouldGetAllEpics() {
        // prepare
        Epic epicOne = new Epic("Epic one", "Do something");
        Epic epicTwo = new Epic("Epic two", "Do something else");
        Epic epicThree = new Epic("Epic three", "Do another thing");

        taskManager.addNewEpic(epicOne);
        taskManager.addNewEpic(epicTwo);
        taskManager.addNewEpic(epicThree);

        // do
        final List<Epic> epics = taskManager.getAllEpics();

        // check
        Assertions.assertNotNull(epics, "List should not be null");
        Assertions.assertEquals(3, epics.size());
    }

    @Test
    void shouldDeleteEpicById() {
        // prepare
        Epic epicToDelete = taskManager.addNewEpic(epic);

        // do
        taskManager.deleteEpicById(epicToDelete.getId());
        Epic deletedEpic = taskManager.getEpicById(epicToDelete.getId());

        // check
        Assertions.assertNull(deletedEpic);
    }

    @Test
    void shouldDeleteAllEpics() {
        // prepare
        Epic epicOne = new Epic("Epic one", "Do something");
        Epic epicTwo = new Epic("Epic two", "Do something else");
        Epic epicThree = new Epic("Epic three", "Do another thing");

        taskManager.addNewEpic(epicOne);
        taskManager.addNewEpic(epicTwo);
        taskManager.addNewEpic(epicThree);

        // do
        taskManager.deleteAllEpics();
        final List<Epic> epics = taskManager.getAllEpics();

        // check
        Assertions.assertNotNull(epics, "List should not be null");
        Assertions.assertTrue(epics.isEmpty(), "List should be empty");
    }

    @Test
    void shouldAddNewSubtask() {
        // prepare
        taskManager.addNewEpic(epic);
        subtask = new Subtask(epic.getId(), "Subtask one", "Do something");
        Subtask expectedSubtask = new Subtask(epic.getId(), 2, "Subtask one", "Do something", Status.NEW);

        // do
        Subtask actualSubtask = taskManager.addNewSubtask(subtask);

        // check
        Assertions.assertNotNull(actualSubtask);
        Assertions.assertNotNull(actualSubtask.getId());
        Assertions.assertEquals(expectedSubtask, actualSubtask);
    }

    @Test
    public void subtasksWithGeneratedAndAssignedIdsDoNotConflict() {
        // prepare
        taskManager.addNewEpic(epic);
        Subtask subtaskOne = new Subtask(epic.getId(), 2, "Subtask one", "Do something", Status.NEW);
        Subtask subtaskTwo = new Subtask(epic.getId(), "Subtask two", "Do something else");

        // do
        taskManager.addNewSubtask(subtaskOne);
        taskManager.addNewSubtask(subtaskTwo);
        //check
        Assertions.assertNotEquals(subtaskOne.getId(), subtaskTwo.getId(), "Subtask ids should be unique");
    }

    @Test
    public void subtaskPermanenceWhenAddedToManager() {
        // prepare
        taskManager.addNewEpic(epic);
        Subtask subtask = new Subtask(epic.getId(), "Subtask one", "Do something else");
        taskManager.addNewSubtask(subtask);

        // do
        Subtask actualSubtask = taskManager.getSubtaskById(2);

        // check
        Assertions.assertEquals(subtask.getId(), actualSubtask.getId());
        Assertions.assertEquals(subtask.getName(), actualSubtask.getName());
        Assertions.assertEquals(subtask.getStatus(), actualSubtask.getStatus());
    }

    @Test
    void shouldUpdateSubtask() {
        // prepare
        taskManager.addNewEpic(epic);
        subtask = new Subtask(epic.getId(), "Subtask one", "Do something");
        Subtask expectedSubtask = new Subtask(epic.getId(), 2, "Subtask one", "Do something", Status.DONE);

        // do
        Subtask addedSubtask = taskManager.addNewSubtask(subtask);
        Subtask updatedSubtask = new Subtask(epic.getId(), 2, "Subtask one", "Do something", Status.DONE);
        taskManager.updateSubtask(updatedSubtask);
        Subtask actualSubtask = taskManager.getSubtaskById(updatedSubtask.getId());

        // check
        Assertions.assertEquals(expectedSubtask, actualSubtask);
    }

    @Test
    void shouldGetSubtasksOfEpic() {
        taskManager.addNewEpic(epic);
        Subtask subtaskOne = new Subtask(epic.getId(), "Subtask one", "Do something");
        Subtask subtaskTwo = new Subtask(epic.getId(), "Subtask two", "Do something else");
        Subtask subtaskThree = new Subtask(epic.getId(), "Subtask three", "Do another thing");

        taskManager.addNewSubtask(subtaskOne);
        taskManager.addNewSubtask(subtaskTwo);
        taskManager.addNewSubtask(subtaskThree);

        // do
        final List<Subtask> subtasks = taskManager.getSubtasksOfEpic(epic.getId());

        // check
        Assertions.assertNotNull(subtasks, "List should not be null");
        Assertions.assertEquals(3, subtasks.size());
    }

    @Test
    void shouldGetSubtaskById() {
        // prepare
        taskManager.addNewEpic(epic);
        subtask = new Subtask(epic.getId(), "Subtask one", "Do something");
        Subtask expectedSubtask = new Subtask(epic.getId(), 2, "Subtask one", "Do something", Status.NEW);

        // do
        Subtask savedSubtask = taskManager.addNewSubtask(subtask);
        Subtask actualSubtask = taskManager.getSubtaskById(savedSubtask.getId());

        // check
        Assertions.assertEquals(expectedSubtask, actualSubtask);
    }

    @Test
    void shouldGetAllSubtasks() {
        // prepare
        taskManager.addNewEpic(epic);
        Subtask subtaskOne = new Subtask(epic.getId(), "Subtask one", "Do something");
        Subtask subtaskTwo = new Subtask(epic.getId(), "Subtask two", "Do something else");
        Subtask subtaskThree = new Subtask(epic.getId(), "Subtask three", "Do another thing");

        taskManager.addNewSubtask(subtaskOne);
        taskManager.addNewSubtask(subtaskTwo);
        taskManager.addNewSubtask(subtaskThree);

        // do
        final List<Subtask> subtasks = taskManager.getAllSubtasks();

        // check
        Assertions.assertNotNull(subtasks, "List should not be null");
        Assertions.assertEquals(3, subtasks.size());
    }

    @Test
    void shouldDeleteSubtaskById() {
        // prepare
        taskManager.addNewEpic(epic);
        subtask = new Subtask(epic.getId(), "Subtask one", "Do something");
        Subtask subtaskToDelete = taskManager.addNewSubtask(subtask);

        // do
        taskManager.deleteSubtaskById(subtaskToDelete.getId());
        Subtask deletedSubtask = taskManager.getSubtaskById(subtaskToDelete.getId());

        // check
        Assertions.assertNull(deletedSubtask);
    }

    @Test
    void shouldDeleteAllSubtasks() {
        // prepare
        taskManager.addNewEpic(epic);
        Subtask subtaskOne = new Subtask(epic.getId(), "Subtask one", "Do something");
        Subtask subtaskTwo = new Subtask(epic.getId(), "Subtask two", "Do something else");
        Subtask subtaskThree = new Subtask(epic.getId(), "Subtask three", "Do another thing");

        taskManager.addNewSubtask(subtaskOne);
        taskManager.addNewSubtask(subtaskTwo);
        taskManager.addNewSubtask(subtaskThree);

        // do
        taskManager.deleteAllSubtasks();
        final List<Subtask> subtasks = taskManager.getAllSubtasks();

        // check
        Assertions.assertNotNull(subtasks, "List should not be null");
        Assertions.assertTrue(subtasks.isEmpty(), "List should be empty");
    }

    @Test
    void deletedSubtasksShouldNotKeepOldId() {
        // prepare
        taskManager.addNewEpic(epic);
        Subtask subtask = new Subtask(epic.getId(), "Subtask one", "Do something");
        Subtask addedSubtask = taskManager.addNewSubtask(subtask);

        // do
        int subtaskId = addedSubtask.getId();
        taskManager.deleteSubtaskById(subtaskId);
        Subtask deletedSubtask = taskManager.getSubtaskById(subtaskId);

        // check
        Assertions.assertNull(deletedSubtask, "Deleted subtask should not keep old ID");
    }

    @Test
    void epicShouldNotContainDeletedSubtaskId() {
        // prepare
        taskManager.addNewEpic(epic);
        Subtask subtaskOne = new Subtask(epic.getId(), "Subtask one", "Do something");
        Subtask subtaskTwo = new Subtask(epic.getId(), "Subtask two", "Do something else");

        Subtask addedSubtaskOne = taskManager.addNewSubtask(subtaskOne);
        Subtask addedSubtaskTwo = taskManager.addNewSubtask(subtaskTwo);

        // do
        int subtaskTwoId = addedSubtaskTwo.getId();
        taskManager.deleteSubtaskById(subtaskTwoId);
        Epic updatedEpic = taskManager.getEpicById(epic.getId());
        List<Integer> subtaskIds = updatedEpic.getSubtasksId();

        // check
        Assertions.assertFalse(subtaskIds.contains(subtaskTwoId), "Epic should not contain the ID of the deleted subtask");
    }

    @Test
    void shouldMatchWithChangesInTaskAfterUsingSetters() {
        // prepare
        Task addedTask = taskManager.addNewTask(task);

        // do
        addedTask.setName("New name");
        addedTask.setDescription("New description");
        addedTask.setStatus(Status.DONE);
        taskManager.updateTask(addedTask);
        Task updatedTask = taskManager.getTaskById(addedTask.getId());

        // check
        Assertions.assertEquals("New name", updatedTask.getName());
        Assertions.assertEquals("New description", updatedTask.getDescription());
        Assertions.assertEquals(Status.DONE, updatedTask.getStatus());
    }

    @Test
    void shouldMatchWithChangesInEpicAfterUsingSetters() {
        // prepare
        Epic addedEpic = taskManager.addNewEpic(epic);
        Subtask subtaskOne = new Subtask(epic.getId(), "Subtask one", "Do something");
        Subtask subtaskTwo = new Subtask(epic.getId(), "Subtask two", "Do something else");

        taskManager.addNewSubtask(subtaskOne);
        taskManager.addNewSubtask(subtaskTwo);

        // do
        addedEpic.setName("New name");
        addedEpic.setDescription("New description");
        taskManager.updateEpic(addedEpic);
        Epic updatedEpic = taskManager.getEpicById(addedEpic.getId());

        // check
        Assertions.assertEquals("New name", addedEpic.getName());
        Assertions.assertEquals("New description", addedEpic.getDescription());
        Assertions.assertEquals(2, updatedEpic.getSubtasksId().size());
        Assertions.assertTrue(updatedEpic.getSubtasksId()
                .contains(subtaskOne.getId()));
        Assertions.assertTrue(updatedEpic.getSubtasksId()
                .contains(subtaskTwo.getId()));
    }

    @Test
    void shouldMatchWithChangesInSubtaskAfterUsingSetters() {
        // prepare
        Epic epic = new Epic("Epic one", "Do something");
        Epic addedEpic = taskManager.addNewEpic(epic);
        Subtask subtask = new Subtask(addedEpic.getId(), "Subtask", "Do something", Status.NEW);

        Subtask addedSubtask = taskManager.addNewSubtask(subtask);

        // do
        addedSubtask.setName("New name");
        addedSubtask.setDescription("New description");
        addedSubtask.setStatus(Status.DONE);
        taskManager.updateSubtask(addedSubtask);
        Subtask updatedSubtask = taskManager.getSubtaskById(addedSubtask.getId());

        // check
        Assertions.assertEquals("New name", updatedSubtask.getName());
        Assertions.assertEquals("New description", updatedSubtask.getDescription());
        Assertions.assertEquals(Status.DONE, updatedSubtask.getStatus());
    }
}
