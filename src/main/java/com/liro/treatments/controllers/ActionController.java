package com.liro.treatments.controllers;

import com.liro.treatments.dto.ActionDTO;
import com.liro.treatments.dto.ApiResponse;
import com.liro.treatments.dto.responses.ActionResponse;
import com.liro.treatments.service.ActionService;
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
@RequestMapping("/actions")
public class ActionController {

    private final ActionService actionService;

    @Autowired
    public ActionController(ActionService actionService) {
        this.actionService = actionService;
    }

    @GetMapping(value = "/{actionId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ActionResponse> getAction(@PathVariable("actionId") Long actionId) {
        return ResponseEntity.ok(actionService.findById(actionId));
    }

    @GetMapping(value = "/getAll", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<ActionResponse>> getAll(@RequestParam("treatmentId") Long treatmentId,
                                                       Pageable pageable) {
        return ResponseEntity.ok(actionService.findAllByTreatmentId(treatmentId, pageable));
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> createAction(@Valid @RequestBody ActionDTO actionDTO) {
        ActionResponse actionResponse = actionService.createAction(actionDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/actions/{actionId}")
                .buildAndExpand(actionResponse.getId()).toUri();

        return ResponseEntity.created(location).body(
                new ApiResponse(true, "Action created successfully"));
    }

    @DeleteMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> deleteAction(@RequestParam("actionId") Long actionId) {
        actionService.deleteAction(actionId);

        return ResponseEntity.ok().build();
    }
}
