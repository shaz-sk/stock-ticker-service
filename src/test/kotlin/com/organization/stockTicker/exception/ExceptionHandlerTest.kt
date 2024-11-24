package com.organization.stockTicker.exception

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.converter.HttpMessageNotReadableException

@ExtendWith(MockitoExtension::class)
class ExceptionHandlerTest {

    @InjectMocks
    private lateinit var exceptionHandler: ExceptionHandler

    @Mock
    private lateinit var httpMessageException: HttpMessageNotReadableException

    @Mock
    private lateinit var serviceUnavailableException: ServiceUnavailableException

    private val serviceErrorMessage = "Service Error"


    @Test
    fun shouldHandleOtherException() {
        val response: List<Error> = exceptionHandler.handleValidationError(httpMessageException)
        assertEquals("Invalid field format", response[0].message)
    }

    @Test
    fun shouldHandleServiceUnavailableException() {
        `when`(serviceUnavailableException.message).thenReturn(serviceErrorMessage)
        val response: Error = exceptionHandler.handleValidationError(serviceUnavailableException)
        assertEquals(serviceErrorMessage, response.message)
    }

}