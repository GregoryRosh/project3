package ru.roshchin.springcourse.project3.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.roshchin.springcourse.project3.dto.MeasurementDTO;
import ru.roshchin.springcourse.project3.models.Measurement;
import ru.roshchin.springcourse.project3.services.MeasurementService;
import ru.roshchin.springcourse.project3.util.measureUtils.MeasureErrorResponse;
import ru.roshchin.springcourse.project3.util.measureUtils.MeasurementHasNoSensorException;
import ru.roshchin.springcourse.project3.util.measureUtils.MeasurementValidator;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {
    private final MeasurementService measurementService;
    private final ModelMapper modelMapper;

    private final MeasurementValidator measurementValidator;

    @Autowired
    public MeasurementController(MeasurementService measurementService, ModelMapper modelMapper, MeasurementValidator measurementValidator) {
        this.measurementService = measurementService;
        this.modelMapper = modelMapper;
        this.measurementValidator = measurementValidator;
    }

    @GetMapping
    public List<MeasurementDTO> showMeasurements(){
        return measurementService.findAll().stream().map(this::convertToMeasurementDTO).collect(Collectors.toList());
    }

    @GetMapping("/rainyDaysCount")
    public Map<String,Integer> rainyDaysCount(){

        return Collections.singletonMap("rainyDaysCount", measurementService.rainyDays());
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasure(@RequestBody @Valid MeasurementDTO measurementDTO, BindingResult bindingResult){
        measurementValidator.validate(measurementDTO,bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            errors.forEach(e -> errorMsg.append(e.getField()).append(" - ").append(e.getDefaultMessage()).append("; "));

            throw new MeasurementHasNoSensorException(errorMsg.toString());
        }

        measurementService.saveMeasurement(convertToMeasurement(measurementDTO));//TODO
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<MeasureErrorResponse> handleException(MeasurementHasNoSensorException e){

        MeasureErrorResponse response = new MeasureErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO) {
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return modelMapper.map(measurement,MeasurementDTO.class);
    }




}
