package com.example.crex.service.implimentation;

import com.example.crex.config.JwtService;
import com.example.crex.converter.SeriesConverter;
import com.example.crex.dto.request.SeriesRequest;
import com.example.crex.dto.response.SeriesResponse;
import com.example.crex.exception.DuplicateResourceException;
import com.example.crex.exception.ResourceNotFoundException;
import com.example.crex.model.entity.Series;
import com.example.crex.model.entity.User;
import com.example.crex.repository.SeriesRepository;
import com.example.crex.repository.UserRepository;
import com.example.crex.service.signature.SeriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeriesServiceImp implements SeriesService {

    private final SeriesRepository seriesRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    // ✅ CREATE SERIES (ADMIN)
    @Override
    public SeriesResponse createSeries(SeriesRequest request, String token) {

        if (token.startsWith("Bearer "))
            token = token.substring(7);

        String email = jwtService.extractUsername(token);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!user.getUserRole().name().equals("ADMIN")) {
            throw new RuntimeException("Only ADMIN can create series");
        }

        if (seriesRepository.existsByTitleIgnoreCase(request.getTitle())) {
            throw new DuplicateResourceException("Series already exists with this title");
        }

        Series series = SeriesConverter.seriesRequestToSeries(request);

        Series saved = seriesRepository.save(series);

        return SeriesConverter.seriesToSeriesResponse(saved);
    }

    // ✅ GET SERIES BY ID (USER + ADMIN)
    @Override
    public SeriesResponse getSeriesById(Integer seriesId) {

        Series series = seriesRepository.findById(seriesId)
                .orElseThrow(() -> new ResourceNotFoundException("Series not found"));

        return SeriesConverter.seriesToSeriesResponse(series);
    }

    // ✅ GET ALL SERIES (PUBLIC)
    @Override
    public List<SeriesResponse> getAllSeries() {

        return seriesRepository.findAll()
                .stream()
                .map(SeriesConverter::seriesToSeriesResponse)
                .toList();
    }

    // ✅ DELETE SERIES (ADMIN)
    @Override
    public void deleteSeries(Integer seriesId, String token) {

        if (token.startsWith("Bearer "))
            token = token.substring(7);

        String email = jwtService.extractUsername(token);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!user.getUserRole().name().equals("ADMIN")) {
            throw new RuntimeException("Only ADMIN can delete series");
        }

        Series series = seriesRepository.findById(seriesId)
                .orElseThrow(() -> new ResourceNotFoundException("Series not found"));

        seriesRepository.delete(series);
    }
}

