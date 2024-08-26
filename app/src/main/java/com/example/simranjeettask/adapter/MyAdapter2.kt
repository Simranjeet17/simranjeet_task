package com.example.demotask.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.simranjeettask.R
import com.example.simranjeettask.databinding.ItemLayoutBinding
import com.example.simranjeettask.model.Restaurant
import com.example.simranjeettask.ui.DashboardFragment

class MyAdapter2(private val context: Context, var restaurants: ArrayList<Restaurant>, val listener: DashboardFragment) :
    RecyclerView.Adapter<MyAdapter2.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemLayoutBinding.bind(itemView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(restaurants[position].image_url).into(holder.binding.ivImage)
        holder.binding.tvItemName.text= restaurants[position].name

        holder.binding.llMain.setOnClickListener{
            listener.onItemClick(position,restaurants[position])
        }





    }


    override fun getItemCount(): Int {
        return restaurants.size
    }

    interface OnItemClicked{
        fun onItemClick2(position: Int, restaurant: Restaurant)
    }
    fun setData(filteredList: List<Restaurant>) {
        this.restaurants= filteredList as ArrayList<Restaurant>
        notifyDataSetChanged()
    }

}
