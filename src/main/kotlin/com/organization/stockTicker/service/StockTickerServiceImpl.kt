package com.organization.stockTicker.service

import com.organization.stockTicker.alphavantage.client.AlphaVantageClient
import com.organization.stockTicker.mapper.StockDetailsMapper
import com.organization.stockTicker.models.StockDetails
import org.springframework.stereotype.Service

/*
Service class that has the business logic
 */
@Service
class StockTickerServiceImpl(private val alphaVantageClient: AlphaVantageClient,
                             private val stockDetailsMapper: StockDetailsMapper) : StockTickerService {

    override fun getClosingQuote(): StockDetails {
        val timeSeriesData = alphaVantageClient.getTimeSeriesData()
        return stockDetailsMapper.mapToStockDetails(timeSeriesData!!)
    }

}