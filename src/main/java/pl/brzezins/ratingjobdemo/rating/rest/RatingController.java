package pl.brzezins.ratingjobdemo.rating.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.brzezins.ratingjobdemo.rating.Rating;
import pl.brzezins.ratingjobdemo.rating.RatingJobExecution;
import pl.brzezins.ratingjobdemo.rating.jpa.BackedRatingRepository;
import pl.brzezins.ratingjobdemo.rating.service.RatingJobService;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;

import static pl.brzezins.ratingjobdemo.rating.batch.RatingBatchJobConfiguration.CREATE_RATING_JOB;

@RestController
@RequiredArgsConstructor
public class RatingController {
    private final RatingJobService ratingJobService;
    private final BackedRatingRepository backedRatingRepository;
    private final JobExplorer jobExplorer;

    @GetMapping("/create-rating/{uuid}")
    public ResponseEntity<Rating> getRatingByUuid(@PathVariable("uuid") UUID uuid) {
        Optional<Rating> rating = backedRatingRepository.findById(uuid);

        return ResponseEntity.of(rating);
    }

    @PostMapping("/create-rating")
    public ResponseEntity<RatingJobExecution> createRating() {
        RatingJobExecution ratingJobExecution = ratingJobService.runCreateRatingJob();

        return ResponseEntity.ok(ratingJobExecution);
    }

    @GetMapping("/create-rating/job-execution/{id}")
    public ResponseEntity<RatingJobExecution> getJobExecution(@PathVariable("id") Long id) {
        Optional<RatingJobExecution> ratingJobExecution = Optional.ofNullable(jobExplorer.getJobExecution(id))
                .filter(isCreateRatingJob())
                .map(toRatingJobExecution());

        return ResponseEntity.of(ratingJobExecution);
    }



    private Function<JobExecution, RatingJobExecution> toRatingJobExecution() {
        return e -> new RatingJobExecution(e.getJobId(),
                e.getExitStatus().getExitCode(),
                e.getJobParameters().getParameters());
    }

    private Predicate<JobExecution> isCreateRatingJob() {
        return (e) -> CREATE_RATING_JOB.equals(e.getJobInstance().getJobName());
    }
}