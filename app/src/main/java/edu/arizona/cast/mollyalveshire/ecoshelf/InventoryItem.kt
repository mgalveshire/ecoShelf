package edu.arizona.cast.mollyalveshire.ecoshelf

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "inventory_item")
data class InventoryItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var name: String,
    val fabric: String,
    var style: String,
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
    var unitsTotal: Int
)