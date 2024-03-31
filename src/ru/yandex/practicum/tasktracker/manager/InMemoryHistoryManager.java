package ru.yandex.practicum.tasktracker.manager;

import java.util.*;

import ru.yandex.practicum.tasktracker.model.Task;
import ru.yandex.practicum.tasktracker.model.Node;

public class InMemoryHistoryManager implements HistoryManager {
    public Node firstNode;
    public Node lastNode;
    private final List<Task> history = new ArrayList<>();
    private final Map<Integer, Node> historyMap = new HashMap<>();

    @Override
    public void add(Task task) {
        Node node = historyMap.remove(task.getId());
        removeNode(node);
        linkLast(task);
    }

    @Override
    public List<Task> getHistory() {
        return history;
    }

    @Override
    public void remove(int id) {
        Node node = historyMap.remove(id);
        removeNode(node);
    }

    public void removeNode(Node node) {
        if (node == null) {
            return;
        }
        if (node.getPreviousNode() == null && node.getNextNode() == null) {
            linkToNulls();
        } else if (node.getPreviousNode() == null) {
            linkAsFirst(node);
        } else if (node.getNextNode() == null) {
            linkAsLast(node);
        } else linkBetween(node.getPreviousNode(), node.getNextNode());
    }

    public void linkLast(Task task) {
        Node node = new Node(task, lastNode, null);
        if (firstNode == null) {
            firstNode = node;
        } else {
            lastNode.setNextNode(node);
        }
        lastNode = node;
        historyMap.put(task.getId(), node);
    }

    private ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>(historyMap.size());
        Node current = firstNode;
        while (current != null) {
            tasks.add(current.getTask());
            current = current.getNextNode();
        }
        return tasks;
    }

    private void linkToNulls() {
        firstNode = null;
        lastNode = null;
    }

    private void linkAsLast(Node node) {
        Node preLastNode = node.getPreviousNode();
        preLastNode.setNextNode(null);
        lastNode = preLastNode;
    }

    private void linkAsFirst(Node node) {
        Node secondNode = node.getNextNode();
        secondNode.setPreviousNode(null);
        firstNode = secondNode;
    }

    private void linkBetween(Node prevNode, Node nextNode) {
        prevNode.setNextNode(nextNode);
        nextNode.setPreviousNode(prevNode);
    }
}
