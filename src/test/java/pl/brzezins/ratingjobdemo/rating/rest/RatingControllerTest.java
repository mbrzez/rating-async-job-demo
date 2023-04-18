package pl.brzezins.ratingjobdemo.rating.rest;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.Duration;
import java.util.concurrent.Callable;

import static org.awaitility.Awaitility.await;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createRatingAndFetchShouldReturnRating() throws Exception {
        MvcResult createRatingResult = mockMvc.perform(post("/create-rating"))
                .andExpect(status().isOk())
                .andReturn();

        Integer jobExecutionId = JsonPath.read(createRatingResult.getResponse().getContentAsString(), "$.jobExecutionId");
        await().pollInterval(Duration.ofSeconds(1)).until(isJobExecutionCompleted(jobExecutionId));
    }

    private Callable<Boolean> isJobExecutionCompleted(Integer jobExecutionId) {
        return () -> {
            MvcResult jobExecutionRequest = mockMvc.perform(get("/create-rating/job-execution/" + jobExecutionId))
                    .andExpect(status().isOk())
                    .andReturn();
            String status = JsonPath.read(jobExecutionRequest.getResponse().getContentAsString(), "$.status");

            return status.equals("COMPLETED");
        };
    }
}