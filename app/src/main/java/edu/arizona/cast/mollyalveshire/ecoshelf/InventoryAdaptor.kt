package edu.arizona.cast.mollyalveshire.ecoshelf


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView

class InventoryAdapter(
    private var itemList: MutableList<InventoryItem>,
    private val inventoryViewModel: InventoryViewModel
) : RecyclerView.Adapter<InventoryAdapter.InventoryViewHolder>() {

    class InventoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.tv_name)
        val styleTextView: TextView = view.findViewById(R.id.tv_style)
        val unitsTextView: TextView = view.findViewById(R.id.tv_units)
        val editButton: Button = view.findViewById(R.id.edit_button) // Reference to delete button
        val deleteButton: Button = view.findViewById(R.id.delete_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_inventory, parent, false)
        return InventoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: InventoryViewHolder, position: Int) {
        val item = itemList[position]
        holder.nameTextView.text = item.name
        holder.styleTextView.text = item.style
        holder.unitsTextView.text = "${item.unitsTotal}"

        // Set up the delete button click listener

        holder.deleteButton.setOnClickListener {
            // Call the ViewModel function to delete the item
            inventoryViewModel.deleteItem(item)
            itemList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemList.size)
        }
        holder.editButton.setOnClickListener {
            val fragmentManager = holder.itemView.context as? FragmentActivity
            fragmentManager?.let {
                // Create an instance of the EditItemFragment
                val confirmationFragment = EditItemFragment()

                // Create a bundle and pass the item's ID
                val bundle = Bundle()
                bundle.putInt("itemId", item.id) // Assuming 'id' is the item's ID
                confirmationFragment.arguments = bundle

                // Replace the current fragment with the EditItemFragment
                it.supportFragmentManager.beginTransaction()
                    .replace(R.id.content_frame, confirmationFragment) // R.id.content_frame is the container holding the fragments
                    .addToBackStack(null) // This allows the user to navigate back to the previous fragment
                    .commit()
            }


//            val fragmentManager = holder.itemView.context as? FragmentActivity
//            fragmentManager?.let {
//                // Create an instance of the ConfirmationFragment
//                val confirmationFragment =EditItemFragment()
//
//                // Replace the current fragment with the confirmation fragment
//                it.supportFragmentManager.beginTransaction()
//                    .replace(R.id.content_frame, confirmationFragment) // R.id.fragment_container is the container holding the fragments
//                    .addToBackStack(null) // This allows the user to navigate back to the previous fragment
//                    .commit()
//            }
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}



//package edu.arizona.cast.mollyalveshire.ecoshelf
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//
//class InventoryAdapter(
//    private var itemList: MutableList<InventoryItem>,
//    private val inventoryViewModel: InventoryViewModel // Pass the ViewModel
//) : RecyclerView.Adapter<InventoryAdapter.InventoryViewHolder>() {
//
//    class InventoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val nameTextView: TextView = view.findViewById(R.id.tv_name)
//        val styleTextView: TextView = view.findViewById(R.id.tv_style)
//        val unitsTextView: TextView = view.findViewById(R.id.tv_units)
//        val deleteButton: Button = view.findViewById(R.id.delete_button) // Reference to delete button
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoryViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_inventory, parent, false)
//        return InventoryViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: InventoryViewHolder, position: Int) {
//
//            inventoryViewModel.deleteItem(item)
//            itemList.removeAt(position)
//            notifyItemRemoved(position)
//            notifyItemRangeChanged(position, itemList.size)
//
//        }
//
//    override fun getItemCount(): Int {
//        return itemList.size
//    }
//}
