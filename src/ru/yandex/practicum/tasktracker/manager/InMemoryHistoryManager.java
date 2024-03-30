package ru.yandex.practicum.tasktracker.manager;

import java.util.*;

import ru.yandex.practicum.tasktracker.model.Task;
import ru.yandex.practicum.tasktracker.model.Node;

public class InMemoryHistoryManager implements HistoryManager {
    public Node firstNode;
    public Node lastNode;
    private final List<Task> history = new ArrayList<>();
    private final Map<Integer, Node> historyMap = new HashMap<>(); //id задачи и узлы списка LinkedHashMapWithNodes

    @Override
    public void add(Task task) {
      //  Node node = new Node(task, );
        // getNode по таку и положить сюда
        // getNode написать в классе Node
        removeNode(task); //удалить задачу если она есть
        linkLast(task); //добавить в конец списка
    }

    @Override
    public List<Task> getHistory() {
        return history;
    }

    @Override
    public void remove(int id) {
        //для удаления задачи из просмотра
        //Добавьте вызов метода при удалении задач, чтобы они удалялись также из истории просмотров.
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
        //lastNode.setTask(task);
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
//собирать все задачи из списка в обычный ArrayList history
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
