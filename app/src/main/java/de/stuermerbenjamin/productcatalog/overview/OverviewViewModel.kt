package de.stuermerbenjamin.productcatalog.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import de.stuermerbenjamin.productcatalog.data.repository.ProductRepository
import de.stuermerbenjamin.productcatalog.data.Result
import de.stuermerbenjamin.productcatalog.data.Result.Success
import de.stuermerbenjamin.productcatalog.data.entity.Product
import kotlinx.coroutines.launch

class OverviewViewModel(private val productRepository: ProductRepository) : ViewModel() {
    private val _forceUpdate = MutableLiveData<Boolean>(false)

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _items: LiveData<List<Product>> = _forceUpdate.switchMap { forceUpdate ->
        if (forceUpdate) {
            _dataLoading.value = true
            viewModelScope.launch {
                productRepository.refreshProducts()
                _dataLoading.value = false
            }
        }
        productRepository.observeProducts().switchMap { filterProducts(it) }
    }
    val items: LiveData<List<Product>> = _items

    // This LiveData depends on another so we can use a transformation.
    val empty: LiveData<Boolean> = Transformations.map(_items) {
        it.isEmpty()
    }

    private val _openDetailsEvent = MutableLiveData<Event<Product>>()
    val openDetailsEvent: LiveData<Event<Product>> = _openDetailsEvent

    init {
        loadItems(true)
    }

    fun loadItems(forceUpdate: Boolean) {
        _forceUpdate.value = forceUpdate
    }

    private fun filterProducts(productsResult: Result<List<Product>>): LiveData<List<Product>> {
        // TODO: This is a good case for liveData builder. Replace when stable.
        val result = MutableLiveData<List<Product>>()

        if (productsResult is Success) {
            viewModelScope.launch {
                result.value = productsResult.data
            }
        } else {
            result.value = emptyList()
            // TODO: show loading error
            // showSnackbarMessage(R.string.loading_products_error)
        }

        return result
    }

    fun addProduct() {
        // TODO: add product
    }
}
