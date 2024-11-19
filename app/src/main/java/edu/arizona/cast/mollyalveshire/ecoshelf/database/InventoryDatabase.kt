package edu.arizona.cast.mollyalveshire.ecoshelf.databaseM

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import edu.arizona.cast.mollyalveshire.ecoshelf.database.InventoryDao
import edu.arizona.cast.mollyalveshire.ecoshelf.database.InventoryItem

@Database(entities = [InventoryItem::class], version = 1)
abstract class InventoryDatabase : RoomDatabase() {
    abstract fun inventoryDao(): InventoryDao

    companion object {
        @Volatile
        private var INSTANCE: InventoryDatabase? = null

        fun getDatabase(context: Context): InventoryDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    InventoryDatabase::class.java,
                    "inventory_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
