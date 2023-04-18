package pl.brzezins.ratingjobdemo.rating.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.brzezins.ratingjobdemo.rating.Rating;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class BackedRatingRepository {
    private final RatingRepository ratingRepository;

    public void save(Rating rating) {
        ratingRepository.save(new RatingEntity(rating.uuid(), rating.score()));
    }

    public Optional<Rating> findById(UUID uuid) {
        return ratingRepository.findById(uuid)
                .map(ratingEntity -> new Rating(ratingEntity.getUuid(), ratingEntity.getScore()));
    }
}