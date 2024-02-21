package com.example.deliveryfeecalculator.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

/**
 * Data Transfer Object (DTO) for representing delivery information.
 * This class uses Jackson annotation `@JsonNaming` to specify the SnakeCaseStrategy for property naming.
 *
 * @property cartValue The cart value for the delivery.
 * @property deliveryDistance The delivery distance in meters.
 * @property numberOfItems The number of items in the delivery.
 * @property time The delivery time in ISO 8601 format.
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class DeliveryInfoDTO(
    val cartValue: Int,
    val deliveryDistance: Int,
    val numberOfItems: Int,
    val time: String
)
