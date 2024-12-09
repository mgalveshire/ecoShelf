package edu.arizona.cast.mollyalveshire.ecoshelf

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StoreAdapter(private val storeList: List<Store>) : RecyclerView.Adapter<StoreAdapter.StoreViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.store_item, parent, false)
        return StoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        val store = storeList[position]
        holder.bind(store)
    }

    override fun getItemCount(): Int = storeList.size

    inner class StoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val storeName: TextView = itemView.findViewById(R.id.storeName)
        private val storeAddress: TextView = itemView.findViewById(R.id.storeAddress)
        private val storeIcon: ImageView = itemView.findViewById(R.id.storeIcon)

        fun bind(store: Store) {
            storeName.text = store.name
            storeAddress.text = store.address
        }
    }
}
