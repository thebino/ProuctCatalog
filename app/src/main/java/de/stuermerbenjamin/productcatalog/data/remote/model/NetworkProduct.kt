package de.stuermerbenjamin.productcatalog.data.remote.model

import com.google.gson.annotations.SerializedName

/**
 *
 */
data class NetworkProduct(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val title: String,

    @SerializedName("description")
    val description: String,

    val price: NetworkPrice
)

data class NetworkPrice(
    val currency: String,
    val discount: Double,
    val priceGross: Double,
    val priceNet: Double,
    val taxpercent: Int
)
