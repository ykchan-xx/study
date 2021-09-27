package com.aisino.frems;

import lombok.extern.slf4j.Slf4j;
import org.asframework.common.util.IPUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Spring Boot应用启动类
 *
 * @author wenzhoaming
 */
@Slf4j
@EnableScheduling
@SpringBootApplication
public class FremsServicesApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(FremsServicesApplication.class);
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext application = SpringApplication.run(FremsServicesApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = IPUtil.getServerIp();
        String port = env.getProperty("server.port" );
        String path = env.getProperty("server.servlet.context-path" );
        log.info("\n----------------------------------------------------------\n\t" +
                "Application AsRestful is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port + path + "/\n\t" +
                "External: \thttp://" + ip + ":" + port + path + "/\n\t" +
                "swagger-ui: \thttp://" + ip + ":" + port + path + "/swagger-ui.html\n\t" +
                "----------------------------------------------------------" );
    }
}
