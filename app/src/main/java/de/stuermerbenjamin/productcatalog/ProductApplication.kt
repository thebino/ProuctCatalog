package de.stuermerbenjamin.productcatalog

import android.app.Application
import android.os.StrictMode
import com.squareup.leakcanary.LeakCanary
import de.stuermerbenjamin.productcatalog.data.repository.ProductRepository

class ProductApplication : Application() {
    val productRepository: ProductRepository
        get() = ServiceLocator.provideProductCatalogRepository(this)

    override fun onCreate() {
        super.onCreate()

        startStrictModeIfNecessarry()

        initLeakCanary()
    }

    private fun startStrictModeIfNecessarry() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .penaltyDeath()
                    .build()
            )
            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .penaltyDeath()
                    .build()
            )
        }
    }

    private fun initLeakCanary() {
        if (BuildConfig.DEBUG) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                // This process is dedicated to LeakCanary for heap analysis.
                // You should not init your app in this process.
                return
            }
            LeakCanary.install(this)
        }
    }
}
