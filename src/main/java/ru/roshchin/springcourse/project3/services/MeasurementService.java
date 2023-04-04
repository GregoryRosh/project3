package ru.roshchin.springcourse.project3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.roshchin.springcourse.project3.models.Measurement;
import ru.roshchin.springcourse.project3.repositories.MeasurementRepository;
import ru.roshchin.springcourse.project3.repositories.SensorRepository;
import ru.roshchin.springcourse.project3.util.measureUtils.MeasurementHasNoSensorException;

import java.time.LocalDateTime;
import java.util.List;


@Service
@Transactional(readOnly = true)
public class MeasurementService {
    private final MeasurementRepository measurementRepository;

    private final SensorRepository sensorRepository;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, SensorRepository sensorRepository) {
        this.measurementRepository = measurementRepository;
        this.sensorRepository = sensorRepository;
    }

    public List<Measurement> findAll(){
        return measurementRepository.findAll();
    }

    public int rainyDays(){
        return measurementRepository.countByIsRainy(true);
    }

    @Transactional
    public void saveMeasurement(Measurement measurement){

        measurement.setCreated_at(LocalDateTime.now());
        measurement.setSensor(sensorRepository.findByName(measurement.getSensor().getName())
                .orElseThrow(() -> new MeasurementHasNoSensorException("Sensor with specified name is not exist")));
        //TODO
        measurementRepository.save(measurement);
    }


}
