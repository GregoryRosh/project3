package ru.roshchin.springcourse.project3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.roshchin.springcourse.project3.models.Sensor;
import ru.roshchin.springcourse.project3.repositories.SensorRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorService {
    private final SensorRepository sensorRepository;


    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }



    public Optional<Sensor> findByName(String name){
        return sensorRepository.findByName(name);
    }

    @Transactional
    public void registerSensor(Sensor sensor){
        sensor.setCreatedAt(LocalDateTime.now());
        //TODO добавь новый пустой лист для измерений если не заработает
        sensorRepository.save(sensor);
    }

}
