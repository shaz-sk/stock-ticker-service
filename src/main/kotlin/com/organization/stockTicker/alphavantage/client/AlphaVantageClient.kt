package com.organization.stockTicker.alphavantage.client

import com.organization.stockTicker.alphavantage.models.StockData


interface AlphaVantageClient {
    fun getTimeSeriesData(): StockData?

}
