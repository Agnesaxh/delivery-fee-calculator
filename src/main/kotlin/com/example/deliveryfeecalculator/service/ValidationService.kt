package com.example.deliveryfeecalculator.service

import com.example.deliveryfeecalculator.dto.DeliveryInfoDTO
import com.example.deliveryfeecalculator.exception.DeliveryFeeException
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

/**
 * Service for validation
 */
@Service
class ValidationService {

    /**
     * Validates the fields of DeliveryInfoDTO and throws exceptions if any of them are invalid.
     *
     * @param deliveryInfo The delivery information to be validated.
     * @throws DeliveryFeeException if any of the fields are invalid.
     */
    fun validateDeliveryInfoDTO(deliveryInfo: DeliveryInfoDTO) {
        val dateTime = LocalDateTime.parse(deliveryInfo.time, DateTimeFormatter.ISO_OFFSET_DATE_TIME)

        when {
            deliveryInfo.cartValue <= 0 -> throw DeliveryFeeException("Invalid cart value: ${deliveryInfo.cartValue}! Cart value must be greater than zero.")
            deliveryInfo.deliveryDistance <= 0 -> throw DeliveryFeeException("Invalid delivery distance: ${deliveryInfo.deliveryDistance}! Delivery distance must be greater than zero.")
            deliveryInfo.numberOfItems <= 0 -> throw DeliveryFeeException("Invalid number of items: ${deliveryInfo.numberOfItems}! Number of items must be greater than zero.")
            dateTime.atOffset(ZoneOffset.UTC).offset != ZoneOffset.UTC -> throw DeliveryFeeException("Invalid input for date: ${deliveryInfo.time}! Date must be in UTC format.")
        }
    }
}
