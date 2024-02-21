package com.example.deliveryfeecalculator.dto
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

/**
 * Data Transfer Object (DTO) for representing delivery fee information.
 * This class uses Jackson annotation `@JsonNaming` to specify the SnakeCaseStrategy for property naming.
 * @property deliveryFee The calculated delivery fee in cents.
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class DeliveryFeeDTO(
    var deliveryFee: Int? = null,
)