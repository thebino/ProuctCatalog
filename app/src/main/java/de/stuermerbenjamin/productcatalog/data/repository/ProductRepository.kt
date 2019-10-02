package de.stuermerbenjamin.productcatalog.data.repository

import androidx.lifecycle.LiveData
import de.stuermerbenjamin.productcatalog.data.dao.ProductDao
import de.stuermerbenjamin.productcatalog.data.entity.Product
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ProductRepository(
    private val ioDispatcher: CoroutineDispatcher,
    private val productDao: ProductDao
) {
    val products = productDao.getProducts()

    suspend fun insert(product: Product) {
        withContext(ioDispatcher) {
            productDao.insert(product)
        }
    }

    suspend fun update(product: Product) {
        withContext(ioDispatcher) {
            productDao.update(product)
        }
    }

    suspend fun delete(product: Product) {
        withContext(ioDispatcher) {
            productDao.delete(product)
        }
    }
}
