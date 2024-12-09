package edu.arizona.cast.mollyalveshire.ecoshelf

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class EditItemFragment : Fragment() {
    private lateinit var editName: EditText
    private lateinit var editStyle: EditText
    private lateinit var editFabric: EditText
    private lateinit var editPattern: EditText
    private lateinit var editMaterial: EditText
    private lateinit var editColor: EditText
    private lateinit var editGender: EditText
    private lateinit var editComposition: EditText
    private lateinit var editType: EditText
    private lateinit var editSeason: EditText
    private lateinit var editProductCost: EditText
    private lateinit var editProductPrice: EditText
    private lateinit var editUnitsSold: EditText
    private lateinit var editUnitsTotal: EditText
    private lateinit var saveButton: Button

    private var itemId: Int = 0 // To store the item's ID
    private lateinit var inventoryViewModel: InventoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_item, container, false)
        editName = view.findViewById(R.id.edit_name)
        editStyle = view.findViewById(R.id.edit_style)
        editFabric = view.findViewById(R.id.edit_fabric)
        editPattern = view.findViewById(R.id.edit_pattern)
        editMaterial = view.findViewById(R.id.edit_material)
        editColor = view.findViewById(R.id.edit_color)
        editGender = view.findViewById(R.id.edit_gender)
        editComposition = view.findViewById(R.id.edit_composition)
        editType = view.findViewById(R.id.edit_type)
        editSeason = view.findViewById(R.id.edit_season)
        editProductCost = view.findViewById(R.id.edit_productCost)
        editProductPrice = view.findViewById(R.id.edit_productPrice)
        editUnitsSold = view.findViewById(R.id.edit_unitsSold)
        editUnitsTotal = view.findViewById(R.id.edit_unitsTotal)
        saveButton = view.findViewById(R.id.saveButton)
        inventoryViewModel = ViewModelProvider(this).get(InventoryViewModel::class.java)
        itemId = arguments?.getInt("itemId") ?: 0

        // Initialize the ViewModel (assuming you're using ViewModel for data)
        inventoryViewModel = ViewModelProvider(this).get(InventoryViewModel::class.java)

        inventoryViewModel.getInventoryItem(itemId).observe(viewLifecycleOwner, Observer { item ->
            if (item != null) {
                // Populate the fields with the existing item data
                editName.setText(item.name)
                editStyle.setText(item.style)
                editFabric.setText(item.fabric)
                editPattern.setText(item.pattern)
                editMaterial.setText(item.material)
                editColor.setText(item.color)
                editGender.setText(item.gender)
                editComposition.setText(item.composition)
                editType.setText(item.type)
                editSeason.setText(item.season)
                editProductCost.setText(item.productCost.toString())
                editProductPrice.setText(item.productPrice.toString())
                editUnitsSold.setText(item.unitsSold.toString())
                editUnitsTotal.setText(item.unitsTotal.toString())
            }
        })

        // Log or use the itemId as needed
        Log.d("EditItemFragment", "Item ID: $itemId")

        saveButton.setOnClickListener {
            // Get the updated values from the EditText fields
            val updatedName = editName.text.toString()
            val updatedStyle = editStyle.text.toString()
            val updatedFabric = editFabric.text.toString()
            val updatedPattern = editPattern.text.toString()
            val updatedMaterial = editMaterial.text.toString()
            val updatedColor = editColor.text.toString()
            val updatedGender = editGender.text.toString()
            val updatedComposition = editComposition.text.toString()
            val updatedType = editType.text.toString()
            val updatedSeason = editSeason.text.toString()
            val updatedProductCost = editProductCost.text.toString().toDouble()
            val updatedProductPrice = editProductPrice.text.toString().toDouble()
            val updatedUnitsSold = editUnitsSold.text.toString().toInt()
            val updatedUnitsTotal = editUnitsTotal.text.toString().toInt()
            val updatedItem = InventoryItem(
                id = itemId, // Preserve the same ID
                name = updatedName,
                fabric = updatedFabric,
                style = updatedStyle,
                pattern = updatedPattern,
                material = updatedMaterial,
                color = updatedColor,
                gender = updatedGender,
                composition = updatedComposition,
                type = updatedType,
                season = updatedSeason,
                productCost = updatedProductCost,
                productPrice = updatedProductPrice,
                unitsSold = updatedUnitsSold,
                unitsTotal = updatedUnitsTotal
            )
            Log.d("EditItemFragment", "YAY")
            inventoryViewModel.updateInventoryItem(updatedItem)
            Log.d("EditItemFragment", "GOOD")

            // Return to the previous fragment
            parentFragmentManager.popBackStack()
        }

        return view
    }
}




