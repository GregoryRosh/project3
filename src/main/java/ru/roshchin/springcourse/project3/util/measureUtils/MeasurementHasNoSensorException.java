package ru.roshchin.springcourse.project3.util.measureUtils;

public class MeasurementHasNoSensorException extends RuntimeException{
    public MeasurementHasNoSensorException(String message) {
        super(message);
    }
}
