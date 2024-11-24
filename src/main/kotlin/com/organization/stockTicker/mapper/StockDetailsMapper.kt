package com.organization.stockTicker.mapper

import com.organization.stockTicker.alphavantage.models.DailyData
import com.organization.stockTicker.alphavantage.models.StockData
import com.organization.stockTicker.models.StockDetails
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class StockDetailsMapper(@Value("\${alphavantage.ndays}") private var ndays: Int) {
    fun mapToStockDetails(stockData: StockData): StockDetails {
        val dailyClosingPrice = getDailyClosingPrice(stockData.timeSeries)
        return StockDetails(
            symbol = stockData.metaData.symbol,
            dailyClosingPrice = dailyClosingPrice,
            averageClosingPrice = averagePrice(dailyClosingPrice.values),
            averagePeriod = ndays
            )
    }

    private fun getDailyClosingPrice(dailyDataMap: Map<String, DailyData>): Map<String, Double> =
        dailyDataMap.entries.take(ndays).associate { it.key to it.value.close }

    private fun averagePrice(dailyPrice: Collection<Double>): Double = dailyPrice.sum()/ndays


}