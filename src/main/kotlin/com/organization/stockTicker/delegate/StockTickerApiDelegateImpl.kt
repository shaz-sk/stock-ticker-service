package com.organization.stockTicker.delegate

import com.organization.stockTicker.api.StockTickerApiDelegate
import com.organization.stockTicker.models.StockDetails
import com.organization.stockTicker.service.StockTickerService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

/*
Delegate class that receives the API request from Controller
 */
@Service
class StockTickerApiDelegateImpl(private val stockTickerService : StockTickerService) : StockTickerApiDelegate {

    override fun getClosingQuote(): ResponseEntity<StockDetails> =
        ResponseEntity.ok(stockTickerService.getClosingQuote())

}