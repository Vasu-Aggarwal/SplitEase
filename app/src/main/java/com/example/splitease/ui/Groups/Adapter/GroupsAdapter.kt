package com.example.splitease.ui.Groups.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.splitease.Models.UserDataModel
import com.example.splitease.R

class GroupsAdapter(var userItemModel: MutableList<UserDataModel>, var mClickListener: ItemClickListener)
    : RecyclerView.Adapter<GroupsAdapter.ViewHolder>() {
    private lateinit var context: Context

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val groupHead = itemView.findViewById<TextView>(R.id.groupHead)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.adapter_groups, parent, false)
        return ViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.groupHead.text = userItemModel[position].grp_name

        holder.itemView.setOnClickListener {
            mClickListener.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return userItemModel.size
    }

    fun updateList(temp: MutableList<UserDataModel>) {
        userItemModel = temp
        notifyDataSetChanged()
    }

    interface ItemClickListener{
        fun onItemClick(position: Int)
    }
}