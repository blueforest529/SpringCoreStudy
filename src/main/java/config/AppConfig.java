package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import spring.MemberDao;

@Configuration
public class AppConfig {

    @Bean
    public MemberDao memberDao() {
        return new MemberDao();
    }
}
