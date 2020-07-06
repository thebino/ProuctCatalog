package de.stuermerbenjamin.productcatalog.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.stuermerbenjamin.productcatalog.data.repository.ProductRepository

@Suppress("UNCHECKED_CAST")
class OverviewViewModelFactory(
    private val productRepository: ProductRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (OverviewViewModel(productRepository) as T)
}
