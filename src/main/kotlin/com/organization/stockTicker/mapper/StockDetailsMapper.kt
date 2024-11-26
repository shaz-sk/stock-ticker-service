package com.organization.stockTicker.mapper

import com.organization.stockTicker.alphavantage.models.DailyData
import com.organization.stockTicker.alphavantage.models.StockData
import com.organization.stockTicker.models.StockDetails
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

/*
Mapper to map the third party response to our API response
 */
@Service
class StockDetailsMapper(@Value("\${alphavantage.ndays}") private var ndays: Int) {
    private val maxDays = 30
    fun mapTimeSeriesToStockDetails(stockData: StockData): StockDetails {
        stockData.timeSeries?.let {
            val dailyClosingPrice = getDailyClosingPrice(it)
            return StockDetails(
                symbol = stockData.metaData?.symbol,
                dailyClosingPrice = dailyClosingPrice,
                averageClosingPrice = averagePrice(dailyClosingPrice.values),
                averagePeriod = ndays
            )
        }
        return StockDetails(information = stockData.information)

    }

    private fun getDailyClosingPrice(dailyDataMap: Map<String, DailyData>): Map<String, Double> {
        ndays = if (ndays < maxDays) ndays else maxDays
        return dailyDataMap.entries.take(ndays).associate { it.key to it.value.close }
    }

    private fun averagePrice(dailyPrice: Collection<Double>): Double = dailyPrice.sum()/ndays


}