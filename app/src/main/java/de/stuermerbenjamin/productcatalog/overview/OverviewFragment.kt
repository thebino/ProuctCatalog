package de.stuermerbenjamin.productcatalog.overview

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import de.stuermerbenjamin.productcatalog.ProductApplication
import de.stuermerbenjamin.productcatalog.R
import de.stuermerbenjamin.productcatalog.data.Resource
import de.stuermerbenjamin.productcatalog.data.local.entity.Product
import de.stuermerbenjamin.productcatalog.databinding.FragmentOverviewBinding

class OverviewFragment : Fragment(R.layout.fragment_overview) {
    private var viewDataBinding: FragmentOverviewBinding? = null

    private val listAdapter = OverviewAdapter()
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private val viewModel by viewModels<OverviewViewModel> {
        OverviewViewModelFactory(
            (requireContext().applicationContext as ProductApplication).productRepository
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding = FragmentOverviewBinding.bind(view).apply {
            viewmodel = viewModel
            lifecycleOwner = viewLifecycleOwner

            recyclerviewProducts.apply {
                adapter = listAdapter
            }

            swipeRefreshLayout = refreshLayout
        }

        viewModel.dataLoading.observe(viewLifecycleOwner, Observer<Boolean> {
            swipeRefreshLayout.isRefreshing = it
        })

        viewModel.getProducts()
            .observe(viewLifecycleOwner, Observer<Resource<List<Product>>> { state ->
                when (state) {
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> listAdapter.submitList(state.data)
                    is Resource.Error -> Toast.makeText(
                        context,
                        "An error occured: ${state.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })

        viewModel.openDetailsEvent.observe(viewLifecycleOwner, EventObserver {
            val action = OverviewFragmentDirections.actionOverviewToDetails(
                resources.getString(R.string.details_title_exist, it.name)
            )
            findNavController().navigate(action)
        })
    }
}
