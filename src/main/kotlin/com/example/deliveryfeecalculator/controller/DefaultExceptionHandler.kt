package com.example.deliveryfeecalculator.controller

import com.example.deliveryfeecalculator.exception.DeliveryFeeException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

/**
 * Controller advice to handle exceptions globally.
 */
class ErrorMessageModel(
    val status: Int? = null,
    val message: String? = null
)

@ControllerAdvice
class DefaultExceptionHandler {

    /**
     * Handles [DeliveryFeeException] and returns a ResponseEntity with an error message.
     *
     * @param ex The exception to be handled.
     * @return ResponseEntity with an error message.
     */
    @ExceptionHandler(
        value = [
            DeliveryFeeException::class
        ]
    )
    fun handleDeliveryFeeException(ex: RuntimeException): ResponseEntity<ErrorMessageModel> {
        val errorMessage = ErrorMessageModel(HttpStatus.BAD_REQUEST.value(), ex.message)
        return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
    }
}
