package de.stuermerbenjamin.productcatalog.overview

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import de.stuermerbenjamin.productcatalog.ProductApplication
import de.stuermerbenjamin.productcatalog.R
import de.stuermerbenjamin.productcatalog.databinding.FragmentOverviewBinding

class OverviewFragment : Fragment(R.layout.fragment_overview) {
    private var viewDataBinding: FragmentOverviewBinding? = null

    private lateinit var listAdapter: OverviewAdapter

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

            listAdapter = OverviewAdapter(viewModel)
        }

        viewModel.openDetailsEvent.observe(this, EventObserver {
            val action = OverviewFragmentDirections.actionOverviewToDetails(
                resources.getString(R.string.details_title_exist, it.name)
            )
            findNavController().navigate(action)
        })
    }
}
