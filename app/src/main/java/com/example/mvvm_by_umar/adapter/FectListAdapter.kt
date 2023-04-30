package com.example.mvvm_by_umar.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_by_umar.R
import com.example.mvvm_by_umar.model.FectModel

class FectListAdapter(
    private val context: Context,
    private var fectList: List<FectModel>,
    private val clickListener: ItemClickListener
) : RecyclerView.Adapter<FectListAdapter.MyViewHolder>() {

    fun setFectList(movieList: List<FectModel>) {
        this.fectList = movieList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycler_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val list: ArrayList<String> = arrayListOf()
        for (model in fectList) {
            list.add(model.text) /**add text to new list**/
        }
        holder.fect.setText(list[position]) /**set fect text to show**/


        holder.itemView.setOnClickListener {
            clickListener.onItemClick(fectList[position])
        }
    }

    override fun getItemCount(): Int {
        return this.fectList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fect: TextView = itemView.findViewById(R.id.facttext) /**get the text files where data will show**/

    }
    interface ItemClickListener {
        fun onItemClick(fects: FectModel)
    }
}
