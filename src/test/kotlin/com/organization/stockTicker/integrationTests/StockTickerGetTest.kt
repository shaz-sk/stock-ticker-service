package com.organization.stockTicker.integrationTests

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import com.organization.stockTicker.StockTickerApplication
import com.organization.stockTicker.TestDataProvider
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.net.MalformedURLException
import java.net.URL
import kotlin.test.BeforeTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = [StockTickerApplication::class])
@TestPropertySource(locations = ["classpath:application-test.yaml"])
@ActiveProfiles("test")
class StockTickerGetTest {

    private var wireMockServer: WireMockServer? = null

    @Autowired
    private val mvc: MockMvc? = null

    @LocalServerPort
    private val randomMockMvcPort = 0

    @BeforeTest
    fun setup() {
        setupWireMock()
    }

    @AfterEach
    fun tearDown() {
        wireMockServer!!.stop()
    }

    @Test
    @Throws(Exception::class)
    fun updateCustomer() {
        mvc!!.perform(
            MockMvcRequestBuilders.get("http://localhost:$randomMockMvcPort/api/v1/stock/report")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(expectedResponse()))
    }

    fun expectedResponse(): String = "{\"symbol\":\"MSFT\",\"averageClosingPrice\":415.12,\"averagePeriod\":3,\"dailyClosingPrice\":{\"2024-11-22\":417.0,\"2024-11-21\":412.87,\"2024-11-20\":415.49}}"

    @Throws(MalformedURLException::class)
    private fun setupWireMock() {
        val basUrl = URL("http://localhost:8000")
        val port = basUrl.port
        val host = basUrl.host
        wireMockServer = WireMockServer(
            WireMockConfiguration().port(port)
        )
        wireMockServer!!.start()
        WireMock.configureFor(host, port)

        WireMock.stubFor(
            WireMock
                .get(WireMock.anyUrl())
                .willReturn(
                    WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(TestDataProvider.timeSeriesRawData)
                )
        )
    }
}