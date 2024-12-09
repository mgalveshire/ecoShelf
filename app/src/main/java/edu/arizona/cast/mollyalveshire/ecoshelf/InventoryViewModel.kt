package edu.arizona.cast.mollyalveshire.ecoshelf

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import edu.arizona.cast.mollyalveshire.ecoshelf.database.InventoryDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class InventoryViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: InventoryRepository
    private val inventoryDao = InventoryDatabase.getDatabase(application).inventoryDao()
    init {
        //val inventoryDao = InventoryDatabase.getDatabase(application).inventoryDao()
        repository = InventoryRepository(inventoryDao)
    }

    // Update an inventory item in the database
    fun updateInventoryItem(item: InventoryItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateItem(item)
        }
    }
    fun getInventoryItem(id: Int): LiveData<InventoryItem> {
        return repository.getInventoryItemById(id)
    }

    fun insertItem(item: InventoryItem) {
        viewModelScope.launch(Dispatchers.IO) {
           repository.insertItem(item)
        }
    }
    // Get all items from the database
    fun getAllItems(): LiveData<List<InventoryItem>> {
        val result = MutableLiveData<List<InventoryItem>>()
        viewModelScope.launch {
            result.postValue(inventoryDao.getAllItems())
        }
        return result
    }


    // Get the total count of items in the database
    fun getTotalItemCount(): LiveData<Int> {
        val result = MutableLiveData<Int>()
        viewModelScope.launch {
            result.postValue(inventoryDao.getTotalItemCount())
        }
        return result
    }
    fun deleteItem(item: InventoryItem) {
        viewModelScope.launch {
            try {
                inventoryDao.deleteItem(item)
            } catch (e: Exception) {
                Log.e("InventoryViewModel", "Error deleting item", e)
            }
        }
    }
    fun updateItem(item: InventoryItem) {
        viewModelScope.launch {
            try {
                inventoryDao.updateItem(item)  // Assuming you have an update function in your DAO
            } catch (e: Exception) {
                Log.e("InventoryViewModel", "Error updating item", e)
            }
        }
    }

    fun getExcessItemsCount(): LiveData<Int> {
        val result = MutableLiveData<Int>()
        viewModelScope.launch {
            // Get all the items from the database
            val allItems = inventoryDao.getAllItems()
            var excessCount = 0

            // Loop through each item and calculate the Inventory Turnover Ratio
            for (item in allItems) {
                val ratio = if (item.unitsSold != 0) {
                    item.unitsSold.toDouble() / item.unitsTotal.toDouble()
                } else {
                    0.0
                }

                // If the ratio is less than 0.2, it's considered excess
                if (ratio < 0.2) {
                    excessCount++
                }
            }

            // Return the excess count
            result.postValue(excessCount)
        }
        return result
    }

    fun calculateWastePotential(): LiveData<Map<String, Double>> {
        val result = MutableLiveData<Map<String, Double>>()
        viewModelScope.launch {
            val allItems = inventoryDao.getAllItems()
            var totalWastePotential = 0.0
            var itemCount = 0

            // Loop through all items and calculate the Excess Inventory Percentage
            for (item in allItems) {
                if (item.unitsTotal > 0) {
                    val excessInventoryPercentage = ((item.unitsTotal - item.unitsSold).toDouble() / item.unitsTotal.toDouble()) * 100
                    totalWastePotential += excessInventoryPercentage
                    itemCount++
                }
            }

            // Calculate the average waste potential if there are any items
            val averageWastePotential = if (itemCount > 0) totalWastePotential / itemCount else 0.0

            // Prepare the result as a Map
            val resultMap = mapOf(
                "averageWastePotential" to averageWastePotential,
                "remainingPotential" to (100 - averageWastePotential)
            )

            // Post the result as LiveData
            result.postValue(resultMap)
        }
        return result
    }

    fun calculateUnsoldInventoryCost(): LiveData<Map<String, Double>> {
        val result = MutableLiveData<Map<String, Double>>()
        viewModelScope.launch {
            val allItems = inventoryDao.getAllItems()
            var totalUnsoldInventoryCost = 0.0
            var itemCount = 0

            // Loop through all items and calculate the Unsold Inventory Value
            for (item in allItems) {
                if (item.unitsTotal > 0) {
                    val unsoldInventoryCost = ((item.unitsTotal - item.unitsSold).toDouble()) * item.productCost
                    totalUnsoldInventoryCost += unsoldInventoryCost
                    itemCount++
                }
            }

            // Calculate the remaining inventory cost if there are any items
            val totalInventoryValue = allItems.sumOf { it.productCost * it.unitsTotal }
            val remainingInventoryCost = totalInventoryValue - totalUnsoldInventoryCost

            // Return both values in a map
            result.postValue(mapOf("unsoldInventoryCost" to totalUnsoldInventoryCost, "remainingInventoryCost" to remainingInventoryCost))
        }
        return result
    }
    fun calculateSalesEfficiency(): LiveData<List<Pair<Float, Float>>> {
        val result = MutableLiveData<List<Pair<Float, Float>>>()

        viewModelScope.launch {
            val allItems = inventoryDao.getAllItems()
            val scatterData: MutableList<Pair<Float, Float>> = mutableListOf()

            // Loop through all items and calculate the Sales Efficiency and Remaining Stock
            for (item in allItems) {
                if (item.unitsTotal > 0) {
                    val salesEfficiency = (item.unitsSold.toFloat() / item.unitsTotal.toFloat()) * 100
                    val remainingStock = (item.unitsTotal - item.unitsSold).toFloat()

                    // Add the sales efficiency and remaining stock as a pair to the list
                    scatterData.add(Pair(salesEfficiency, remainingStock))
                }
            }

            // Post the result as LiveData
            result.postValue(scatterData)
        }

        return result
    }
    fun calculateExcessProfitability(): LiveData<List<Double>> {
        val result = MutableLiveData<List<Double>>()

        viewModelScope.launch {
            val allItems = inventoryDao.getAllItems()  // Fetch all inventory items
            val excessProfitabilities: MutableList<Double> = mutableListOf()

            // Loop through all items and calculate Excess Profitability for overstocked items
            for (item in allItems) {
                if (item.unitsTotal > item.unitsSold) {
                    // Calculate Excess Profitability for overstocked items
                    val excessProfitability = (item.productPrice - item.productCost) *
                            (item.unitsTotal - item.unitsSold)
                    excessProfitabilities.add(excessProfitability)
                }
            }

            // Post the result as LiveData
            result.postValue(excessProfitabilities)
        }

        return result
    }

    fun calculateAverageProductCost(): LiveData<Double> {
        val result = MutableLiveData<Double>()

        viewModelScope.launch {
            val allItems = inventoryDao.getAllItems()  // Fetch all inventory items
            var totalCost = 0.0
            var totalItems = 0

            // Loop through all items and sum up the product cost
            for (item in allItems) {
                totalCost += item.productCost
                totalItems++
            }

            // Calculate the average cost
            val averageCost = if (totalItems > 0) {
                totalCost / totalItems
            } else {
                0.0  // Avoid division by zero if there are no items
            }

            // Post the result as LiveData
            result.postValue(averageCost)
        }

        return result
    }
}