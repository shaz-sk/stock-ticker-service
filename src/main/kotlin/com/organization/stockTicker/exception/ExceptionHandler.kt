package com.organization.stockTicker.exception

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class ExceptionHandler {

    private var logger: Logger = LoggerFactory.getLogger(ExceptionHandler::class.java)

    @ExceptionHandler(MethodArgumentNotValidException::class, HttpMessageNotReadableException::class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handleValidationError(ex: Exception): Error {
        logger.error("Error:{}", ex.message)
        if (ex is MethodArgumentNotValidException) {
            return  Error(ex.bindingResult.fieldErrors[0].field)
        }
        return Error("Invalid field format")
    }

    @ExceptionHandler(ServiceUnavailableException::class)
    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    @ResponseBody
    fun handleValidationError(ex: ServiceUnavailableException): Error = Error(ex.message)

}
