package ru.yandex.practicum.tasktracker.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import ru.yandex.practicum.tasktracker.model.Task;

public class InMemoryHistoryManager implements HistoryManager {
    public Node firstNode;
    public Node lastNode;
    private final Map<Integer, Node> historyMap = new HashMap<>();

    @Override
    public void add(Task task) {
        Node node = historyMap.remove(task.getId());
        removeNode(node);
        linkLast(task);
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
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

    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>(historyMap.size());
        Node currentNode = firstNode;
        while (currentNode != null) {
            tasks.add(currentNode.getTask());
            currentNode = currentNode.getNextNode();
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

    private static class Node {
        private Task task;
        private Node previousNode;
        private Node nextNode;

        public Node(Task task, Node previousNode, Node nextNode) {
            this.task = task;
            this.previousNode = previousNode;
            this.nextNode = nextNode;
        }

        public Task getTask() {
            return task;
        }

        public void setTask(Task task) {
            this.task = task;
        }

        public Node getPreviousNode() {
            return previousNode;
        }

        public void setPreviousNode(Node previousNode) {
            this.previousNode = previousNode;
        }

        public Node getNextNode() {
            return nextNode;
        }

        public void setNextNode(Node nextNode) {
            this.nextNode = nextNode;
        }
    }
}
