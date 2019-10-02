package de.stuermerbenjamin.productcatalog.data.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import de.stuermerbenjamin.productcatalog.data.entity.Product


@Dao
interface ProductDao {
    @Insert
    fun insert(vararg product: Product)

    @Insert
    fun insertAll(data: List<Product>)

    @Query("SELECT * FROM products ORDER BY id")
    fun getProducts(): DataSource.Factory<Int, Product>

    @Update
    fun update(vararg product: Product)

    @Delete
    fun delete(vararg product: Product)
}
