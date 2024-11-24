package com.organization.stockTicker.mapper

import com.organization.stockTicker.TestDataProvider.Companion.timeSeriesMappedData
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import kotlin.test.BeforeTest

@ExtendWith(MockitoExtension::class)
class StockDetailsMapperTest {
    private lateinit var stockDetailsMapper: StockDetailsMapper
    private val days = 3

    @BeforeTest
    fun setup() {
        stockDetailsMapper = StockDetailsMapper(days)
    }
    @Test
    fun `should map from StockData to StockDetails`() {
        val stockDetails = stockDetailsMapper.mapToStockDetails(timeSeriesMappedData)
        assertEquals(timeSeriesMappedData.metaData.symbol, stockDetails.symbol)
        assertEquals(days, stockDetails.averagePeriod)
        //TODO
    }
}