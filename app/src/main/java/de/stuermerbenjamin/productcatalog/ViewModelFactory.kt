package de.stuermerbenjamin.productcatalog

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.stuermerbenjamin.productcatalog.data.database.ProductCatalogDatabase
import de.stuermerbenjamin.productcatalog.data.repository.ProductRepository
import kotlinx.coroutines.Dispatchers

class ViewModelFactory(application: Application) : ViewModelProvider.Factory {
    private val productDao = ProductCatalogDatabase.getInstance(application).productDao()
    private val repository = ProductRepository(Dispatchers.IO, productDao)

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return MainViewModel(repository) as T
    }
}
