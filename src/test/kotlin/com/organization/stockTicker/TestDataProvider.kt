package com.organization.stockTicker

import com.fasterxml.jackson.databind.ObjectMapper
import com.organization.stockTicker.alphavantage.models.StockData

class TestDataProvider {
    companion object {

        val timeSeriesRawData = """
            {
                "Meta Data": {
                    "1. Information": "Daily Prices (open, high, low, close) and Volumes",
                    "2. Symbol": "MSFT",
                    "3. Last Refreshed": "2024-11-22",
                    "4. Output Size": "Compact",
                    "5. Time Zone": "US/Eastern"
                },
                "Time Series (Daily)": {
                    "2024-11-22": {
                        "1. open": "411.3650",
                        "2. high": "417.4000",
                        "3. low": "411.0600",
                        "4. close": "417.0000",
                        "5. volume": "24814626"
                    },
                    "2024-11-21": {
                        "1. open": "419.5000",
                        "2. high": "419.7800",
                        "3. low": "410.2887",
                        "4. close": "412.8700",
                        "5. volume": "20780162"
                    },
                    "2024-11-20": {
                        "1. open": "416.8700",
                        "2. high": "417.2900",
                        "3. low": "410.5800",
                        "4. close": "415.4900",
                        "5. volume": "19191655"
                    },
                    "2024-11-19": {
                        "1. open": "413.1100",
                        "2. high": "417.9400",
                        "3. low": "411.5500",
                        "4. close": "417.7900",
                        "5. volume": "18133529"
                    },
                    "2024-11-18": {
                        "1. open": "414.8700",
                        "2. high": "418.4037",
                        "3. low": "412.1000",
                        "4. close": "415.7600",
                        "5. volume": "24742013"
                    },
                    "2024-11-15": {
                        "1. open": "419.8200",
                        "2. high": "422.8000",
                        "3. low": "413.6400",
                        "4. close": "415.0000",
                        "5. volume": "28247644"
                    }
                }
                }
                    """.trimIndent()

        val timeSeriesMappedData = ObjectMapper().readValue(timeSeriesRawData, StockData::class.java)
    }

}