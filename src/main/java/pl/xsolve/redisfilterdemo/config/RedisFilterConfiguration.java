package pl.xsolve.redisfilterdemo.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class RedisFilterConfiguration {

    public static final String SPARKPOST_BATCH_ID_HTTP_HEADER = "X-MessageSystems-Batch-ID";
    private final ValueOperations<String, String> redisValueOps;

    public RedisFilterConfiguration(final ValueOperations<String, String> redisValueOps) {
        this.redisValueOps = redisValueOps;
    }

    @Bean
    public Filter redisOncePerRequestFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
                                            final FilterChain filterChain) throws ServletException, IOException {
                final ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
                final String batchId = requestWrapper.getHeader(SPARKPOST_BATCH_ID_HTTP_HEADER);

                try {
                    filterChain.doFilter(requestWrapper, response);
                } finally {
                    final ContentCachingRequestWrapper wrapperAfterRequest = WebUtils.getNativeRequest(requestWrapper, ContentCachingRequestWrapper.class);
                    final String payload = new String(wrapperAfterRequest.getContentAsByteArray(), wrapperAfterRequest.getCharacterEncoding());
                    redisValueOps.set(batchId, payload);
                }
            }
        };
    }
}
