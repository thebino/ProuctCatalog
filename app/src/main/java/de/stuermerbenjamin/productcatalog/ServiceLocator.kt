package de.stuermerbenjamin.productcatalog

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Room
import de.stuermerbenjamin.productcatalog.data.local.dao.ProductDao
import de.stuermerbenjamin.productcatalog.data.local.database.DATABASE_NAME
import de.stuermerbenjamin.productcatalog.data.local.database.ProductDatabase
import de.stuermerbenjamin.productcatalog.data.remote.api.ProductApiService
import de.stuermerbenjamin.productcatalog.data.remote.api.ProductMockInterceptor
import de.stuermerbenjamin.productcatalog.data.repository.ProductRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceLocator {
    private val lock = Any()
    private var database: ProductDatabase? = null
    private var apiService: ProductApiService? = null

    @Volatile
    var productRepository: ProductRepository? = null
        @VisibleForTesting set

    fun provideProductCatalogRepository(context: Context): ProductRepository {
        synchronized(this) {
            return productRepository ?: createProductCatalogRepository(context)
        }
    }

    private fun createProductCatalogRepository(context: Context): ProductRepository {
        val newRepo = ProductRepository(
            createLocalDataSource(context),
            createRemoteDatasource()
        )
        productRepository = newRepo
        return newRepo
    }

    private fun createLocalDataSource(context: Context): ProductDao {
        val database = database ?: createDatabase(context)
        return database.productDao()
    }

    private fun createDatabase(context: Context): ProductDatabase {
        val result = Room.databaseBuilder(
            context.applicationContext,
            ProductDatabase::class.java, DATABASE_NAME
        ).build()
        database = result
        return result
    }

    private fun createRemoteDatasource(): ProductApiService {
        return apiService ?: createApiService()
    }

    private fun createApiService(): ProductApiService {
        val mockInterceptor = ProductMockInterceptor()

        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        val okhttpClient = OkHttpClient
            .Builder()
            .addInterceptor(logging)
            .addInterceptor(mockInterceptor)
            .build()

        val result = Retrofit.Builder()
            .baseUrl("https://google.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okhttpClient)
            .build()
            .create(ProductApiService::class.java)

        apiService = result
        return result
    }

    @VisibleForTesting
    fun resetRepository() {
        synchronized(lock) {
            // Clear all data to avoid test pollution.
            database?.apply {
                clearAllTables()
                close()
            }
            database = null
            productRepository = null
        }
    }
}
