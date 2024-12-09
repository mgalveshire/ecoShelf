package edu.arizona.cast.mollyalveshire.ecoshelf.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import edu.arizona.cast.mollyalveshire.ecoshelf.InventoryItem

@Database(entities = [InventoryItem::class], version = 2)  // Increment version to 2
abstract class InventoryDatabase : RoomDatabase() {
    abstract fun inventoryDao(): InventoryDao

    companion object {
        @Volatile
        private var INSTANCE: InventoryDatabase? = null

        // Define the migration object
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Example: Add a new column to the table
                database.execSQL("ALTER TABLE inventory_item ADD COLUMN new_column INTEGER DEFAULT 0 NOT NULL")
            }
        }

        fun getDatabase(context: Context): InventoryDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    InventoryDatabase::class.java,
                    "clothing_inventory"
                )
                    .addMigrations(MIGRATION_1_2) // Add migration here
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

//
//
//package edu.arizona.cast.mollyalveshire.ecoshelf.databaseM
//
//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//import edu.arizona.cast.mollyalveshire.ecoshelf.InventoryItem
//import edu.arizona.cast.mollyalveshire.ecoshelf.database.InventoryDao
//
//@Database(entities = [InventoryItem::class], version = 2)
//abstract class InventoryDatabase : RoomDatabase() {
//    abstract fun inventoryDao(): InventoryDao
//
//    companion object {
//        @Volatile
//        private var INSTANCE: InventoryDatabase? = null
//
//        fun getDatabase(context: Context): InventoryDatabase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    InventoryDatabase::class.java,
//                    "clothing_inventory"
//                ).build()
//                INSTANCE = instance
//                instance
//            }
//        }
//    }
//}
