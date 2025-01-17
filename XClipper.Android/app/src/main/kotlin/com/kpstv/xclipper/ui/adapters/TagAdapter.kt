package com.kpstv.xclipper.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kpstv.xclipper.R
import com.kpstv.xclipper.data.localized.DialogState
import com.kpstv.xclipper.data.model.ClipTag
import com.kpstv.xclipper.data.model.Tag
import com.kpstv.xclipper.data.model.TagMap
import kotlinx.android.synthetic.main.item_tag_chip.view.*

class TagAdapter(
    private val lifecycleOwner: LifecycleOwner,
    private val dialogState: LiveData<DialogState>,
    private val tagFilter: LiveData<ArrayList<Tag>>,
    private val tagMapData: LiveData<List<TagMap>>,
    private val onCloseClick: (Tag, count: Int, index: Int) -> Unit,
    private val onClick: (Tag, Int) -> Unit
) : ListAdapter<Tag, TagAdapter.TagHolder>(DiffCallback()) {

    private val TAG = javaClass.simpleName

    class DiffCallback : DiffUtil.ItemCallback<Tag>() {
        override fun areItemsTheSame(oldItem: Tag, newItem: Tag): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Tag, newItem: Tag): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagHolder =
        TagHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_tag_chip, parent, false)
        )

    override fun onBindViewHolder(holder: TagHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    @SuppressLint("SetTextI18n")
    private fun TagHolder.bind(tag: Tag) = with(itemView) {
        chip.text = tag.name

        dialogState.observe(lifecycleOwner) {
            if (it == DialogState.Edit) {
                chip.isCloseIconVisible = ClipTag.fromValue(tag.name) == null
            }
            else if (it == DialogState.Normal)
                chip.isCloseIconVisible = false
        }

        tagMapData.observe(lifecycleOwner) { list ->
            val find = list.find { it.name == tag.name }
            if (find?.count != null) {
                chip.text = "${tag.name} (${find.count})"
                chip.tag = find.count
            }
        }

        tagFilter.observe(lifecycleOwner) { list ->
            chip.isChipIconVisible = list.any { it.name == tag.name }
        }

        chip.setOnCloseIconClickListener { v ->
            onCloseClick.invoke(tag, v.tag as? Int ?: 0, layoutPosition)
        }
        chip.setOnClickListener { onClick.invoke(tag, layoutPosition) }
    }

    class TagHolder(view: View) : RecyclerView.ViewHolder(view)
}