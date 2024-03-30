package ru.yandex.practicum.tasktracker.model;

public class Node {
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