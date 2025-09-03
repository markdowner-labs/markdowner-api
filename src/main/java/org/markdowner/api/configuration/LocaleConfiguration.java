package org.markdowner.api.configuration;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class LocaleConfiguration implements WebMvcConfigurer {

    @Bean
    LocaleResolver localeResolver() {
        final var localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.forLanguageTag("pt-BR"));
        return localeResolver;
    }

    @Bean
    LocaleChangeInterceptor localeChangeInterceptor() {
        final var interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        return interceptor;
    }

    @Override
    public void addInterceptors(@NonNull final InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}