package de.stuermerbenjamin.productcatalog.ui

import androidx.recyclerview.widget.RecyclerView
import de.stuermerbenjamin.productcatalog.data.entity.Product
import de.stuermerbenjamin.productcatalog.databinding.ProductItemBinding


class ProductViewHolder(private val binding: ProductItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bindTo(product: Product?, productItemClickListener: ProductAdapter.ProductItemClickListener) {
        binding.product = product
        binding.root.setOnClickListener {
            product?.let {
                it.isFavorite = !it.isFavorite
                productItemClickListener.itemClicked(it)
            }
        }
    }
}
