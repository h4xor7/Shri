package com.pandey.shri.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pandey.shri.databinding.ItemMonthBinding

class MonthAdapter : RecyclerView.Adapter<MonthAdapter.MonthViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthViewHolder {
        val monthBinding =
            ItemMonthBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MonthViewHolder(monthBinding)
    }

    override fun onBindViewHolder(holder: MonthViewHolder, position: Int) {
      //  TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return 12
    }

    inner class MonthViewHolder(private val monthBinding: ItemMonthBinding) :
        RecyclerView.ViewHolder(monthBinding.root)

}