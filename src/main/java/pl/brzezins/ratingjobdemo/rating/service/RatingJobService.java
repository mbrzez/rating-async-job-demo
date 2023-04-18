package pl.brzezins.ratingjobdemo.rating.service;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;
import pl.brzezins.ratingjobdemo.rating.RatingJobExecution;

@Service
@RequiredArgsConstructor
public class RatingJobService {
    private final JobLauncher jobLauncher;
    private final Job createRatingJob;
    private final RatingJobParamsService ratingJobParamsService;

    public RatingJobExecution runCreateRatingJob() {
        try {
            JobParameters jobParameters = ratingJobParamsService.createJobParameters();
            JobExecution jobExecution = jobLauncher.run(createRatingJob, jobParameters);

            return new RatingJobExecution(jobExecution.getJobId(),
                    jobExecution.getExitStatus().getExitCode(),
                    jobParameters.getParameters());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}