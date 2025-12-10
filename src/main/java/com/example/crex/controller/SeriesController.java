package com.example.crex.controller;

import com.example.crex.dto.request.SeriesRequest;
import com.example.crex.dto.response.SeriesResponse;
import com.example.crex.service.signature.SeriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/series")
@RequiredArgsConstructor
public class SeriesController {

    private final SeriesService seriesService;

    // ✅ CREATE SERIES (ADMIN)
    @PostMapping("/add")
    public ResponseEntity<?> createSeries(
            @RequestBody SeriesRequest request,
            @RequestHeader("Authorization") String token) {

        SeriesResponse response = seriesService.createSeries(request, token);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // ✅ GET SERIES BY ID (USER + ADMIN)
    @GetMapping("/{id}")
    public ResponseEntity<?> getSeriesById(@PathVariable Integer id) {

        SeriesResponse response = seriesService.getSeriesById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // ✅ GET ALL SERIES (PUBLIC)
    @GetMapping("/all")
    public ResponseEntity<?> getAllSeries() {

        List<SeriesResponse> response = seriesService.getAllSeries();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // ✅ DELETE SERIES (ADMIN)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSeries(
            @PathVariable Integer id,
            @RequestHeader("Authorization") String token) {

        seriesService.deleteSeries(id, token);
        return ResponseEntity.ok("Series deleted successfully");
    }
}
