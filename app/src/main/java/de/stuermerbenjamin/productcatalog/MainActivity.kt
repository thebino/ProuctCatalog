package de.stuermerbenjamin.productcatalog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import de.stuermerbenjamin.productcatalog.data.entity.Product
import de.stuermerbenjamin.productcatalog.databinding.MainActivityBinding
import de.stuermerbenjamin.productcatalog.ui.ProductAdapter

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels { ViewModelFactory(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val adapter = ProductAdapter(object : ProductAdapter.ProductItemClickListener {
            override fun itemClicked(product: Product) {
                viewModel.update(product)
            }
        })
        viewModel.products.observe(this, Observer(adapter::submitList))

        DataBindingUtil.setContentView<MainActivityBinding>(this, R.layout.main_activity).apply {
            lifecycleOwner = this@MainActivity
            viewmodel = viewModel

            recyclerviewProducts.adapter = adapter
        }
    }
}
