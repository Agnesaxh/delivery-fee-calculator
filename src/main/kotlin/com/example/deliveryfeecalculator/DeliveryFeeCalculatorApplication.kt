package com.example.deliveryfeecalculator

import DeliveryFeeProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(DeliveryFeeProperties::class)
class DeliveryFeeCalculatorApplication

fun main(args: Array<String>) {
    runApplication<DeliveryFeeCalculatorApplication>(*args)
}
