package ru.roshchin.springcourse.project3.util.sensorUtils;

public class SensorIsAlreadyExistException extends RuntimeException {
    public SensorIsAlreadyExistException(String message) {
        super(message);
    }
}
