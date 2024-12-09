package edu.arizona.cast.mollyalveshire.ecoshelf

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class StoreListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var storeAdapter: StoreAdapter
    private lateinit var storeList: List<Store>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the fragment layout
        val rootView = inflater.inflate(R.layout.fragment_options, container, false)

        // Initialize RecyclerView
        recyclerView = rootView.findViewById(R.id.storeRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Sample data
        storeList = listOf(
            Store("Store 1", "123 Main St"),
            Store("Store 2", "456 Oak St"),
            Store("Store 3", "789 Pine St")
        )

        // Set adapter
        storeAdapter = StoreAdapter(storeList)
        recyclerView.adapter = storeAdapter

        return rootView
    }
}
