package com.liro.applications.controllers;

import com.liro.applications.dto.ApiResponse;
import com.liro.applications.dto.ApplicationRecordDTO;
import com.liro.applications.dto.responses.ApplicationRecordResponse;
import com.liro.applications.service.ApplicationRecordService;
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
@RequestMapping("/applicationRecords")
public class ApplicationRecordController {

    private final ApplicationRecordService applicationRecordService;

    @Autowired
    public ApplicationRecordController(ApplicationRecordService applicationRecordService) {
        this.applicationRecordService = applicationRecordService;
    }

    @GetMapping(value = "/{applicationRecordId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApplicationRecordResponse> getApplicationRecord(@PathVariable("applicationRecordId") Long applicationRecordId,
                                                                      String token) {
        return ResponseEntity.ok(applicationRecordService.findById(applicationRecordId, token));
    }

    @GetMapping(value = "/getAll", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<ApplicationRecordResponse>> getAllByAnimalId(@RequestParam("animalId") Long animalId,
                                                                          String token,
                                                                          Pageable pageable) {
        return ResponseEntity.ok(applicationRecordService.findAllByAnimalId(animalId, pageable, token));
    }


    @GetMapping(value = "/getAllByAnimalIdAndApplicationId", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<ApplicationRecordResponse>> getAllByAnimalIdAndApplicationId(@RequestParam("animalId") Long animalId,
                                                                                        @RequestParam("applicationId") Long applicationId,
                                                                                        String token,
                                                                                        Pageable pageable) {
        return ResponseEntity.ok(applicationRecordService
                .findAllByAnimalIdAndApplicationId(animalId, applicationId, pageable, token));
    }

    @GetMapping(value = "/getAllByAnimalIdAndApplicationTypeId", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<ApplicationRecordResponse>> getAllByAnimalIdAndApplicationTypeId(@RequestParam("animalId") Long animalId,
                                                                                            @RequestParam("applicationTypeId") Long applicationTypeId,
                                                                                            String token,
                                                                                            Pageable pageable) {
        return ResponseEntity.ok(applicationRecordService
                .findAllByAnimalIdAndApplicationTypeId(animalId, applicationTypeId, pageable, token));
    }

    @GetMapping(value = "/getLastByAnimalIdAndApplicationId", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApplicationRecordResponse> getLastByAnimalIdAndApplicationId(@RequestParam("animalId") Long animalId,
                                                                                   @RequestParam("applicationId") Long applicationId,
                                                                                   String token) {
        return ResponseEntity.ok(applicationRecordService.findLastByAnimalIdAndApplicationId(animalId, applicationId, token));
    }

    @GetMapping(value = "/getLastByAnimalIdAndApplicationTypeId", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApplicationRecordResponse> getLastByAnimalIdAndApplicationTypeId(@RequestParam("animalId") Long animalId,
                                                                                       @RequestParam("applicationId") Long applicationTypeId,
                                                                                       String token) {
        return ResponseEntity.ok(applicationRecordService.findLastByAnimalIdAndApplicationTypeId(animalId, applicationTypeId, token));
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> createApplicationRecord(@Valid @RequestBody ApplicationRecordDTO applicationRecordDto,
                                                             @RequestHeader(name = "Authorization") String token) {
        ApplicationRecordResponse applicationRecordResponse = applicationRecordService.createApplicationRecord(applicationRecordDto, token);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/applicationRecords/{applicationRecordId}")
                .buildAndExpand(applicationRecordResponse.getId()).toUri();

        return ResponseEntity.created(location).body(
                new ApiResponse(true, "ApplicationRecord created successfully"));
    }



    @DeleteMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> deleteApplicationRecord(@RequestParam("applicationRecordId") Long applicationRecordId,
                                                      String token) {
        applicationRecordService.deleteApplicationRecord(applicationRecordId, token);

        return ResponseEntity.ok().build();
    }
}