package com.guptaji.springboot_learning.config;

import com.guptaji.springboot_learning.service.impl.UserHelper;
import com.guptaji.springboot_learning.service.impl.UsersUtility;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CodeConfigs {

    @Value("${name.prefix}")
    private String prefix;

    @Value("${name.suffix}")
    private String suffix;

    @Bean("userHelper")
    public UserHelper userHelper(){
        return new UserHelper(prefix, suffix);
    }

    @Bean
    public UsersUtility usersUtility(@Qualifier("userHelper") UserHelper userHelper){
        return new UsersUtility(userHelper);
    }
}
