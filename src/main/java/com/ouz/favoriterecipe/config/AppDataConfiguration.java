package com.ouz.favoriterecipe.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;


@Configuration
public class AppDataConfiguration
{

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSourceBundle =
                new ResourceBundleMessageSource();
        messageSourceBundle.setBasenames("localization/message");
        messageSourceBundle.setDefaultEncoding("UTF-8");
        return messageSourceBundle;
    }
}
