package com.example.deliveryfeecalculator

import DeliveryFeeProperties
import com.example.deliveryfeecalculator.dto.DeliveryFeeDTO
import com.example.deliveryfeecalculator.dto.DeliveryInfoDTO
import com.example.deliveryfeecalculator.service.DeliveryFeeService
import com.example.deliveryfeecalculator.service.ValidationService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.Assertions.assertEquals

import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@ExtendWith(MockitoExtension::class)
class DeliveryFeeServiceTest {

    private val deliveryFeeProperties = DeliveryFeeProperties(
        baseDistance = 1000,
        baseFeeFor1000m = 200,
        additionalFeePer500m = 100,
        maxDeliveryFee = 1500,
        cartValue200Euro = 20000
    )
    private val validationService = ValidationService()
    private val deliveryFeeService = DeliveryFeeService(validationService, deliveryFeeProperties)

    @Test
    fun `test calculate delivery fee`() {
        //test case when cart value is 200 euros or more
        val deliveryInfo200CartValue = DeliveryInfoDTO(
            cartValue = 20000,
            deliveryDistance = 1,
            numberOfItems = 1,
            time = "2024-01-19T11:00:00Z"
        )
        val deliveryFee200CartValue = DeliveryFeeDTO(0)
        assertEquals(deliveryFee200CartValue, deliveryFeeService.calculateDeliveryFee(deliveryInfo200CartValue))

        //test case when surcharges extend 15 euros
        val deliveryInfoMoreThan15 = DeliveryInfoDTO(
            cartValue = 200,
            deliveryDistance = 10000,
            numberOfItems = 20,
            time = "2024-01-19T11:00:00Z"
        )
        val deliveryFeeMoreThan15 = DeliveryFeeDTO(1500)
        assertEquals(deliveryFeeMoreThan15, deliveryFeeService.calculateDeliveryFee(deliveryInfoMoreThan15))

        //general test case
        val deliveryInfoDTO = DeliveryInfoDTO(
            cartValue = 790,
            deliveryDistance = 2235,
            numberOfItems = 4,
            time = "2024-01-15T13:00:00Z"
        )
        val deliveryFeeDTO = DeliveryFeeDTO(
            deliveryFee = 710
        )
        assertEquals(deliveryFeeDTO, deliveryFeeService.calculateDeliveryFee(deliveryInfoDTO))
    }

    @Test
    fun `test calculate cart value fee`() {
        assertEquals(110, deliveryFeeService.calculateCartValueFee(890))
    }

    @Test
    fun `test number of items fee`() {
        assertEquals(0, deliveryFeeService.calculateNumberOfItemsFee(4))
        assertEquals(50, deliveryFeeService.calculateNumberOfItemsFee(5))
        assertEquals(300, deliveryFeeService.calculateNumberOfItemsFee(10))
        assertEquals(570, deliveryFeeService.calculateNumberOfItemsFee(13))
        assertEquals(620, deliveryFeeService.calculateNumberOfItemsFee(14))
    }

    @Test
    fun `test delivery distance fee`() {
        assertEquals(300, deliveryFeeService.calculateDeliveryDistanceFee(1499))
        assertEquals(300, deliveryFeeService.calculateDeliveryDistanceFee(1500))
        assertEquals(400, deliveryFeeService.calculateDeliveryDistanceFee(1501))
        assertEquals(200, deliveryFeeService.calculateDeliveryDistanceFee(1000))
        assertEquals(200, deliveryFeeService.calculateDeliveryDistanceFee(999))
    }

    @Test
    fun `test isFriday fee`() {
        val dateTime = LocalDateTime.parse("2024-01-19T16:00:00Z", DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        val isFriday = deliveryFeeService.isFridayRush(dateTime)
        assertEquals(true, isFriday)
        assertEquals(480, deliveryFeeService.applyFridayRushExtraCharge(400))
    }
}
