package de.stuermerbenjamin.productcatalog

import android.app.Application
import android.os.StrictMode
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import com.squareup.leakcanary.LeakCanary
import de.stuermerbenjamin.productcatalog.data.repository.ProductRepository
import io.fabric.sdk.android.Fabric

class ProductApplication : Application() {
    val productRepository: ProductRepository
        get() = ServiceLocator.provideProductCatalogRepository(this)

    override fun onCreate() {
        super.onCreate()

        startStrictModeIfNecessarry()

        //disableCrashreportingForDebug()

        initLeakCanary()
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

    private fun disableCrashreportingForDebug() {
        if (BuildConfig.DEBUG) {
            Fabric.with(
                applicationContext,
                Crashlytics.Builder().core(
                    CrashlyticsCore.Builder().build()
                ).build()
            )
        }
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
}
