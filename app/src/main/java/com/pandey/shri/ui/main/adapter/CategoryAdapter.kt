package com.pandey.shri.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pandey.shri.data.model.CategoryModel
import com.pandey.shri.databinding.ItemCategoryBinding
import java.util.*

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    var data = ArrayList<CategoryModel>()
    var itemListener: EventListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {


        val catBinding = ItemCategoryBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)


        return CategoryViewHolder(catBinding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val item = data[position]

        holder.catBinding.imgCategory.setImageResource(item.imageResourceId)

        holder.catBinding.imgCategory.setOnClickListener {
            itemListener?.onItemClick(data,holder,position)
        }
    }

    fun addData(categoryList: List<CategoryModel>?) {
        data.clear()
        categoryList?.let { data.addAll(it) }
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return data.size
    }

    inner class CategoryViewHolder(val catBinding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(catBinding.root) {
        init {
            setEventlistener(itemListener)
        }

    }


    interface EventListener {
        fun onItemClick(data: ArrayList<CategoryModel>, holder: CategoryViewHolder, position: Int)
    }
    fun setEventlistener(onItemClick: EventListener?) {
        itemListener = onItemClick
    }

}