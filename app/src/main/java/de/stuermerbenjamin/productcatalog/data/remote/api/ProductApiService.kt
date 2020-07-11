package de.stuermerbenjamin.productcatalog.data.remote.api

import de.stuermerbenjamin.productcatalog.data.remote.model.NetworkProduct
import retrofit2.http.GET

interface ProductApiService {
    @GET("products")
    suspend fun getProducts(): List<NetworkProduct>
}
