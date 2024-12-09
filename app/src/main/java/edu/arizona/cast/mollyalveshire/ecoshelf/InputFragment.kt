//
//package edu.arizona.cast.mollyalveshire.ecoshelf
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.EditText
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.ViewModelProvider
//
//class InputFragment : Fragment() {
//
//    // Declare EditText fields and the submit button
//    private lateinit var etName: EditText
//    private lateinit var etFabric: EditText
//    private lateinit var etStyle: EditText
//    private lateinit var etPattern: EditText
//    private lateinit var etMaterial: EditText
//    private lateinit var etColor: EditText
//    private lateinit var etGender: EditText
//    private lateinit var etComposition: EditText
//    private lateinit var etType: EditText
//    private lateinit var etSeason: EditText
//    private lateinit var etProductCost: EditText
//    private lateinit var etProductPrice: EditText
//    private lateinit var etUnitsSold: EditText
//    private lateinit var etUnitsTotal: EditText
//    private lateinit var btnSubmit: Button
//    private lateinit var inventoryViewModel: InventoryViewModel
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the fragment layout
//        val view = inflater.inflate(R.layout.fragment_input, container, false)
//
//        inventoryViewModel = ViewModelProvider(requireActivity()).get(InventoryViewModel::class.java)
//        // Initialize the EditText views
//        etName = view.findViewById(R.id.et_name)
//        etFabric = view.findViewById(R.id.et_fabric)
//        etStyle = view.findViewById(R.id.et_style)
//        etPattern = view.findViewById(R.id.et_pattern)
//        etMaterial = view.findViewById(R.id.et_material)
//        etColor = view.findViewById(R.id.et_color)
//        etGender = view.findViewById(R.id.spinner_gender)
//        etComposition = view.findViewById(R.id.et_composition)
//        etType = view.findViewById(R.id.et_type)
//        etSeason = view.findViewById(R.id.spinner_season)
//        etProductCost = view.findViewById(R.id.et_product_cost)
//        etProductPrice = view.findViewById(R.id.et_product_price)
//        etUnitsSold = view.findViewById(R.id.et_units_sold)
//        etUnitsTotal = view.findViewById(R.id.et_units_total)
//        btnSubmit = view.findViewById(R.id.btn_submit)
//
//        // Set onClick listener for the submit button
//        btnSubmit.setOnClickListener {
//            // Get data from EditText fields
//            val name = etName.text.toString()
//            val fabric = etFabric.text.toString()
//            val style = etStyle.text.toString()
//            val pattern = etPattern.text.toString()
//            val material = etMaterial.text.toString()
//            val color = etColor.text.toString()
//            val gender = etGender.text.toString()
//            val composition = etComposition.text.toString()
//            val type = etType.text.toString()
//            val season = etSeason.text.toString()
//            val productCost = etProductCost.text.toString().toDoubleOrNull() ?: 0.0
//            val productPrice = etProductPrice.text.toString().toDoubleOrNull() ?: 0.0
//            val unitsSold = etUnitsSold.text.toString().toIntOrNull() ?: 0
//            val unitsTotal = etUnitsTotal.text.toString().toIntOrNull() ?: 0
//
//            // Create InventoryItem object
//            val item = InventoryItem(
//                name = name,
//                fabric = fabric,
//                style = style,
//                pattern = pattern,
//                material = material,
//                color = color,
//                gender = gender,
//                composition = composition,
//                type = type,
//                season = season,
//                productCost = productCost,
//                productPrice = productPrice,
//                unitsSold = unitsSold,
//                unitsTotal = unitsTotal
//            )
//            inventoryViewModel.insertItem(item)
//
//            // Clear the EditText fields
//            etName.text.clear()
//            etFabric.text.clear()
//            etStyle.text.clear()
//            etPattern.text.clear()
//            etMaterial.text.clear()
//            etColor.text.clear()
//            etGender.text.clear()
//            etComposition.text.clear()
//            etType.text.clear()
//            etSeason.text.clear()
//            etProductCost.text.clear()
//            etProductPrice.text.clear()
//            etUnitsSold.text.clear()
//            etUnitsTotal.text.clear()
//        }
//
//        return view
//    }
//}



