package pl.brzezins.ratingjobdemo.rating.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import pl.brzezins.ratingjobdemo.rating.Rating;
import pl.brzezins.ratingjobdemo.rating.jpa.BackedRatingRepository;
import pl.brzezins.ratingjobdemo.rating.service.RatingCreatingService;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class RatingCreatingTasklet implements Tasklet {
    private final BackedRatingRepository backedRatingRepository;
    private final RatingCreatingService ratingCreatingService;

    @Value("#{jobParameters['create.rating.uuid']}")
    private String uuid;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        Rating rating = ratingCreatingService.createRating(UUID.fromString(uuid));
        backedRatingRepository.save(rating);

        log.info("Rating with {} uuid and {} score saved", rating.uuid(), rating.score());

        return RepeatStatus.FINISHED;
    }
}