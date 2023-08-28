package ru.mpei.model;

import java.time.LocalDateTime;

public class Subtask extends Task {
    private final int epicId;

    public Subtask(int epicId, String name, String description, LocalDateTime startTime, long duration) {
        super(name, description, startTime, duration);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "id=" + id +
                ", epicId=" + epicId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", taskStatus=" + taskStatus +
                ", startTime=" + startTime +
                ", duration=" + duration +
                '}';
    }
}
