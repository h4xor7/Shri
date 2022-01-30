package com.pandey.shri.ui.main.adapter

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pandey.shri.R
import com.pandey.shri.data.model.Entry
import com.pandey.shri.databinding.ItemExpenseBinding
import com.pandey.shri.utils.Utils
import java.time.format.DateTimeFormatter
import java.util.*

class NewDetailAdapter : RecyclerView.Adapter<NewDetailAdapter.DetailViewHolder>() {
    private var listData = emptyList<Entry>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val expenseBinding =
            ItemExpenseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailViewHolder(expenseBinding)
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {


        val currentItemDate:String = DateFormat.format("dd-MMM-yyyy", Date(listData[position].date)).toString()
        if (position ==0) {
            holder.expenseBinding.txtDateText.visibility = View.VISIBLE
            holder.expenseBinding.txtDateText.text = currentItemDate.toString()

        } else {

            val previousItemDate = DateFormat.format("dd-MMM-yyyy", Date(listData[position-1].date)).toString()

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

    internal fun setExpense(entry: List<Entry>) {
        this.listData = entry
        notifyDataSetChanged()

    }

    inner class DetailViewHolder(val expenseBinding: ItemExpenseBinding) :
        RecyclerView.ViewHolder(expenseBinding.root)




}