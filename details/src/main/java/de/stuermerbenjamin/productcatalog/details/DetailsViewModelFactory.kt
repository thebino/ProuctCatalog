package de.stuermerbenjamin.productcatalog.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.stuermerbenjamin.productcatalog.data.repository.ProductRepository

@Suppress("UNCHECKED_CAST")
class DetailsViewModelFactory(
    private val productRepository: ProductRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (DetailsViewModel(productRepository) as T)
}
