package com.organization.stockTicker.service

import com.organization.stockTicker.models.StockDetails

interface StockTickerService {
    fun getQuote(): StockDetails
}
