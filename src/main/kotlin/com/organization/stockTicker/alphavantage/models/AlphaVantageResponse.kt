package com.organization.stockTicker.alphavantage.models

import com.fasterxml.jackson.annotation.JsonProperty

data class StockData(
    @JsonProperty("Meta Data") val metaData: MetaData,
    @JsonProperty("Time Series (Daily)") val timeSeries: Map<String, DailyData>
)

data class MetaData(
    @JsonProperty("1. Information") val information: String,
    @JsonProperty("2. Symbol") val symbol: String,
    @JsonProperty("3. Last Refreshed") val lastRefreshed: String,
    @JsonProperty("4. Output Size") val outputSize: String,
    @JsonProperty("5. Time Zone") val timeZone: String
)

data class DailyData(
    @JsonProperty("1. open") val open: Double,
    @JsonProperty("2. high") val high: Double,
    @JsonProperty("3. low") val low: Double,
    @JsonProperty("4. close") val close: Double,
    @JsonProperty("5. volume") val volume: Long
)