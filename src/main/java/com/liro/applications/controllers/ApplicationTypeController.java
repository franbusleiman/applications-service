package com.liro.applications.controllers;

import com.liro.applications.dto.responses.ApiResponse;
import com.liro.applications.dto.ApplicationTypeDTO;
import com.liro.applications.dto.responses.ApplicationTypeResponse;
import com.liro.applications.service.ApplicationTypeService;
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
@RequestMapping("/applicationTypes")
public class ApplicationTypeController {

    private final ApplicationTypeService applicationTypeService;

    @Autowired
    public ApplicationTypeController(ApplicationTypeService applicationTypeService) {
        this.applicationTypeService = applicationTypeService;
    }

    @GetMapping(value = "/{applicationTypeId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApplicationTypeResponse> getApplicationType(@PathVariable("applicationTypeId") Long applicationTypeId) {
        return ResponseEntity.ok(applicationTypeService.findById(applicationTypeId));
    }

    @GetMapping(value = "/getAll", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<ApplicationTypeResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(applicationTypeService.findAll(pageable));
    }

    @GetMapping(value = "/getByNameContaining", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<ApplicationTypeResponse>> getByNameContaining(
            @RequestParam("nameContaining") String nameContaining, Pageable pageable) {
        return ResponseEntity.ok(applicationTypeService.findAllByNameContaining(nameContaining, pageable));
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> createApplicationType(@Valid @RequestBody ApplicationTypeDTO applicationTypeDto) {
        ApplicationTypeResponse applicationTypeResponse = applicationTypeService.createApplicationType(applicationTypeDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/applicationTypes/{applicationTypeId}")
                .buildAndExpand(applicationTypeResponse.getId()).toUri();

        return ResponseEntity.created(location).body(
                new ApiResponse(true, "ApplicationType created successfully"));
    }
}