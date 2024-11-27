package com.organization.stockTicker.service

import com.organization.stockTicker.alphavantage.client.AlphaVantageClient
import com.organization.stockTicker.mapper.StockDetailsMapper
import com.organization.stockTicker.models.StockDetails
import org.springframework.stereotype.Service

/*
Service class that handles the business logic for retrieving and processing stock ticker data
 */
@Service
class StockTickerServiceImpl(private val alphaVantageClient: AlphaVantageClient,
                             private val stockDetailsMapper: StockDetailsMapper) : StockTickerService {

    override fun getClosingQuote(): StockDetails {
        val timeSeriesData = alphaVantageClient.getTimeSeriesData()
        return stockDetailsMapper.mapTimeSeriesToStockDetails(timeSeriesData!!)
    }

}