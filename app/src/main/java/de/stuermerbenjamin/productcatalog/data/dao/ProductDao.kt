package de.stuermerbenjamin.productcatalog.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import de.stuermerbenjamin.productcatalog.data.entity.Product


@Dao
interface ProductDao {
    @Insert
    suspend fun insert(vararg product: Product)

    @Insert
    fun insertAll(data: List<Product>)

    @Query("SELECT * FROM products ORDER BY id")
    suspend fun getProducts(): List<Product>

    @Query("SELECT * FROM products ORDER BY id")
    fun observeProducts(): LiveData<List<Product>>

    @Update
    suspend fun update(vararg product: Product)

    @Delete
    suspend fun delete(vararg product: Product)
}
