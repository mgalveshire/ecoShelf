package edu.arizona.cast.mollyalveshire.ecoshelf

import androidx.lifecycle.LiveData
import edu.arizona.cast.mollyalveshire.ecoshelf.database.InventoryDao

class InventoryRepository(private val dao: InventoryDao) {


    suspend fun insertItem(item: InventoryItem) {
        dao.insertItem(item)
    }

    fun getInventoryItemById(id: Int): LiveData<InventoryItem> {
        return dao.getInventoryItemById(id)
    }

    // Update an item in the database
    fun updateItem(item: InventoryItem) {
        dao.updateInventoryItem(item)
    }

}