package pl.brzezins.ratingjobdemo.rating.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "rating")
public class RatingEntity {
    @Id
    private UUID uuid;
    private Integer score;

    public RatingEntity() {}

    public RatingEntity(UUID uuid, Integer score) {
        this.uuid = uuid;
        this.score = score;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}