package de.stuermerbenjamin.productcatalog

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Room
import de.stuermerbenjamin.productcatalog.data.DefaultProductCatalogDataSource
import de.stuermerbenjamin.productcatalog.data.ProductDataSource
import de.stuermerbenjamin.productcatalog.data.database.DATABASE_NAME
import de.stuermerbenjamin.productcatalog.data.database.ProductCatalogDatabase
import de.stuermerbenjamin.productcatalog.data.repository.DefaultProductRepository
import de.stuermerbenjamin.productcatalog.data.repository.ProductRepository

object ServiceLocator {
    private val lock = Any()
    private var database: ProductCatalogDatabase? = null

    @Volatile
    var productRepository: ProductRepository? = null
        @VisibleForTesting set

    fun provideProductCatalogRepository(context: Context): ProductRepository {
        synchronized(this) {
            return productRepository ?: createProductCatalogRepository(context)
        }
    }

    private fun createProductCatalogRepository(context: Context): ProductRepository {
        val newRepo = DefaultProductRepository(createLocalDataSource(context))
        productRepository = newRepo
        return newRepo
    }

    private fun createLocalDataSource(context: Context): ProductDataSource {
        val database = database ?: createDatabase(context)
        return DefaultProductCatalogDataSource(database.productDao())
    }

    private fun createDatabase(context: Context): ProductCatalogDatabase {
        val result = Room.databaseBuilder(
            context.applicationContext,
            ProductCatalogDatabase::class.java, DATABASE_NAME
        ).build()
        database = result
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