package edu.arizona.cast.mollyalveshire.ecoshelf

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class InputFragment : Fragment() {

    // Declare EditText fields and the submit button
    private lateinit var etName: EditText
    private lateinit var etFabric: EditText
    private lateinit var etStyle: EditText
    private lateinit var etPattern: EditText
    private lateinit var etMaterial: EditText
    private lateinit var etColor: EditText
    private lateinit var etGender: EditText
    private lateinit var etComposition: EditText
    private lateinit var etType: EditText
    private lateinit var etSeason: EditText
    private lateinit var etProductCost: EditText
    private lateinit var etProductPrice: EditText
    private lateinit var etUnitsSold: EditText
    private lateinit var etUnitsTotal: EditText
    private lateinit var btnSubmit: Button
    private lateinit var inventoryViewModel: InventoryViewModel
    private lateinit var errorMessage: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the fragment layout
        val view = inflater.inflate(R.layout.fragment_input, container, false)
        inventoryViewModel = ViewModelProvider(requireActivity()).get(InventoryViewModel::class.java)

        // Initialize the EditText views
        etName = view.findViewById(R.id.et_name)
        etFabric = view.findViewById(R.id.et_fabric)
        etStyle = view.findViewById(R.id.et_style)
        etPattern = view.findViewById(R.id.et_pattern)
        etMaterial = view.findViewById(R.id.et_material)
        etColor = view.findViewById(R.id.et_color)
        etGender = view.findViewById(R.id.spinner_gender)
        etComposition = view.findViewById(R.id.et_composition)
        etType = view.findViewById(R.id.et_type)
        etSeason = view.findViewById(R.id.spinner_season)
        etProductCost = view.findViewById(R.id.et_product_cost)
        etProductPrice = view.findViewById(R.id.et_product_price)
        etUnitsSold = view.findViewById(R.id.et_units_sold)
        etUnitsTotal = view.findViewById(R.id.et_units_total)
        btnSubmit = view.findViewById(R.id.btn_submit)
        errorMessage = view.findViewById(R.id.error_message)
        //totalItems = view.findViewById(R.id.total_items_home)

        // Set onClick listener for the submit button
        btnSubmit.setOnClickListener {
            // Get data from EditText fields
            val name = etName.text.toString()
            val fabric = etFabric.text.toString()
            val style = etStyle.text.toString()
            val pattern = etPattern.text.toString()
            val material = etMaterial.text.toString()
            val color = etColor.text.toString()
            val gender = etGender.text.toString()
            val composition = etComposition.text.toString()
            val type = etType.text.toString()
            val season = etSeason.text.toString()
            val productCost = etProductCost.text.toString().toDoubleOrNull() ?: 0.0
            val productPrice = etProductPrice.text.toString().toDoubleOrNull() ?: 0.0
            val unitsSold = etUnitsSold.text.toString().toIntOrNull() ?: 0
            val unitsTotal = etUnitsTotal.text.toString().toIntOrNull() ?: 0
            Log.d("InputFragment", "Name: $name, Fabric: $fabric, Cost: $productCost")
            // Create InventoryItem object
            val item = InventoryItem(
                name = name,
                fabric = fabric,
                style = style,
                pattern = pattern,
                material = material,
                color = color,
                gender = gender,
                composition = composition,
                type = type,
                season = season,
                productCost = productCost,
                productPrice = productPrice,
                unitsSold = unitsSold,
                unitsTotal = unitsTotal
            )
            if (name.isNotEmpty()){
                inventoryViewModel.insertItem(item)
            }
            else {
                errorMessage.setText("No input for name of product.")
            }

            etName.text.clear()
            etFabric.text.clear()
            etStyle.text.clear()
            etPattern.text.clear()
            etMaterial.text.clear()
            etColor.text.clear()
            etGender.text.clear()
            etComposition.text.clear()
            etType.text.clear()
            etSeason.text.clear()
            etProductCost.text.clear()
            etProductPrice.text.clear()
            etUnitsSold.text.clear()
            etUnitsTotal.text.clear()
        }

        inventoryViewModel.getAllItems().observe(viewLifecycleOwner, Observer { items ->
            if (items.isNotEmpty()) {
                Log.d("InventoryActivity", "Items in database: $items")
            } else {
                Log.d("InventoryActivity", "No items in database.")
            }
        })



        return view
    }
}
