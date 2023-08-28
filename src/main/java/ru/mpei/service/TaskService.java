package ru.mpei.service;

import ru.mpei.model.Epic;
import ru.mpei.model.Subtask;
import ru.mpei.model.Task;

import java.util.List;

public interface TaskService {
    /**
     * Adds task into HashMap
     * @param task to be added
     * @return added Task
     */
    Task addTask(Task task);

    /**
     * Adds epic into HashMap
     * @param epic to be added
     * @return added Epic
     */
    Epic addEpic(Epic epic);

    /**
     * Adds subtask into HashMap
     * @param subtask to be added
     * @return added Subtask
     */
    Subtask addSubtask(Subtask subtask);

    /**
     * Gets task from HashMap
     * @param id of task
     * @return task with id = id
     */
    Task getTask(int id);

    /**
     * Gets epic from HashMap
     * @param id of epic
     * @return epic with id = id
     */
    Epic getEpic(int id);

    /**
     * Gets subtask from HashMap
     * @param id of subtask
     * @return subtask with id = id
     */
    Subtask getSubtask(int id);

    /**
     * Gets all tasks
     * @return all tasks
     */
    List<Task> getAllTasks();

    /**
     * Gets all epics
     * @return all tasks
     */
    List<Epic> getAllEpics();

    /**
     * Gets all subtasks
     * @return all tasks
     */
    List<Subtask> getAllSubtasks();

    /**
     * Removes task from HashMap
     * @param id of task
     * @return removed Task
     */
    Task removeTask(int id);

    /**
     * Removes epic and it's subtasks
     * @param id of epic
     * @return removed Epic
     */
    Epic removeEpic(int id);

    /**
     * Removes subtasks from epic
     * !!!after removing need to update Epic status
     * @param id of subtask
     * @return removed Subtask
     */
    Subtask removeSubtask(int id);

    /**
     * Updates task in HashMap
     * @param task to be updated
     * @return updated task
     */
    Task updateTask(Task task);

    /**
     * Updates subtask in HashMap
     * !!!after updating need to update Epic status
     * @param subtask to be updated
     * @return updated subtask
     */
    Subtask updateSubtask(Subtask subtask);
}
