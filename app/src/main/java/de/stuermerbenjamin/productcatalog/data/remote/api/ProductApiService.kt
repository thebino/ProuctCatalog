package de.stuermerbenjamin.productcatalog.data.remote.api

import de.stuermerbenjamin.productcatalog.data.remote.model.NetworkProduct
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApiService {
    @GET("products")
    suspend fun getProducts(): List<NetworkProduct>

    @GET("product/{productId}")
    suspend fun getProduct(@Path("productId") productId: String): NetworkProduct
}
