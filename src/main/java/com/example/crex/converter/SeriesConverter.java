package com.example.crex.converter;

import com.example.crex.dto.request.SeriesRequest;
import com.example.crex.dto.response.SeriesResponse;
import com.example.crex.model.entity.Series;

public class SeriesConverter {

    public static Series seriesRequestToSeries(SeriesRequest seriesRequest){
        return Series.builder()
                .title(seriesRequest.getTitle())
                .season(seriesRequest.getSeason())
                .startDate(seriesRequest.getStartDate())
                .endDate(seriesRequest.getEndDate())
                .organizer(seriesRequest.getOrganizer())
                .format(seriesRequest.getFormat())
                .build();
    }

    public static SeriesResponse seriesToSeriesResponse(Series series){

        int totalMatches = (series.getMatches() != null)
                ? series.getMatches().size()
                : 0;

        return SeriesResponse.builder()
                .seriesId(series.getSeriesId())
                .title(series.getTitle())
                .season(series.getSeason())
                .startDate(series.getStartDate())
                .endDate(series.getEndDate())
                .organizer(series.getOrganizer())
                .format(series.getFormat())
                .totalMatches(totalMatches)
                .build();
    }
}
