package de.stuermerbenjamin.productcatalog.data.local.database

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import de.stuermerbenjamin.productcatalog.data.local.entity.Product
import java.io.IOException

class ProductCatalogPrePopulateDataWorker(
    private val appContext: Context,
    workerParams: WorkerParameters
) :
    Worker(appContext, workerParams) {
    private val nameType = object : TypeToken<ArrayList<Product>>() {}.type

    override fun doWork() = try {
        val objectArrayString: String =
            appContext.assets.open("products.json").bufferedReader().use { it.readText() }

        val products = Gson().fromJson<List<Product>>(objectArrayString, nameType)

        val database = ProductDatabase.getInstance(applicationContext)
        database.productDao().insertAll(products)

        Result.success()
    } catch (e: IOException) {
        Result.failure()
    }
}
