package ru.roshchin.springcourse.project3.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.roshchin.springcourse.project3.dto.SensorDTO;
import ru.roshchin.springcourse.project3.models.Sensor;
import ru.roshchin.springcourse.project3.services.SensorService;
import ru.roshchin.springcourse.project3.util.sensorUtils.SensorDTORegistrationValidator;
import ru.roshchin.springcourse.project3.util.sensorUtils.SensorErrorResponse;
import ru.roshchin.springcourse.project3.util.sensorUtils.SensorIsAlreadyExistException;

import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorController {

    private final SensorDTORegistrationValidator sensorDTORegistrationValidator;
    private final ModelMapper modelMapper;

    private final SensorService sensorService;

    @Autowired
    public SensorController(SensorDTORegistrationValidator sensorDTORegistrationValidator, ModelMapper modelMapper, SensorService sensorService) {
        this.sensorDTORegistrationValidator = sensorDTORegistrationValidator;
        this.modelMapper = modelMapper;
        this.sensorService = sensorService;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> register (@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult) {

        sensorDTORegistrationValidator.validate(sensorDTO,bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            errors.forEach(e -> errorMsg.append(e.getField()).append(" - ").append(e.getDefaultMessage()).append("; "));
//            for (FieldError error : errors) {
//                errorMsg.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append("; ");
//            }
            throw new SensorIsAlreadyExistException(errorMsg.toString());
        }

        sensorService.registerSensor(convertToSensor(sensorDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorIsAlreadyExistException e){

        SensorErrorResponse response = new SensorErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }

    private SensorDTO convertToSensor(Sensor sensor) {
        return modelMapper.map(sensor, SensorDTO.class);
    }




}
