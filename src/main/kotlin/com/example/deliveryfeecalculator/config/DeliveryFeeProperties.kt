import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Configuration properties for delivery fees.
 * This class is annotated with `@ConfigurationProperties` to bind properties with the specified prefix from the application.yml file.
 */
@ConfigurationProperties(prefix = "delivery.fee")
data class DeliveryFeeProperties (
    val baseDistance: Int,
    val baseFeeFor1000m: Int,
    val additionalFeePer500m: Int,
    val maxDeliveryFee: Int,
    val cartValue200Euro: Int
)
