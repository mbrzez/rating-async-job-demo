package pl.brzezins.ratingjobdemo.rating.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.brzezins.ratingjobdemo.rating.Rating;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RatingCreatingService {
    private final RatingScoreService ratingScoreService;

    public Rating createRating(UUID uuid) {
        return new Rating(uuid, ratingScoreService.calculateScore());
    }
}
