package de.stuermerbenjamin.productcatalog.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import de.stuermerbenjamin.productcatalog.R
import de.stuermerbenjamin.productcatalog.data.local.entity.Product

class ProductAdapter(private val productItemClickListener: ProductItemClickListener) :
    PagedListAdapter<Product, ProductViewHolder>(diffCallback) {
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bindTo(getItem(position), productItemClickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProductViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.product_item,
            parent,
            false
        )
    )

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem.id == newItem.id

            /**
             * Note that in kotlin, == checking on data classes compares all contents, but in Java,
             * typically you'll implement Object#equals, and use it to compare object contents.
             */
            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem == newItem
        }
    }

    interface ProductItemClickListener {
        fun itemClicked(product: Product)
    }
}
