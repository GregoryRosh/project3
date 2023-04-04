package ru.roshchin.springcourse.project3.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;

@Entity
@Table(name = "Measurement")
public class Measurement {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "value")
    @NotNull(message = "Value should not be empty")
    @Range( min = -100, max = 100, message = "Temperature should be between -100 and 100")
    private Integer value;

    @Column(name = "is_rainy")
    @NotNull(message = "IsRainy should not be empty")
    private boolean isRainy;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @ManyToOne
    @JoinColumn(name = "sensor_id",referencedColumnName = "id")
    private Sensor sensor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public LocalDateTime getCreated_at() {
        return created_at;
    }


    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