//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.EditText
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProvider
//
//
//class EditItemFragment : Fragment() {
//
//    private lateinit var saveButton: Button
//    private lateinit var editName: EditText
//    private lateinit var editStyle: EditText
//    private lateinit var editFabric: EditText
//    private lateinit var editPattern: EditText
//    private lateinit var editMaterial: EditText
//    private lateinit var editColor: EditText
//    private lateinit var editGender: EditText
//    private lateinit var editComposition: EditText
//    private lateinit var editType: EditText
//    private lateinit var editSeason: EditText
//    private lateinit var editProductCost: EditText
//    private lateinit var editProductPrice: EditText
//    private lateinit var editUnitsSold: EditText
//    private lateinit var editUnitsTotal: EditText
//
//    private var itemId: Long = 0 // To store the item's ID
//
//    private lateinit var inventoryViewModel: InventoryViewModel
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_edit_item, container, false)
//
//        // Initialize the UI elements
//        editName = view.findViewById(R.id.edit_name)
//        editStyle = view.findViewById(R.id.edit_style)
//        editFabric = view.findViewById(R.id.edit_fabric)
//        editPattern = view.findViewById(R.id.edit_pattern)
//        editMaterial = view.findViewById(R.id.edit_material)
//        editColor = view.findViewById(R.id.edit_color)
//        editGender = view.findViewById(R.id.edit_gender)
//        editComposition = view.findViewById(R.id.edit_composition)
//        editType = view.findViewById(R.id.edit_type)
//        editSeason = view.findViewById(R.id.edit_season)
//        editProductCost = view.findViewById(R.id.edit_productCost)
//        editProductPrice = view.findViewById(R.id.edit_productPrice)
//        editUnitsSold = view.findViewById(R.id.edit_unitsSold)
//        editUnitsTotal = view.findViewById(R.id.edit_unitsTotal)
//
//        saveButton = view.findViewById(R.id.saveButton)
//
//        // Retrieve the item ID passed from the previous fragment
//        itemId = arguments?.getLong("itemId") ?: 0
//
//        // Initialize the ViewModel (assuming you're using ViewModel for data)
//        inventoryViewModel = ViewModelProvider(this).get(InventoryViewModel::class.java)
//
//        // Fetch and display the current item data
//        inventoryViewModel.getInventoryItem(itemId).observe(viewLifecycleOwner, Observer { item ->
//            if (item != null) {
//                // Populate the fields with the existing item data
//                editName.setText(item.name)
//                editStyle.setText(item.style)
//                editFabric.setText(item.fabric)
//                editPattern.setText(item.pattern)
//                editMaterial.setText(item.material)
//                editColor.setText(item.color)
//                editGender.setText(item.gender)
//                editComposition.setText(item.composition)
//                editType.setText(item.type)
//                editSeason.setText(item.season)
//                editProductCost.setText(item.productCost.toString())
//                editProductPrice.setText(item.productPrice.toString())
//                editUnitsSold.setText(item.unitsSold.toString())
//                editUnitsTotal.setText(item.unitsTotal.toString())
//            }
//        })
//
//        // Set up the save button to save updated data
//        saveButton.setOnClickListener {
//            // Get the updated values from the EditText fields
//            val updatedName = editName.text.toString()
//            val updatedStyle = editStyle.text.toString()
//            val updatedFabric = editFabric.text.toString()
//            val updatedPattern = editPattern.text.toString()
//            val updatedMaterial = editMaterial.text.toString()
//            val updatedColor = editColor.text.toString()
//            val updatedGender = editGender.text.toString()
//            val updatedComposition = editComposition.text.toString()
//            val updatedType = editType.text.toString()
//            val updatedSeason = editSeason.text.toString()
//            val updatedProductCost = editProductCost.text.toString().toDouble()
//            val updatedProductPrice = editProductPrice.text.toString().toDouble()
//            val updatedUnitsSold = editUnitsSold.text.toString().toInt()
//            val updatedUnitsTotal = editUnitsTotal.text.toString().toInt()
//
//            // Create a new InventoryItem with the updated data
//            val updatedItem = InventoryItem(
//                id = itemId, // Preserve the same ID
//                name = updatedName,
//                fabric = updatedFabric,
//                style = updatedStyle,
//                pattern = updatedPattern,
//                material = updatedMaterial,
//                color = updatedColor,
//                gender = updatedGender,
//                composition = updatedComposition,
//                type = updatedType,
//                season = updatedSeason,
//                productCost = updatedProductCost,
//                productPrice = updatedProductPrice,
//                unitsSold = updatedUnitsSold,
//                unitsTotal = updatedUnitsTotal
//            )
//
//            // Update the item in the database
//            inventoryViewModel.updateInventoryItem(updatedItem)
//
//            // Return to the previous fragment
//            parentFragmentManager.popBackStack()
//        }
//
//        return view
//    }
//}
