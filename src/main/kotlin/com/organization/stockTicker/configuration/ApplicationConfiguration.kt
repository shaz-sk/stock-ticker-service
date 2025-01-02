package com.organization.stockTicker.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestTemplate

@Configuration
class ApplicationConfiguration {

    @Bean
    fun restTemplate(): RestTemplate = RestTemplate(
        HttpComponentsClientHttpRequestFactory().apply {
            setConnectTimeout(3000)
        }
    )

    @Bean
    fun objectMapper(): ObjectMapper = ObjectMapper()

}