package com.liro.treatments.controllers;

import com.liro.treatments.dto.ApiResponse;
import com.liro.treatments.dto.TreatmentTypeDTO;
import com.liro.treatments.dto.responses.TreatmentTypeResponse;
import com.liro.treatments.service.TreatmentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/treatmentTypes")
public class TreatmentTypeController {

    private final TreatmentTypeService treatmentTypeService;

    @Autowired
    public TreatmentTypeController(TreatmentTypeService treatmentTypeService) {
        this.treatmentTypeService = treatmentTypeService;
    }

    @GetMapping(value = "/{treatmentTypeId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TreatmentTypeResponse> getTreatmentType(@PathVariable("treatmentTypeId") Long treatmentTypeId) {
        return ResponseEntity.ok(treatmentTypeService.findById(treatmentTypeId));
    }

    @GetMapping(value = "/getAll", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<TreatmentTypeResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(treatmentTypeService.findAll(pageable));
    }

    @GetMapping(value = "/getByNameContaining", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<TreatmentTypeResponse>> getByNameContaining(
            @RequestParam("nameContaining") String nameContaining, Pageable pageable) {
        return ResponseEntity.ok(treatmentTypeService.findAllByNameContaining(nameContaining, pageable));
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> createTreatmentType(@Valid @RequestBody TreatmentTypeDTO treatmentTypeDto) {
        TreatmentTypeResponse treatmentTypeResponse = treatmentTypeService.createTreatmentType(treatmentTypeDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/treatmentTypes/{treatmentTypeId}")
                .buildAndExpand(treatmentTypeResponse.getId()).toUri();

        return ResponseEntity.created(location).body(
                new ApiResponse(true, "TreatmentType created successfully"));
    }
}