package top.pppppap.train;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * TODO
 *
 * @author pppppap
 * @since 2018-07-04 下午 17:52
 */

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    /**
     * 解决跨域访问时session不一致问题
     *
     * @param
     * @return
     **/
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedHeaders("*")
                .allowedMethods("*")
                .allowedOrigins("*")
                .allowCredentials(true);
    }
}
