package com.organization.stockTicker.alphavantage.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.organization.stockTicker.alphavantage.models.StockData
import com.organization.stockTicker.exception.ServiceUnavailableException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Service
class AlphaVantageClientImpl(private val restTemplate: RestTemplate,
                             private val objectMapper: ObjectMapper,
                             @Value("\${alphavantage.url}") private var url: String,
                             @Value("\${alphavantage.apikey}") private var apikey: String,
                             @Value("\${alphavantage.symbol}") private var symbol: String) : AlphaVantageClient {

    private var logger: Logger = LoggerFactory.getLogger(ExceptionHandler::class.java)

    private val function = "TIME_SERIES_DAILY"

    override fun getTimeSeriesData(): StockData? {
        try {
            val response = restTemplate.getForObject(getUri(), String::class.java)
            return objectMapper.readValue(response, StockData::class.java)
        } catch (e: Exception) {
            logger.error("Exception: ${e.message}")
            throw ServiceUnavailableException("Alphavantage service error $e.message")
        }
    }

    private fun getUri() = UriComponentsBuilder.fromHttpUrl(url)
        .queryParam("apikey", apikey)
        .queryParam("function", function)
        .queryParam("symbol", symbol)
        .toUriString()
}