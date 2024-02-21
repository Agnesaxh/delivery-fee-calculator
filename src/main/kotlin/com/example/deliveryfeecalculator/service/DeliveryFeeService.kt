package com.example.deliveryfeecalculator.service

import DeliveryFeeProperties
import com.example.deliveryfeecalculator.dto.DeliveryInfoDTO
import com.example.deliveryfeecalculator.dto.DeliveryFeeDTO
import com.example.deliveryfeecalculator.exception.DeliveryFeeException
import org.springframework.stereotype.Service
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

/**
 * Service for calculating delivery fees based on given delivery information.
 */
@Service
class DeliveryFeeService(
    private val validationService: ValidationService,
    private val deliveryFeeProperties: DeliveryFeeProperties
) {

    /**
     * Calculates the delivery fee based on the provided [DeliveryInfoDTO].
     *
     * @param request The [DeliveryInfoDTO] containing delivery information.
     * @return A [DeliveryFeeDTO] object containing the calculated delivery fee.
     * @throws DeliveryFeeException if any of the provided information is invalid.
     */
    fun calculateDeliveryFee(request: DeliveryInfoDTO): DeliveryFeeDTO {
        validationService.validateDeliveryInfoDTO(request)
        val response = DeliveryFeeDTO()
        val dateTime = LocalDateTime.parse(request.time, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        var totalDeliveryFee = 0

        if (request.cartValue >= deliveryFeeProperties.cartValue200Euro) {
            totalDeliveryFee = 0
        } else {
            totalDeliveryFee =
                calculateCartValueFee(request.cartValue) + calculateNumberOfItemsFee(request.numberOfItems) +
                        calculateDeliveryDistanceFee(request.deliveryDistance)

            if (isFridayRush(dateTime)) {
                totalDeliveryFee = applyFridayRushExtraCharge(totalDeliveryFee)
            }
        }
        response.deliveryFee = totalDeliveryFee.coerceAtMost(deliveryFeeProperties.maxDeliveryFee)
        return response
    }

    /**
     * Calculates the delivery fee based on the cart value
     * @param cartValue
     */
    fun calculateCartValueFee(cartValue: Int): Int {
        return when {
            cartValue < 1000 -> 1000 - cartValue
            else -> 0
        }
    }

    /**
     * Calculates the delivery fee based on number of items
     * @param numberOfItems
     */
    fun calculateNumberOfItemsFee(numberOfItems: Int): Int {
        return when {
            numberOfItems == 5 -> 50
            numberOfItems > 5 -> {
                val additionalItemsFee = (numberOfItems - 4) * 50
                additionalItemsFee + if (numberOfItems > 12) 120 else 0
            }

            else -> 0
        }
    }

    /**
     * Calculates the delivery fee based on delivery distance
     * @param deliveryDistance
     */
    fun calculateDeliveryDistanceFee(deliveryDistance: Int): Int {
        return when {
            deliveryDistance <= deliveryFeeProperties.baseDistance -> deliveryFeeProperties.baseFeeFor1000m
            else -> {
                var totalFee = deliveryFeeProperties.baseFeeFor1000m
                var distanceWithoutBaseDistance = deliveryDistance - deliveryFeeProperties.baseDistance
                while (distanceWithoutBaseDistance > 0) {
                    totalFee += deliveryFeeProperties.additionalFeePer500m
                    distanceWithoutBaseDistance -= 500
                }
                totalFee
            }
        }
    }

    /**
     * Checks if the delivery day is Friday and the time between 3-7pm
     * @param dateTime
     */
    fun isFridayRush(dateTime: LocalDateTime): Boolean {
        return if (dateTime.dayOfWeek == DayOfWeek.FRIDAY) {
            val instant = dateTime.toInstant(ZoneOffset.UTC)
            instant.atZone(ZoneOffset.UTC).hour in 15..19
        } else {
            false
        }
    }


    /**
     * Adds to the total delivery fee by 1.2x if the delivery is done on Friday between 3-7pm
     * @param totalDeliveryFee
     */
    fun applyFridayRushExtraCharge(totalDeliveryFee: Int): Int {
        val extraFee = (totalDeliveryFee * 1.2).toInt()
        return extraFee
    }
}
