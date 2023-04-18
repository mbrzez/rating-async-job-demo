package pl.brzezins.ratingjobdemo.rating.jpa;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface RatingRepository extends CrudRepository<RatingEntity, UUID> {
}