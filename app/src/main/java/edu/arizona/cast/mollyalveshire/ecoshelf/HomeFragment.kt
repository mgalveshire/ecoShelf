//package edu.arizona.cast.mollyalveshire.ecoshelf
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.activityViewModels
//
//class HomeFragment : Fragment() {
//
//    private lateinit var totalItemsTextView: TextView
//    private val sharedViewModel: SharedViewModel by activityViewModels() // Access shared ViewModel
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_home, container, false)
//
//        totalItemsTextView = view.findViewById(R.id.total_items_home)
//
//        // Observe the LiveData from SharedViewModel
//        sharedViewModel.itemCount.observe(viewLifecycleOwner) { count ->
//            // Update the UI with the new count
//            totalItemsTextView.text = "Total Items: $count"
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
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class HomeFragment : Fragment() {
    private lateinit var inventoryViewModel: InventoryViewModel
    private lateinit var  totalItems: TextView
    private lateinit var  excessItems: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        totalItems = view.findViewById(R.id.total_items_home)
        excessItems = view.findViewById(R.id.excess_items_home)
        inventoryViewModel = ViewModelProvider(requireActivity()).get(InventoryViewModel::class.java)
        // To get the total count of items
        inventoryViewModel.getTotalItemCount().observe(viewLifecycleOwner, Observer { count ->
            if (count != null) {
                Log.d("InventoryActivity", "Total number of items in database: $count")
                // Update your UI with the count
                totalItems.text = "Total items: $count"
            } else {
                Log.d("InventoryActivity", "Failed to get total item count.")
            }
        })
        // To get the count of excess items
        inventoryViewModel.getExcessItemsCount().observe(viewLifecycleOwner, Observer { excessCount ->
            if (excessCount != null) {
                Log.d("InventoryActivity", "Total excess items: $excessCount")
                // Update your UI with the excess count
                excessItems.text = "Excess items: $excessCount"
            } else {
                Log.d("InventoryActivity", "Failed to calculate excess items.")
            }
        })
        return view
    }
}
