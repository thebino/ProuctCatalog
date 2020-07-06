package de.stuermerbenjamin.productcatalog.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import de.stuermerbenjamin.productcatalog.data.dao.ProductDao
import de.stuermerbenjamin.productcatalog.data.entity.Product
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DefaultProductCatalogDataSource internal constructor(
    private val productDao: ProductDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ProductDataSource {
    override fun observeProducts(): LiveData<Result<List<Product>>> {
        return productDao.observeProducts().map {
            Result.Success(it)
        }
    }

    override suspend fun insert(product: Product) {
        productDao.insert(product)
    }

    override suspend fun update(product: Product) {
        productDao.update(product)
    }

    override suspend fun delete(product: Product) {
        productDao.delete(product)
    }
}
