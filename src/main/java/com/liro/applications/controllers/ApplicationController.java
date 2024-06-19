package com.liro.applications.controllers;

import com.liro.applications.dto.ApiResponse;
import com.liro.applications.dto.ApplicationDTO;
import com.liro.applications.dto.responses.ApplicationResponse;
import com.liro.applications.service.ApplicationService;
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
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping(value = "/{applicationId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApplicationResponse> getApplication(@PathVariable("applicationId") Long applicationId) {
        return ResponseEntity.ok(applicationService.findById(applicationId));
    }

    @GetMapping(value = "/getAll", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<ApplicationResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(applicationService.findAll(pageable));
    }

    @GetMapping(value = "/getByNameContaining", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<ApplicationResponse>> getByNameContaining(
            @RequestParam("nameContaining") String nameContaining, Pageable pageable) {
        return ResponseEntity.ok(applicationService.findAllByNameContaining(nameContaining, pageable));
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> createApplication(@Valid @RequestBody ApplicationDTO applicationDto) {
        ApplicationResponse applicationResponse = applicationService.createApplication(applicationDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/applications/{applicationId}")
                .buildAndExpand(applicationResponse.getId()).toUri();

        return ResponseEntity.created(location).body(
                new ApiResponse(true, "Application created successfully"));
    }

}
