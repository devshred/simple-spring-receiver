package cc.sandkasten.spring.receiver.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfig {
    @Bean
    public WebMvcConfigurer forwardingRules() {
        return new WebMvcConfigurer() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/").setViewName("forward:/web/all");
                registry.addViewController("/web").setViewName("forward:/web/all");
            }
        };
    }
}
