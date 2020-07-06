package de.stuermerbenjamin.productcatalog.data.repository

import androidx.lifecycle.LiveData
import de.stuermerbenjamin.productcatalog.data.entity.Product
import de.stuermerbenjamin.productcatalog.data.Result

interface ProductRepository {
    fun observeProducts(): LiveData<Result<List<Product>>>

    suspend fun refreshProducts()
}
