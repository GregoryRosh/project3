package ru.roshchin.springcourse.project3.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class SensorDTO {


    @Size(min = 2, max = 30, message = "Name size should be between 2 and 30")
    @NotEmpty(message = "Name should not be empty")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
