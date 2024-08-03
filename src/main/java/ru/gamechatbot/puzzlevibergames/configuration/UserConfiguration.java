package ru.gamechatbot.puzzlevibergames.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.gamechatbot.puzzlevibergames.model.User;

@Configuration
public class UserConfiguration {
    @Bean
    @Scope("prototype")
    User user(){
        return new User();
    }
}
