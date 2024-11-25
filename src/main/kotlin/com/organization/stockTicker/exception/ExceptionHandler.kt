package com.organization.stockTicker.exception

import com.organization.stockTicker.models.ErrorReport
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
    fun handleValidationError(ex: Exception): List<ErrorReport> {
        logger.error("Error:{}", ex.message)
        val errors: MutableList<ErrorReport> = ArrayList()
        if (ex is MethodArgumentNotValidException) {
            ex.bindingResult.fieldErrors.forEach{error -> errors.add(ErrorReport(error.field))}
            return errors
        }
        errors.add(ErrorReport("Invalid field format"))
        return errors
    }

    @ExceptionHandler(ServiceUnavailableException::class)
    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    @ResponseBody
    fun handleValidationError(ex: ServiceUnavailableException): ErrorReport = ErrorReport(ex.message!!)

}
