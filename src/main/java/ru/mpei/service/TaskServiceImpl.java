package ru.mpei.service;

import ru.mpei.exception.EntityNotFoundException;
import ru.mpei.model.Epic;
import ru.mpei.model.Subtask;
import ru.mpei.model.Task;
import ru.mpei.model.enums.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskServiceImpl implements TaskService {
    protected static int id = 1;
    protected final Map<Integer, Task> tasks = new HashMap<>();
    protected final Map<Integer, Epic> epics = new HashMap<>();
    protected final Map<Integer, Subtask> subtasks = new HashMap<>();

    @Override
    public Task addTask(Task task) {
        if (task == null) {
            return null;
        }
        task.setId(id);
        tasks.put(id++, task);
        return task;
    }

    @Override
    public Epic addEpic(Epic epic) {
        if (epic == null) {
            return null;
        }
        epic.setId(id);
        epics.put(id++, epic);
        return epic;
    }

    @Override
    public Subtask addSubtask(Subtask subtask) {
        if (subtask == null) {
            return null;
        }
        subtask.setId(id);
        subtasks.put(id++, subtask);
        epics.get(subtask.getEpicId()).getSubtasks().add(subtask);
        updateEpicStatus(epics.get(subtask.getEpicId()));
        return subtask;
    }

    @Override
    public Task getTask(int id) {
        if (!tasks.containsKey(id)) {
            throw new EntityNotFoundException("Task with id = " + id + " not found");
        }
        return tasks.get(id);
    }

    @Override
    public Epic getEpic(int id) {
        if (!epics.containsKey(id)) {
            throw new EntityNotFoundException("Epic with id = \" + id + \" not found");
        }
        return epics.get(id);
    }

    @Override
    public Subtask getSubtask(int id) {
        if (!subtasks.containsKey(id)) {
            throw new EntityNotFoundException("Subtask with id = " + id + " not found");
        }
        return subtasks.get(id);
    }

    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public Task removeTask(int id) {
        if (!tasks.containsKey(id)) {
            throw new EntityNotFoundException("Task with id = " + id + " not found");
        }
        return tasks.remove(id);
    }

    @Override
    public Epic removeEpic(int id) {
        if (!epics.containsKey(id)) {
            throw new EntityNotFoundException("Epic with id = " + id + " not found");
        }
        for (Subtask subtask : epics.get(id).getSubtasks()) {
            removeSubtask(subtask.getId());
        }
        return epics.remove(id);
    }

    @Override
    public Subtask removeSubtask(int id) {
        if (!subtasks.containsKey(id)) {
            throw new EntityNotFoundException("Subtask with id = " + id + " not found");
        }
        Subtask subtask = subtasks.get(id);
        subtasks.remove(id);
        epics.get(subtask.getEpicId()).getSubtasks().remove(subtask);
        updateEpicStatus(epics.get(subtask.getEpicId()));
        return subtask;
    }

    @Override
    public Task updateTask(Task task) {
        if (!tasks.containsKey(task.getId())) {
            throw new EntityNotFoundException("Task with id = " + task.getId() + " not found");
        }
        tasks.put(task.getId(), task);
        return null;
    }

    @Override
    public Subtask updateSubtask(Subtask subtask) {
        if (!subtasks.containsKey(subtask.getId())) {
            throw new EntityNotFoundException("Subtask with id = " + subtask.getId() + " not found");
        }
        if (!epics.containsKey(subtask.getEpicId())) {
            throw new EntityNotFoundException("Epic with id = " + subtask.getEpicId() + " not found");
        }
        subtasks.put(subtask.getId(), subtask);
        List<Subtask> epicSubtasks = epics.get(subtask.getEpicId()).getSubtasks();
        for (int i = 0; i < epicSubtasks.size(); i++) {
            if (epicSubtasks.get(i).getId() == subtask.getId()) {
                epicSubtasks.set(i, subtask);
                break;
            }
        }
        updateEpicStatus(epics.get(subtask.getEpicId()));
        return subtask;
    }

    private void updateEpicStatus(Epic epic) {
        List<Subtask> epicSubtasks = epic.getSubtasks();
        if (epicSubtasks.isEmpty()) {
            epic.setTaskStatus(TaskStatus.NEW);
            return;
        }
        TaskStatus taskStatus = null;
        for (Subtask epicSubtask : epicSubtasks) {
            if (taskStatus == null) {
                taskStatus = epicSubtask.getTaskStatus();
                continue;
            }

            if (taskStatus.equals(epicSubtask.getTaskStatus()) && !taskStatus.equals(TaskStatus.IN_PROGRESS)) {
                continue;
            }
            epic.setTaskStatus(TaskStatus.IN_PROGRESS);
            return;
        }
        epic.setTaskStatus(taskStatus);
    }
}