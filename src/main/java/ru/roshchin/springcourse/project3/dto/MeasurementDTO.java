package ru.roshchin.springcourse.project3.dto;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

public class MeasurementDTO {


    @NotNull(message = "Value should not be empty")
    @Range( min = -100, max = 100, message = "Temperature should be between -100 and 100")
    private Integer value;

    @NotNull(message = "IsRainy should not be empty")
    private boolean isRainy;


    private SensorDTO sensor; //TODO

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public boolean isRainy() {
        return isRainy;
    }

    public void setRainy(boolean rainy) {
        isRainy = rainy;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }
}
