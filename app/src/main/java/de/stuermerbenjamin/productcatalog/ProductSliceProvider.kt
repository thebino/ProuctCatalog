package de.stuermerbenjamin.productcatalog

import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.graphics.drawable.IconCompat
import androidx.lifecycle.asLiveData
import androidx.slice.Slice
import androidx.slice.SliceProvider
import androidx.slice.builders.ListBuilder
import androidx.slice.builders.ListBuilder.LARGE_IMAGE
import androidx.slice.builders.SliceAction
import androidx.slice.builders.cell
import androidx.slice.builders.gridRow
import androidx.slice.builders.header
import androidx.slice.builders.list
import de.stuermerbenjamin.productcatalog.data.Resource

class ProductSliceProvider : SliceProvider() {

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreateSliceProvider(): Boolean {
        val repository = (requireContext().applicationContext as ProductApplication).productRepository

        val data = repository.getProducts().asLiveData()
        when (data.value) {
            is Resource.Success -> {
                // TODO: cache 4 random products for later usage
            }
            is Resource.Error -> {

            }
            Resource.Loading -> {

            }
        }

        return true
    }

    override fun onBindSlice(sliceUri: Uri): Slice? {
        when (sliceUri.path) {
            "/" -> return createProductSlice(sliceUri)
        }

        return null
    }

    private fun createProductSlice(sliceUri: Uri): Slice {
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        return list(context!!, sliceUri, ListBuilder.INFINITY) {
            header {
                title = "Popular Products"
                primaryAction = SliceAction.create(
                    pendingIntent,
                    IconCompat.createWithResource(context, R.drawable.ic_baseline_shopping_cart_24),
                    ListBuilder.ICON_IMAGE,
                    "Popular Products"
                )
            }
            gridRow {
                cell {
                    addImage(IconCompat.createWithResource(context, R.drawable.dagger), LARGE_IMAGE)
                    addTitleText("Dagger")
                    addText("$ 1300")
                    contentIntent = pendingIntent
                }
                cell {
                    addImage(IconCompat.createWithResource(context, R.drawable.hilt), LARGE_IMAGE)
                    addTitleText("Hilt")
                    addText("$ 140")
                    contentIntent = pendingIntent
                }
                cell {
                    addImage(IconCompat.createWithResource(context, R.drawable.flower), LARGE_IMAGE)
                    addTitleText("Flower")
                    addText("$ 7")
                    contentIntent = pendingIntent
                }
                cell {
                    addImage(IconCompat.createWithResource(context, R.drawable.bottle), LARGE_IMAGE)
                    addTitleText("Bottle")
                    addText("$ 13")
                    contentIntent = pendingIntent
                }
            }
        }
    }
}
