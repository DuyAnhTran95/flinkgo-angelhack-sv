package vn.crazyx.flinkgo.config.requestLog;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class RequestLoggingFilterConfigurer {
    
    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter logFilter = new CommonsRequestLoggingFilter();
        logFilter.setIncludeHeaders(true);
        logFilter.setIncludePayload(true);
        logFilter.setMaxPayloadLength(1024);
        logFilter.setIncludeQueryString(true);
        return logFilter;
    }
}
