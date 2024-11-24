package com.organization.stockTicker.alphavantage.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.organization.stockTicker.TestDataProvider.Companion.timeSeriesMappedData
import com.organization.stockTicker.TestDataProvider.Companion.timeSeriesRawData
import com.organization.stockTicker.alphavantage.models.StockData
import com.organization.stockTicker.exception.ServiceUnavailableException
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.web.client.HttpServerErrorException
import org.springframework.web.client.RestTemplate
import kotlin.test.BeforeTest
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@ExtendWith(MockitoExtension::class)
class AlphaVantageClientImplTest {
    lateinit var alphaVantageClientImpl: AlphaVantageClientImpl

    @Mock
    private lateinit var restTemplate: RestTemplate
    @Mock
    private lateinit var objectMapper: ObjectMapper
    @Mock
    private lateinit var httpServerErrorException: HttpServerErrorException

    private val url = "http://localhost:80"
    private lateinit var urlComponent: String

    @BeforeTest
    fun setup() {
        urlComponent = "http://localhost:80?apikey=&function=TIME_SERIES_DAILY&symbol="
        alphaVantageClientImpl = AlphaVantageClientImpl(restTemplate, objectMapper, url, "", "")
    }

    @Test
    fun shouldThrowServiceUnavailableException() {
        `when`(restTemplate.getForObject(anyString(), any(Class::class.java))).thenThrow(httpServerErrorException)
        assertThrows<ServiceUnavailableException> { alphaVantageClientImpl.getTimeSeriesData() }
    }

    @Nested
    inner class SuccessfulGet {
        @BeforeTest
        fun setup() {
            `when`(restTemplate.getForObject(urlComponent, String::class.java)).thenReturn(timeSeriesRawData)
        }

        @Test
        fun shouldCallRestTemplateWithUrlComponent() {
            alphaVantageClientImpl.getTimeSeriesData()
            verify(restTemplate).getForObject(urlComponent, String::class.java)
        }

        @Test
        fun shouldCallObjectMapperWithRawData() {
            alphaVantageClientImpl.getTimeSeriesData()
            verify(objectMapper).readValue(timeSeriesRawData, StockData::class.java)
        }

        @Test
        fun shouldReturnTimeseriesData() {
            `when`(objectMapper.readValue(timeSeriesRawData, StockData::class.java)).thenReturn(timeSeriesMappedData)
            assertNotNull(alphaVantageClientImpl.getTimeSeriesData()!!)
        }

        @Test
        fun shouldMapTimeseriesData() {
            `when`(objectMapper.readValue(timeSeriesRawData, StockData::class.java)).thenReturn(timeSeriesMappedData)
            val response = alphaVantageClientImpl.getTimeSeriesData()!!
            assertEquals(timeSeriesMappedData, response)
        }
    }

}