package de.stuermerbenjamin.productcatalog.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import de.stuermerbenjamin.productcatalog.data.Resource
import de.stuermerbenjamin.productcatalog.data.local.entity.Product
import de.stuermerbenjamin.productcatalog.data.repository.ProductRepository

class OverviewViewModel(repository: ProductRepository) : ViewModel() {

    private val products: LiveData<Resource<List<Product>>> = repository.getProducts().asLiveData()
    fun getProducts(): LiveData<Resource<List<Product>>> = products

    val dataLoading: LiveData<Boolean> = Transformations.map(products) {
        it is Resource.Loading
    }

    // This LiveData depends on another so we can use a transformation.
    val empty: LiveData<Boolean> = Transformations.map(products) {
        it is Resource.Success && it.data.isEmpty()
    }

    private val _openDetailsEvent = MutableLiveData<Event<Product>>()
    val openDetailsEvent: LiveData<Event<Product>> = _openDetailsEvent

    /**
     * Called by Data Binding.
     */
    fun openDetails(product: Product) {
        _openDetailsEvent.value = Event(product)
    }
}
