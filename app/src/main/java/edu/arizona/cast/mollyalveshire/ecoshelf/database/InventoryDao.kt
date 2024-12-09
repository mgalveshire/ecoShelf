package edu.arizona.cast.mollyalveshire.ecoshelf.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import edu.arizona.cast.mollyalveshire.ecoshelf.InventoryItem

@Dao
interface InventoryDao {

    @Query("SELECT * FROM inventory_item WHERE id = :id LIMIT 1")
    fun getInventoryItemById(id: Int): LiveData<InventoryItem>

    @Update
    fun updateInventoryItem(item: InventoryItem)

    @Insert
    suspend fun insertItem(item: InventoryItem)

    @Update
    suspend fun updateItem(item: InventoryItem)

    // Delete item by item object
    @Delete
    suspend fun deleteItem(item: InventoryItem)

    @Query("SELECT * FROM inventory_item")
    suspend fun getAllItems(): List<InventoryItem>

    @Query("SELECT COUNT(*) FROM inventory_item")
    suspend fun getTotalItemCount(): Int

}