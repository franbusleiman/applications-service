package com.liro.treatments.controllers;

import com.liro.treatments.dto.ActionRecordDTO;
import com.liro.treatments.dto.ApiResponse;
import com.liro.treatments.dto.responses.ActionRecordResponse;
import com.liro.treatments.service.ActionRecordService;
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
@RequestMapping("/actionRecords")
public class ActionRecordController {

    private final ActionRecordService actionRecordService;

    @Autowired
    public ActionRecordController(ActionRecordService actionRecordService) {
        this.actionRecordService = actionRecordService;
    }

    @GetMapping(value = "/{actionRecordId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ActionRecordResponse> getActionRecord(@PathVariable("actionRecordId") Long actionRecordId) {
        return ResponseEntity.ok(actionRecordService.findById(actionRecordId, token));
    }

    @GetMapping(value = "/getAll", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<ActionRecordResponse>> getAll(@RequestParam("treatmentRecordId") Long treatmentRecordId,
                                                             Pageable pageable) {
        return ResponseEntity.ok(actionRecordService.findAllByTreatmentRecordId(treatmentRecordId, token, pageable));
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> createActionRecord(@Valid @RequestBody ActionRecordDTO actionRecordDto) {
        ActionRecordResponse actionRecordResponse = actionRecordService.createActionRecord(actionRecordDto, token);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/actionRecords/{actionRecordId}")
                .buildAndExpand(actionRecordResponse.getId()).toUri();

        return ResponseEntity.created(location).body(
                new ApiResponse(true, "ActionRecord created successfully"));
    }

    @DeleteMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> deleteActionRecord(@RequestParam("actionRecordId") Long actionRecordId, String token) {
        actionRecordService.deleteActionRecord(actionRecordId, token);

        return ResponseEntity.ok().build();
    }
}