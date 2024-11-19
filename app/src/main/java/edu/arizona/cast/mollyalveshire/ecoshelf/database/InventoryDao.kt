package edu.arizona.cast.mollyalveshire.ecoshelf.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface InventoryDao {

    // Insert a new inventory item
    @Insert
    suspend fun insertItem(item: InventoryItem)

    // Get all inventory items
    @Query("SELECT * FROM inventory_items")
    suspend fun getAllItems(): List<InventoryItem>

    // Optionally, add methods for update and delete
}