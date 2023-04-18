package pl.brzezins.ratingjobdemo.rating.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RatingScoreService {
    public Integer calculateScore() {
        return new Random().nextInt(0, 100);
    }
}