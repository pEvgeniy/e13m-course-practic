package ru.mpei;

import ru.mpei.model.Epic;
import ru.mpei.model.Subtask;
import ru.mpei.model.Task;
import ru.mpei.model.enums.TaskStatus;
import ru.mpei.service.TaskServiceImpl;

import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        addTasksTest();
        deleteTasks();
        updateSubtaskTest();
    }

    private static Task createTask() {
        return new Task("Task name", "Task description", LocalDateTime.now(), 100L);
    }

    private static Epic createEpic() {
        return new Epic("Epic name", "Epic description", LocalDateTime.now(), 100L);
    }

    private static Subtask createSubtask(int epicId) {
        return new Subtask(epicId, "Subtask name", "Subtask description", LocalDateTime.now(), 50L);
    }

    private static List<Subtask> createThreeSubtasks(int epicId) {
        return List.of(createSubtask(epicId), createSubtask(epicId), createSubtask(epicId));
    }

    private static void addTasksTest() {
        System.out.println("-------------------------------------------------------");
        System.out.println("Add tasks test");

        TaskServiceImpl taskService = new TaskServiceImpl();

        Task createdTask = taskService.addTask(createTask());
        System.out.println("Get task:");
        System.out.println(taskService.getTask(createdTask.getId()));

        Epic createdEpic = taskService.addEpic(createEpic());

        List<Subtask> subtasks = createThreeSubtasks(createdEpic.getId());

        for (Subtask subtask : subtasks) {
            taskService.addSubtask(subtask);
        }

        System.out.println("Get epic:");
        System.out.println(taskService.getEpic(createdEpic.getId()));
        System.out.println("\n");
    }

    private static void deleteTasks() {
        System.out.println("-------------------------------------------------------");
        System.out.println("Delete tasks test");

        TaskServiceImpl taskService = new TaskServiceImpl();

        Task createdTask = taskService.addTask(createTask());
        Epic createdEpic = taskService.addEpic(createEpic());
        List<Subtask> subtasks = createThreeSubtasks(createdEpic.getId());
        for (Subtask subtask : subtasks) {
            taskService.addSubtask(subtask);
        }

        System.out.println("Before:");
        System.out.println("Tasks:");
        System.out.println(taskService.getAllTasks());
        System.out.println("Epics:");
        System.out.println(taskService.getAllEpics());
        System.out.println("Subtasks:");
        System.out.println(taskService.getAllSubtasks());
        System.out.println("\n");
        System.out.println("Delete task:");
        taskService.removeTask(createdTask.getId());
        System.out.println("Tasks:");
        System.out.println(taskService.getAllTasks());
        System.out.println("\n");
        System.out.println("Delete subtasks:");
        taskService.removeSubtask(8);
        taskService.removeSubtask(9);
        System.out.println("Epics:");
        System.out.println(taskService.getAllEpics());
        System.out.println("Subtasks:");
        System.out.println(taskService.getAllSubtasks());
        System.out.println("\n");
    }

    private static void updateSubtaskTest() {
        System.out.println("-------------------------------------------------------");
        System.out.println("Update subtasks test");

        TaskServiceImpl taskService = new TaskServiceImpl();

        Epic createdEpic = taskService.addEpic(createEpic());
        List<Subtask> subtasks = createThreeSubtasks(createdEpic.getId());
        for (Subtask subtask : subtasks) {
            taskService.addSubtask(subtask);
        }

        System.out.println("Epic status before:");
        System.out.println("Status = " + createdEpic.getTaskStatus());
        System.out.println(createdEpic);
        System.out.println("\n");

        Subtask newSubtask = createSubtask(createdEpic.getId());
        newSubtask.setId(createdEpic.getId() + 1);
        newSubtask.setTaskStatus(TaskStatus.IN_PROGRESS);
        taskService.updateSubtask(newSubtask);
        System.out.println("Epic status after:");
        System.out.println("Status = " + createdEpic.getTaskStatus());
        System.out.println(createdEpic);
        System.out.println("\n");

        newSubtask.setId(createdEpic.getId() + 1);
        newSubtask.setTaskStatus(TaskStatus.DONE);
        taskService.updateSubtask(newSubtask);
        System.out.println("Epic status after:");
        System.out.println("Status = " + createdEpic.getTaskStatus());
        System.out.println(createdEpic);
        System.out.println("\n");

        for (Subtask subtask : createdEpic.getSubtasks()) {
            subtask.setTaskStatus(TaskStatus.DONE);
            taskService.updateSubtask(subtask);
        }
        System.out.println("Epic status after:");
        System.out.println("Status = " + createdEpic.getTaskStatus());
        System.out.println(createdEpic);
        System.out.println("\n");
    }
}