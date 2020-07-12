package de.stuermerbenjamin.productcatalog.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.stuermerbenjamin.productcatalog.data.Resource
import de.stuermerbenjamin.productcatalog.data.local.entity.Product
import de.stuermerbenjamin.productcatalog.data.repository.ProductRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailsViewModel(private val repository: ProductRepository) : ViewModel() {

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _selectedProduct = MutableLiveData<Product>()
    val selectedProduct: LiveData<Product> = _selectedProduct

    fun start(productId: String) {
        _dataLoading.value = true

        viewModelScope.launch {
            repository.getProductDetails(productId).collect { value ->

                when (value) {
                    is Resource.Success -> onProductLoaded(value.data)

                    is Resource.Error -> onDataNotAvailable()

                    is Resource.Loading -> {
                    }
                }
            }
        }
    }

    private fun onProductLoaded(product: Product) {
        _selectedProduct.value = product
        _dataLoading.value = false
    }

    private fun onDataNotAvailable() {
        _dataLoading.value = false
    }
}
