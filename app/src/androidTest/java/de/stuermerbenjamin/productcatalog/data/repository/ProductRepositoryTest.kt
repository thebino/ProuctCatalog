package de.stuermerbenjamin.productcatalog.data.repository

import androidx.arch.core.executor.testing.CountingTaskExecutorRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import de.stuermerbenjamin.productcatalog.MainCoroutineRule
import de.stuermerbenjamin.productcatalog.data.Resource
import de.stuermerbenjamin.productcatalog.data.local.dao.ProductDao
import de.stuermerbenjamin.productcatalog.data.local.database.ProductDatabase
import de.stuermerbenjamin.productcatalog.data.local.entity.Product
import de.stuermerbenjamin.productcatalog.data.remote.api.ProductApiService
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ProductRepositoryTest {
    @Rule
    @JvmField
    val countingTaskExecutorRule = CountingTaskExecutorRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var _db: ProductDatabase
    val db: ProductDatabase
        get() = _db

    @Before
    fun initDb() {
        _db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ProductDatabase::class.java
        ).build()
    }

    @After
    fun closeDb() {
        countingTaskExecutorRule.drainTasks(10, TimeUnit.SECONDS)
        _db.close()
    }

    private lateinit var localDataSource: ProductDao
    private lateinit var remoteDataSource: ProductApiService

    // class under test
    private lateinit var productRepository: ProductRepository

    private val localProducts = listOf<Product>()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun createRepository() {
        // Get a reference to the class under test
        productRepository = ProductRepository(db.productDao(), remoteDataSource)
    }

    @Test
    fun getProducts_requestsAllProductsFromLocalDataSource() = mainCoroutineRule.runBlockingTest {
        // When products are requested from the repository
        val products = productRepository.getProducts().asLiveData()

        // Then products are loaded from the remote data source
        assert(products.value is Resource.Success<List<Product>>)
    }
}
