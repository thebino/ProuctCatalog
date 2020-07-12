package de.stuermerbenjamin.productcatalog.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import de.stuermerbenjamin.productcatalog.ProductApplication
import de.stuermerbenjamin.productcatalog.data.local.entity.Product
import de.stuermerbenjamin.productcatalog.details.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment(R.layout.fragment_details) {
    lateinit var binding: FragmentDetailsBinding
    private val viewModel: DetailsViewModel by viewModels {
        DetailsViewModelFactory(
            (requireContext().applicationContext as ProductApplication).productRepository
        )
    }

    private val args: DetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDetailsBinding.bind(view).apply {
            textView.text = args.title
            refreshLayout.setOnRefreshListener {
                viewModel.start(args.productId)
            }
        }

        viewModel.start(args.productId)

        viewModel.dataLoading.observe(viewLifecycleOwner, Observer<Boolean> {
            binding.refreshLayout.isRefreshing = it
        })

        viewModel.selectedProduct.observe(viewLifecycleOwner, Observer<Product> { product ->
            product?.let {
                binding.textviewProductDescription.text = it.description
            }
        })
    }
}
