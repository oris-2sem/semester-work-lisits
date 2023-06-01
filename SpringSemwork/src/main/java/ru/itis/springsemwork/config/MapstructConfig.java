package ru.itis.springsemwork.config;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.itis.springsemwork.dto.converters.ItemConverter;
import ru.itis.springsemwork.dto.converters.UserConverter;

@Configuration
public class MapstructConfig {
    @Bean
    public ItemConverter itemConverter() {
        return Mappers.getMapper(ItemConverter.class);
    }

    @Bean
    public UserConverter userConverter() {
        return Mappers.getMapper(UserConverter.class);
    }
}
