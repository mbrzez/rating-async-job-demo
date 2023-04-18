package pl.brzezins.ratingjobdemo.rating.service;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static pl.brzezins.ratingjobdemo.rating.batch.RatingBatchJobConfiguration.CREATE_RATING_JOB_PARAM_UUID;

@Service
@RequiredArgsConstructor
public class RatingJobParamsService {
    public JobParameters createJobParameters() {
        return createJobParameters(UUID.randomUUID());
    }

    public JobParameters createJobParameters(UUID uuid) {
        JobParametersBuilder builder = new JobParametersBuilder();
        builder.addString(CREATE_RATING_JOB_PARAM_UUID, uuid.toString());

        return builder.toJobParameters();
    }
}