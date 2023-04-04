package ru.roshchin.springcourse.project3.util.sensorUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.roshchin.springcourse.project3.dto.SensorDTO;
import ru.roshchin.springcourse.project3.services.SensorService;

@Component
public class SensorDTORegistrationValidator implements Validator {
    private final SensorService sensorService;

    @Autowired
    public SensorDTORegistrationValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return SensorDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SensorDTO sensorDTO = (SensorDTO) target;

        if (sensorService.findByName(sensorDTO.getName()).isPresent())
            errors.rejectValue("name","","Sensor with specified name is already exist");
    }
}
