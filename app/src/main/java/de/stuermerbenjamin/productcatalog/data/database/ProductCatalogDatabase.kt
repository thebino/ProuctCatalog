package de.stuermerbenjamin.productcatalog.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import de.stuermerbenjamin.productcatalog.data.dao.ProductDao
import de.stuermerbenjamin.productcatalog.data.entity.Product
import java.util.concurrent.Executors

const val DATABASE_NAME = "products.db"


@Database(entities = [Product::class], version = 1, exportSchema = false)
abstract class ProductCatalogDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: ProductCatalogDatabase? = null

        fun getInstance(context: Context): ProductCatalogDatabase {
            return instance ?: synchronized(this) {
                instance
                    ?: buildDatabase(context).also { instance = it }
            }
        }

        private val IO_EXECUTOR = Executors.newSingleThreadExecutor()

        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): ProductCatalogDatabase {
            return Room
                .databaseBuilder(
                    context, ProductCatalogDatabase::class.java,
                    DATABASE_NAME
                )
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)

                        val request =
                            OneTimeWorkRequestBuilder<ProductCatalogPrePopulateDataWorker>().build()
                        WorkManager.getInstance(context).enqueue(request)
                    }
                })
                .build()
        }
    }
}
