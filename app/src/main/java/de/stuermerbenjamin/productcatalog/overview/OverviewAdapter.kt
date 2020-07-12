package de.stuermerbenjamin.productcatalog.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.stuermerbenjamin.productcatalog.data.local.entity.Product
import de.stuermerbenjamin.productcatalog.databinding.ProductItemBinding

class OverviewAdapter(private val viewModel: OverviewViewModel) :
    ListAdapter<Product, OverviewAdapter.ViewHolder>(CountdownDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ProductItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(viewModel, getItem(position))

    class ViewHolder constructor(val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: OverviewViewModel, product: Product) {
            binding.product = product
            binding.viewmodel = viewModel
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder = ViewHolder(
                ProductItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }
}

class CountdownDiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id &&
            oldItem.name == newItem.name &&
            oldItem.description == newItem.description &&
            oldItem.isFavorite == newItem.isFavorite
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}
