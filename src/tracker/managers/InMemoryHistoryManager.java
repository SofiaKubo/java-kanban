package tracker.managers;

import tracker.models.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    private final Map<Integer, Node<Task>> historyMap = new HashMap<>();
    private Node<Task> head;
    private Node<Task> tail;
    private int size = 0;

    public class Node<T> {
        public T data;
        public Node<T> next;
        public Node<T> prev;

        public Node(Node<T> prev, T data, Node<T> next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }

    private void linkLast(Task element) {
        final Node<Task> oldTail = tail;
        final Node<Task> newNode = new Node<>(oldTail, element, null);
        tail = newNode;
        if (oldTail == null)
            head = newNode;
        else
            oldTail.next = newNode;
        size++;
    }

    private void removeNode(Node<Task> currentNode) {
        if (currentNode == head) {
            head = currentNode.next;
            if (head != null) {
                head.prev = null;
            } else {
                tail = null;
            }
        } else if (currentNode == tail) {
            tail = currentNode.prev;
            if (tail != null) {
                tail.next = null;
            } else {
                head = null;
            }
        } else {
            currentNode.prev.next = currentNode.next;
            currentNode.next.prev = currentNode.prev;
        }
        historyMap.remove(currentNode.data.getId());
        size--;
    }

    private List<Task> getTasks() {
        List<Task> history = new ArrayList<>();
        Node<Task> currentNode = head;
        while (currentNode != null) {
            history.add(currentNode.data);
            currentNode = currentNode.next;
        }
        return history;
    }

    @Override
    public void add(Task task) {
        if (historyMap.containsKey(task.getId())) {
            Node<Task> currentNode = historyMap.get(task.getId());
            removeNode(currentNode);
        }
        linkLast(task);
        Node<Task> newNode = tail;
        historyMap.put(task.getId(), newNode);
    }

    @Override
    public void remove(int id) {
        if (historyMap.containsKey(id)) {
            Node<Task> currentNode = historyMap.get(id);
            removeNode(currentNode);
        }
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }
}


