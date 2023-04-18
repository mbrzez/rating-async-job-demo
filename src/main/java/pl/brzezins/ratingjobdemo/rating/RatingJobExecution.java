package pl.brzezins.ratingjobdemo.rating;

import org.springframework.batch.core.JobParameter;

import java.util.Map;

public record RatingJobExecution(Long jobExecutionId, String status, Map<String, JobParameter<?>> jobParameterMap) {
}