package com.hhh.studentsapp.adapter

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.hhh.studentsapp.R
import com.hhh.studentsapp.model.Student
import java.util.zip.Inflater

class StudentAdapter: RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    inner class StudentViewHolder(view: View): RecyclerView.ViewHolder(view){
        val nameStudent = view.findViewById<TextView>(R.id.nameStudent)
        val groupStudent = view.findViewById<TextView>(R.id.groupStudent)
    }

    private val callback = object: DiffUtil.ItemCallback<Student>() {
        override fun areItemsTheSame(oldItem: Student, newItem: Student): Boolean {
            return oldItem.idStudent == newItem.idStudent
        }

        override fun areContentsTheSame(oldItem: Student, newItem: Student): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        return StudentViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false)
        )
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val currentStudent = differ.currentList[position]
        holder.nameStudent.text = "${currentStudent.lastName} ${currentStudent.firstName} ${currentStudent.patronymic}"
        holder.groupStudent.text = currentStudent.groupForStudent.toString()

        holder.itemView.apply {
            setOnClickListener{
                onItemClickListener?.let { it(currentStudent) }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Student) -> Unit)? = null

    fun setOnItemClickListener(listener: (Student) -> Unit) {
        onItemClickListener = listener
    }
}