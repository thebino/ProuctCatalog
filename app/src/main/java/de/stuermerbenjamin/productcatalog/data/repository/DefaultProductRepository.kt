package de.stuermerbenjamin.productcatalog.data.repository

import androidx.lifecycle.LiveData
import androidx.test.espresso.idling.CountingIdlingResource
import de.stuermerbenjamin.productcatalog.data.ProductDataSource
import de.stuermerbenjamin.productcatalog.data.entity.Product
import de.stuermerbenjamin.productcatalog.data.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultProductRepository(
    private val localDataSource: ProductDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): ProductRepository {

    override fun observeProducts(): LiveData<Result<List<Product>>> {
        wrapEspressoIdlingResource {
            return localDataSource.observeProducts()
        }
    }

    override suspend fun refreshProducts() {
        TODO("Not yet implemented")
    }

    suspend fun insert(product: Product) {
        withContext(ioDispatcher) {
            localDataSource.insert(product)
        }
    }

    suspend fun update(product: Product) {
        withContext(ioDispatcher) {
            localDataSource.update(product)
        }
    }

    suspend fun delete(product: Product) {
        withContext(ioDispatcher) {
            localDataSource.delete(product)
        }
    }
}


/**
 * Contains a static reference to [IdlingResource]
 */
object EspressoIdlingResource {

    private const val RESOURCE = "GLOBAL"

    @JvmField
    val countingIdlingResource = CountingIdlingResource(RESOURCE)

    fun increment() {
        countingIdlingResource.increment()
    }

    fun decrement() {
        if (!countingIdlingResource.isIdleNow) {
            countingIdlingResource.decrement()
        }
    }
}

inline fun <T> wrapEspressoIdlingResource(function: () -> T): T {
    // Espresso does not work well with coroutines yet. See
    // https://github.com/Kotlin/kotlinx.coroutines/issues/982
    EspressoIdlingResource.increment() // Set app as busy.
    return try {
        function()
    } finally {
        EspressoIdlingResource.decrement() // Set app as idle.
    }
}
