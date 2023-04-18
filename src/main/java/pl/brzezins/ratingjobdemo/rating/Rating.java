package pl.brzezins.ratingjobdemo.rating;

import java.util.UUID;

public record Rating(UUID uuid, Integer score) {
}