package com.liro.treatments.controllers;

import com.liro.treatments.dto.ApiResponse;
import com.liro.treatments.dto.TreatmentDTO;
import com.liro.treatments.dto.responses.TreatmentResponse;
import com.liro.treatments.service.TreatmentService;
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
@RequestMapping("/treatments")
public class TreatmentController {

    private final TreatmentService treatmentService;

    @Autowired
    public TreatmentController(TreatmentService treatmentService) {
        this.treatmentService = treatmentService;
    }

    @GetMapping(value = "/{treatmentId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TreatmentResponse> getTreatment(@PathVariable("treatmentId") Long treatmentId) {
        return ResponseEntity.ok(treatmentService.findById(treatmentId));
    }

    @GetMapping(value = "/getAll", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<TreatmentResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(treatmentService.findAll(pageable));
    }

    @GetMapping(value = "/getByNameContaining", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<TreatmentResponse>> getByNameContaining(
            @RequestParam("nameContaining") String nameContaining, Pageable pageable) {
        return ResponseEntity.ok(treatmentService.findAllByNameContaining(nameContaining, pageable));
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> createTreatment(@Valid @RequestBody TreatmentDTO treatmentDto) {
        TreatmentResponse treatmentResponse = treatmentService.createTreatment(treatmentDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/treatments/{treatmentId}")
                .buildAndExpand(treatmentResponse.getId()).toUri();

        return ResponseEntity.created(location).body(
                new ApiResponse(true, "Treatment created successfully"));
    }

}
