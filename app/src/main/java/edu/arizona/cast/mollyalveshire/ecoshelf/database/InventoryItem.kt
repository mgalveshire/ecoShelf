package edu.arizona.cast.mollyalveshire.ecoshelf.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "inventory_items")
data class InventoryItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val fabric: String,
    val style: String,
    val pattern: String,
    val material: String,
    val color: String,
    val gender: String,
    val composition: String,
    val type: String,
    val season: String,
    val productCost: Double,
    val productPrice: Double,
    val unitsSold: Int,
    val unitsTotal: Int
)
