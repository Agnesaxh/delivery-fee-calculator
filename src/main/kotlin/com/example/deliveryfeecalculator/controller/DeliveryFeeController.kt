package com.example.deliveryfeecalculator.controller

import com.example.deliveryfeecalculator.api.DeliveryFeeEndpoint
import com.example.deliveryfeecalculator.dto.DeliveryInfoDTO
import com.example.deliveryfeecalculator.dto.DeliveryFeeDTO
import com.example.deliveryfeecalculator.service.DeliveryFeeService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 * REST controller for handling delivery fee calculations.
 */
@RestController
class DeliveryFeeController(private val deliveryFeeService: DeliveryFeeService) : DeliveryFeeEndpoint {

    /**
     * Calculates the delivery fee based on the provided [DeliveryInfoDTO].
     *
     * @param request The [DeliveryInfoDTO] containing delivery information.
     * @return [DeliveryFeeDTO] containing the calculated delivery fee.
     */
    @PostMapping("/delivery-fee")
    override fun calculateDeliveryFee(@RequestBody request: DeliveryInfoDTO): DeliveryFeeDTO {
        return deliveryFeeService.calculateDeliveryFee(request)
    }
}
