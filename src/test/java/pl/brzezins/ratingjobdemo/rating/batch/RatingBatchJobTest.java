package pl.brzezins.ratingjobdemo.rating.batch;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.brzezins.ratingjobdemo.rating.jpa.RatingRepository;
import pl.brzezins.ratingjobdemo.rating.service.RatingJobParamsService;

import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

import static org.awaitility.Awaitility.await;

@SpringBootTest
class RatingBatchJobTest {
    @Autowired
    private Job createRatingJob;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private RatingJobParamsService ratingJobParamsService;

    @Test
    public void createRatingJobIsCompletedWithRatingCreated() throws Exception {
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();

        JobExecution firstJobExecution = jobLauncher.run(createRatingJob, ratingJobParamsService.createJobParameters(uuid1));
        JobExecution secondJobExecution = jobLauncher.run(createRatingJob, ratingJobParamsService.createJobParameters(uuid2));

        await().until(() -> firstJobExecution.getExitStatus().equals(ExitStatus.COMPLETED));
        await().until(() -> secondJobExecution.getExitStatus().equals(ExitStatus.COMPLETED));
        await().until(() -> getRatingCreatedCount(List.of(uuid1, uuid2)) == 2);
    }

    private Integer getRatingCreatedCount(List<UUID> uuids) {
        return StreamSupport.stream(ratingRepository.findAllById(uuids).spliterator(), false)
                .toList()
                .size();
    }
}