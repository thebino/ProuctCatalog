package de.stuermerbenjamin.productcatalog.data.repository

import androidx.test.espresso.idling.CountingIdlingResource
import de.stuermerbenjamin.productcatalog.data.Resource
import de.stuermerbenjamin.productcatalog.data.local.dao.ProductDao
import de.stuermerbenjamin.productcatalog.data.local.entity.Product
import de.stuermerbenjamin.productcatalog.data.remote.api.ProductApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

/**
 * Abstracts fetching data from remote and persisting it in a local data source for offline access.
 * So the repository is the single source of truth https://en.wikipedia.org/wiki/Single_source_of_truth
 *
 * Every data READ should trigger
 *   - return cache if available
 *   - return local copy if available
 *   - return remote copy
 *
 * Every data WRITE should trigger
 *   - update cache
 *   - update local
 *   - update remote
 */
class ProductRepository(
    private val localDataSource: ProductDao,
    private val remoteDataSource: ProductApiService
) {
    fun getProducts(): Flow<Resource<List<Product>>> = flow {
        // loading state
        emit(Resource.Loading)

        // load from external and persist
        try {
            val response = remoteDataSource.getProducts()
            response.map {
                Product(
                    it.id,
                    it.title,
                    it.description,
                    false
                )
            }.let(localDataSource::insertAll)
        } catch (e: Exception) {
            // ignore failure
        }

        // read from local
        val localData = localDataSource.getProducts()
        emitAll(localData.map { Resource.Success(it) })
    }.flowOn(Dispatchers.IO)

    fun getProductDetails(productId: String): Flow<Resource<Product>> = flow {
        // loading state
        emit(Resource.Loading)

        try {
            val response = remoteDataSource.getProduct(productId)
            localDataSource.insert(
                Product(
                    response.id,
                    response.title,
                    response.description,
                    false
                )
            )
        } catch (e: Exception) {
            // ignore failure
        }

        // read from local
        val localData = localDataSource.getProduct(productId)
        emit(Resource.Success(localData))
    }.flowOn(Dispatchers.IO)
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
