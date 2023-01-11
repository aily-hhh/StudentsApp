package com.hhh.studentsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hhh.studentsapp.R
import com.hhh.studentsapp.model.Group

class GroupAdapter: RecyclerView.Adapter<GroupAdapter.GroupViewHolder>() {

    inner class GroupViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val nameGroup = view.findViewById<TextView>(R.id.nameGroup)
        val faculty = view.findViewById<TextView>(R.id.faculty)
    }

    private val callback = object: DiffUtil.ItemCallback<Group>() {
        override fun areItemsTheSame(oldItem: Group, newItem: Group): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Group, newItem: Group): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        return GroupViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_group, parent, false)
        )
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val currentGroup = differ.currentList[position]
        holder.nameGroup.text = currentGroup.nameGroup
        holder.faculty.text = currentGroup.faculty

        holder.itemView.apply {
            setOnClickListener {
                onItemClickListener?.let { it(currentGroup) }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Group) -> Unit)? = null

    fun setOnItemClickListener(listener: (Group) -> Unit) {
        onItemClickListener = listener
    }
}