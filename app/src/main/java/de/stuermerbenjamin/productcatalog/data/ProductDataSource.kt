package de.stuermerbenjamin.productcatalog.data

import androidx.lifecycle.LiveData
import de.stuermerbenjamin.productcatalog.data.entity.Product

interface ProductDataSource {
    fun observeProducts(): LiveData<Result<List<Product>>>

    suspend fun insert(product: Product)

    suspend fun update(product: Product)

    suspend fun delete(product: Product)

}
