package com.example.deliveryfeecalculator.api

import com.example.deliveryfeecalculator.dto.DeliveryFeeDTO
import com.example.deliveryfeecalculator.dto.DeliveryInfoDTO
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody


/**
 * API endpoint for calculating delivery fees based on the provided delivery information.
 */
interface DeliveryFeeEndpoint {

    /**
     * Calculates the delivery fee based on the provided delivery information.
     *
     * @param request The delivery information containing details such as cart value, delivery distance, number of items, and time.
     * @return A [DeliveryFeeDTO] containing the calculated delivery fee.
     */
    @ApiOperation("Calculate delivery fee based on the provided information.")
    @PostMapping("/delivery-fee")
    fun calculateDeliveryFee(@RequestBody request: DeliveryInfoDTO): DeliveryFeeDTO
}
