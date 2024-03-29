package com.pandey.shri.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pandey.shri.R
import com.pandey.shri.data.model.Entry
import com.pandey.shri.databinding.ItemExpenseBinding
import com.pandey.shri.utils.Utils
import java.time.format.DateTimeFormatter

class NewDetailAdapter : RecyclerView.Adapter<NewDetailAdapter.DetailViewHolder>() {
    private var listData = emptyList<Entry>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val expenseBinding =
            ItemExpenseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailViewHolder(expenseBinding)
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        val fmt: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy")
        val currentItemDate = fmt.format(listData[position].date)
        if (position ==0) {
            holder.expenseBinding.txtDateText.visibility = View.VISIBLE
            holder.expenseBinding.txtDateText.text = currentItemDate.toString()

        } else {

            val previousItemDate = fmt.format(listData[position - 1].date)

            if (currentItemDate == previousItemDate) {
                holder.expenseBinding.txtDateText.visibility = View.GONE
            } else {
                holder.expenseBinding.txtDateText.visibility = View.VISIBLE
                holder.expenseBinding.txtDateText.text = currentItemDate.toString()
            }
        }

        holder.expenseBinding.txtItemName.text = listData[position].itemName.toString()
        holder.expenseBinding.txtExpenseValue.text = listData[position].itemPrice.toString()
        holder.expenseBinding.imgCategory.setImageResource(Utils.setCategoryImage(listData[position].category))




    }

    override fun getItemCount(): Int {
        return listData.size
    }

    fun getExpenseAtPosition(position: Int): Entry {
        return listData[position]
    }


    internal fun setExpense(entry: List<Entry>) {
        this.listData = entry
        notifyDataSetChanged()

    }

    inner class DetailViewHolder(val expenseBinding: ItemExpenseBinding) :
        RecyclerView.ViewHolder(expenseBinding.root)




}