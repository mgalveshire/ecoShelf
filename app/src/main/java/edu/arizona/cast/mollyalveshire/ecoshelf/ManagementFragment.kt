package edu.arizona.cast.mollyalveshire.ecoshelf

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ManagementFragment : Fragment() {
    private lateinit var inventoryViewModel: InventoryViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: InventoryAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_management, container, false)

        inventoryViewModel = ViewModelProvider(requireActivity()).get(InventoryViewModel::class.java)

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Observe the list of items and update the RecyclerView when data changes
        inventoryViewModel.getAllItems().observe(viewLifecycleOwner, Observer { items ->
            if (items != null) {
                // Update the adapter with the list of items
                val mutableList = items.toMutableList()
                val fragmentManager: FragmentManager
                val adapter = InventoryAdapter(mutableList, inventoryViewModel)
                recyclerView.adapter = adapter
                recyclerView.adapter = adapter
            }
        })

        return view
    }



//    private lateinit var inventoryViewModel: InventoryViewModel
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_management, container, false)
//        inventoryViewModel = ViewModelProvider(requireActivity()).get(InventoryViewModel::class.java)
//
//        return view
//    }
}