package de.stuermerbenjamin.productcatalog

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Config
import de.stuermerbenjamin.productcatalog.data.entity.Product
import de.stuermerbenjamin.productcatalog.data.repository.ProductRepository
import kotlinx.coroutines.launch
import androidx.paging.toLiveData

class MainViewModel(private val repository: ProductRepository) : ViewModel() {
    val products = repository.products.toLiveData(Config(
        pageSize = 8,
        enablePlaceholders = true,
        maxSize = 200
    ))

    fun insert(product: Product) {
        viewModelScope.launch {
            repository.insert(product)
        }
    }

    fun update(product: Product) {
        viewModelScope.launch {
            repository.update(product)
        }
    }

    fun delete(product: Product) {
        viewModelScope.launch {
            repository.delete(product)
        }
    }
}
