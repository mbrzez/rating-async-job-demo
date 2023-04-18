package pl.brzezins.ratingjobdemo.rating.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import pl.brzezins.ratingjobdemo.rating.jpa.BackedRatingRepository;
import pl.brzezins.ratingjobdemo.rating.service.RatingCreatingService;

@Configuration
public class RatingBatchJobConfiguration {
    public static final String CREATE_RATING_JOB = "create-rating-job";
    public static final String CREATE_RATING_STEP = "create-rating-step";
    public static final String CREATE_RATING_JOB_PARAM_UUID = "create.rating.uuid";

    @Bean
    public JobLauncher asyncJobLauncher(JobRepository jobRepository, TaskExecutor jobLauncherExecutor) throws Exception {
        TaskExecutorJobLauncher jobLauncher = new TaskExecutorJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.setTaskExecutor(jobLauncherExecutor);
        jobLauncher.afterPropertiesSet();

        return jobLauncher;
    }

    @Bean
    public TaskExecutor jobLauncherExecutor() {
        return new SimpleAsyncTaskExecutor();
    }

    @Bean
    public Job createRatingJob(JobRepository jobRepository, Step createRatingStep) {
        return new JobBuilder(CREATE_RATING_JOB, jobRepository)
                .preventRestart()
                .start(createRatingStep)
                .build();
    }

    @Bean
    public Step createRatingStep(JobRepository jobRepository,
                                 PlatformTransactionManager transactionManager,
                                 Tasklet createRatingTasklet) {
        return new StepBuilder(CREATE_RATING_STEP, jobRepository)
                .tasklet(createRatingTasklet, transactionManager)
                .build();
    }

    @Bean
    @StepScope
    public Tasklet createRatingTasklet(BackedRatingRepository backedRatingRepository, RatingCreatingService ratingCreatingService) {
        return new RatingCreatingTasklet(backedRatingRepository, ratingCreatingService);
    }
}