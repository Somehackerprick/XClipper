package com.kpstv.xclipper.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kpstv.xclipper.databinding.ItemSingleBinding

class MoreChooserAdapter(
    private val items: List<String>,
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<MoreChooserAdapter.MoreChooserHolder>() {

    class MoreChooserHolder(val binding: ItemSingleBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoreChooserHolder {
        return MoreChooserHolder(
            ItemSingleBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MoreChooserHolder, position: Int) {
        holder.binding.title.text = (items[position])
        holder.binding.root.setOnClickListener { onClick.invoke(items[position]) }
    }

    override fun getItemCount(): Int = items.size
}