package com.organization.stockTicker.alphavantage.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.organization.stockTicker.alphavantage.models.StockData
import com.organization.stockTicker.exception.ServiceUnavailableException
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

/*
This client is used to make third party API call (Alphavantage)
 */
@Service
class AlphaVantageClientImpl(private val restTemplate: RestTemplate,
                             private val objectMapper: ObjectMapper,
                             @Value("\${alphavantage.url}") private var url: String,
                             @Value("\${alphavantage.apikey}") private var apikey: String,
                             @Value("\${alphavantage.symbol}") private var symbol: String) : AlphaVantageClient {

    private var logger: Logger = LoggerFactory.getLogger(ExceptionHandler::class.java)

    private val function = "TIME_SERIES_DAILY"

    @CircuitBreaker(name = "circuit-breaker", fallbackMethod = "fallbackResponse")
    override fun getTimeSeriesData(): StockData? {
        try {
            logger.info("Retrieving data from Alphavantage with symbol $symbol and function $function")
            val response = restTemplate.getForObject(getUri(), String::class.java)
            logger.info("Mapping response received from Alphavantage ")

            return objectMapper.readValue(response, StockData::class.java)

        } catch (e: Exception) {
            logger.error("Alphavantage service exception: ${e.message}")
            throw ServiceUnavailableException("Alphavantage service error $e.message")
        }
    }

    fun fallbackResponse(e: Exception): StockData? {
        logger.error("Fallback on exception: ${e.message}")
        throw ServiceUnavailableException("Alphavantage service error $e.message")
    }

    private fun getUri() = UriComponentsBuilder.fromHttpUrl(url)
        .queryParam("apikey", apikey)
        .queryParam("function", function)
        .queryParam("symbol", symbol)
        .toUriString()
}