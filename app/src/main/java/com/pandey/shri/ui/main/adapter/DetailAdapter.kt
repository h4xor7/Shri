package com.pandey.shri.ui.main.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pandey.shri.R
import com.pandey.shri.data.model.Entry
import com.pandey.shri.databinding.ItemDateBinding
import com.pandey.shri.databinding.ItemShopBinding

class DetailAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private var listData = emptyList<Entry>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

            return if (viewType == R.layout.item_date) {

                val dateBinding =
                    ItemDateBinding.inflate(LayoutInflater.from(parent.context), parent, false)

                DateViewHolder(dateBinding)

            } else {

                val shopBinding =
                    ItemShopBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                DetailViewHolder(shopBinding)
            }

     /*   val shopBinding =
            ItemShopBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailViewHolder(shopBinding)
*/

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    //   (holder as DetailViewHolder ).shopBinding.txtItemName.text = listData[position].itemName.toString()

    }

    override fun getItemCount(): Int {

        return listData.size
    }

    fun addData(detailList: List<Entry>) {

            this.listData =detailList
            notifyDataSetChanged()
    }


    override fun getItemViewType(position: Int): Int {

        return when {
            position % 3 == 0 -> R.layout.item_date
            else -> R.layout.item_category
        }

    }

    inner class DetailViewHolder( val shopBinding: ItemShopBinding) :
        RecyclerView.ViewHolder(shopBinding.root)

    inner class DateViewHolder(private val dateBinding: ItemDateBinding) :
        RecyclerView.ViewHolder(dateBinding.root)

    companion object {
        const val VIEW_TYPE_ONE = 1
        const val VIEW_TYPE_TWO = 2
        private const val TAG = "DetailAdapter"
    }
}