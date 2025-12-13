package com.example.crex.service.signature;

import com.example.crex.dto.request.SeriesRequest;
import com.example.crex.dto.response.SeriesResponse;

import java.util.List;

public interface SeriesService {
    SeriesResponse createSeries(SeriesRequest request, String token);   // ADMIN only

    SeriesResponse getSeriesById(Integer seriesId);                     // USER + ADMIN

    List<SeriesResponse> getAllSeries();                                // PUBLIC

    void deleteSeries(Integer seriesId, String token);
    SeriesResponse updateSeries(Integer seriesId, SeriesRequest request);
}
