package tracker.managers;

class InMemoryTaskManagerTest extends AbstractTaskManagerTest<InMemoryTaskManager> {
    @Override
    protected InMemoryTaskManager createTaskManager() {
        return Managers.getInMemoryTaskManager();
    }
}
