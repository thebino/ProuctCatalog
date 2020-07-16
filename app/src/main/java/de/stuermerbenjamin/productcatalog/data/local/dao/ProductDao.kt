package de.stuermerbenjamin.productcatalog.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import de.stuermerbenjamin.productcatalog.data.local.entity.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Insert
    suspend fun insert(vararg product: Product)

    @Insert
    fun insertAll(data: List<Product>)

    @Query("SELECT * FROM products ORDER BY id")
    fun getProducts(): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE id = :productId")
    fun getProduct(productId: String): Product

    @Update
    suspend fun update(vararg product: Product)

    @Delete
    suspend fun delete(vararg product: Product)
}
