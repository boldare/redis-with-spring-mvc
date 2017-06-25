package pl.xsolve.redisfilterdemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.xsolve.redisfilterdemo.config.RedisFilterConfiguration;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StoreRequestInRedisTest {

    private static final String SOME_RANDOM_GUID = "315be434-e50a-4088-ad51-13be08498d4c";
    private static final String REQUEST_BODY = "{\"name\": \"John\"}";
    private static final String EXPECTED_RESPONSE_BODY = "{\"greeting\": \"Hello John!\"}";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ValueOperations<String, String> valueOperations;

    @Test
    public void requestIsSavedToRedis() throws Exception {
        mockMvc.perform(post("/")
                    .header(RedisFilterConfiguration.SPARKPOST_BATCH_ID_HTTP_HEADER, SOME_RANDOM_GUID)
                    .content(REQUEST_BODY)
                    .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(EXPECTED_RESPONSE_BODY));

        verify(valueOperations).set(eq(SOME_RANDOM_GUID), eq(REQUEST_BODY));
    }

}
