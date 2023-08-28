package ru.mpei;

import ru.mpei.model.Task;
import ru.mpei.service.TaskService;
import ru.mpei.service.TaskServiceImpl;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Menu {
    private static final Scanner scanner = new Scanner(System.in);
    private static final TaskService taskService = new TaskServiceImpl();

    public static void main(String[] args) {
        boolean isRunnable = true;
        int command;

        while (isRunnable) {
            System.out.println("Выберите команду: \n" +
                    "1. Добавить задачу\n" +
                    "2. Удалить задачу\n" +
                    "3. Найти задачу\n" +
                    "4. Обновить задачу\n" +
                    "5. Выйти\n");
            command = scanner.nextInt();
            if (command == 1) {
                createTaskInMenu();
            } else if (command == 5) {
                System.out.println("Выходим...");
                isRunnable = false;
            }
        }
    }

    private static void createTaskInMenu() {
        System.out.println("Введите имя");
        String name = scanner.next();
        System.out.println("Введите описание");
        String description = scanner.next();
        System.out.println("Введите длительность");
        long duration = scanner.nextLong();
        Task createdTask = new Task(name, description, LocalDateTime.now(), duration);
        System.out.println(taskService.addTask(createdTask).getId());
    }
}
