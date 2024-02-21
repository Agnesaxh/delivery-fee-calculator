package com.example.deliveryfeecalculator.exception

/**
 * Custom runtime exception for handling delivery fee-related errors.
 * This exception is thrown when there is an issue related to the calculation or validation of delivery fees.
 * @param message A descriptive message providing details about the exception.
 */
class DeliveryFeeException(message: String) : RuntimeException(message)
