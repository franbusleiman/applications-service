package com.liro.treatments.controllers;

import com.liro.treatments.dto.ApiResponse;
import com.liro.treatments.dto.TreatmentRecordDTO;
import com.liro.treatments.dto.responses.TreatmentRecordResponse;
import com.liro.treatments.service.TreatmentRecordService;
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
@RequestMapping("/treatmentRecords")
public class TreatmentRecordController {

    private final TreatmentRecordService treatmentRecordService;

    @Autowired
    public TreatmentRecordController(TreatmentRecordService treatmentRecordService) {
        this.treatmentRecordService = treatmentRecordService;
    }

    @GetMapping(value = "/{treatmentRecordId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TreatmentRecordResponse> getTreatmentRecord(@PathVariable("treatmentRecordId") Long treatmentRecordId,
                                                                      String token) {
        return ResponseEntity.ok(treatmentRecordService.findById(treatmentRecordId, token));
    }

    @GetMapping(value = "/getAll", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<TreatmentRecordResponse>> getAllByAnimalId(@RequestParam("animalId") Long animalId,
                                                                          String token,
                                                                          Pageable pageable) {
        return ResponseEntity.ok(treatmentRecordService.findAllByAnimalId(animalId, pageable, token));
    }


    @GetMapping(value = "/getAllByAnimalIdAndTreatmentId", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<TreatmentRecordResponse>> getAllByAnimalIdAndTreatmentId(@RequestParam("animalId") Long animalId,
                                                                                        @RequestParam("treatmentId") Long treatmentId,
                                                                                        String token,
                                                                                        Pageable pageable) {
        return ResponseEntity.ok(treatmentRecordService
                .findAllByAnimalIdAndTreatmentId(animalId, treatmentId, pageable, token));
    }

    @GetMapping(value = "/getAllByAnimalIdAndTreatmentTypeId", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<TreatmentRecordResponse>> getAllByAnimalIdAndTreatmentTypeId(@RequestParam("animalId") Long animalId,
                                                                                            @RequestParam("treatmentTypeId") Long treatmentTypeId,
                                                                                            String token,
                                                                                            Pageable pageable) {
        return ResponseEntity.ok(treatmentRecordService
                .findAllByAnimalIdAndTreatmentTypeId(animalId, treatmentTypeId, pageable, token));
    }

    @GetMapping(value = "/getLastByAnimalIdAndTreatmentId", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TreatmentRecordResponse> getLastByAnimalIdAndTreatmentId(@RequestParam("animalId") Long animalId,
                                                                                   @RequestParam("treatmentId") Long treatmentId,
                                                                                   String token) {
        return ResponseEntity.ok(treatmentRecordService.findLastByAnimalIdAndTreatmentId(animalId, treatmentId, token));
    }

    @GetMapping(value = "/getLastByAnimalIdAndTreatmentTypeId", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TreatmentRecordResponse> getLastByAnimalIdAndTreatmentTypeId(@RequestParam("animalId") Long animalId,
                                                                                       @RequestParam("treatmentId") Long treatmentTypeId,
                                                                                       String token) {
        return ResponseEntity.ok(treatmentRecordService.findLastByAnimalIdAndTreatmentTypeId(animalId, treatmentTypeId, token));
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> createTreatmentRecord(@Valid @RequestBody TreatmentRecordDTO treatmentRecordDto,
                                                             @RequestHeader(name = "Authorization") String token) {
        TreatmentRecordResponse treatmentRecordResponse = treatmentRecordService.createTreatmentRecord(treatmentRecordDto, token);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/treatmentRecords/{treatmentRecordId}")
                .buildAndExpand(treatmentRecordResponse.getId()).toUri();

        return ResponseEntity.created(location).body(
                new ApiResponse(true, "TreatmentRecord created successfully"));
    }



    @DeleteMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> deleteTreatmentRecord(@RequestParam("treatmentRecordId") Long treatmentRecordId,
                                                      String token) {
        treatmentRecordService.deleteTreatmentRecord(treatmentRecordId, token);

        return ResponseEntity.ok().build();
    }
}