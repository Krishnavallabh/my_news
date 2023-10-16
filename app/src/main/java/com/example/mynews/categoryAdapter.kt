package com.example.mynews

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class categoryAdapter (val categories: ArrayList<String>, val lisner : onCategoryClick): RecyclerView.Adapter<categoryAdapter.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val category: TextView
        val categoryImage: ImageView

        init {

            // Define click listener for the ViewHolder's View
            category = view.findViewById(R.id.category)
            categoryImage = view.findViewById(R.id.categoryImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_category,parent,false)
        val viewHolder = ViewHolder(view)
        view.setOnClickListener {
            lisner.onCategoryClicked(categories[viewHolder.adapterPosition])
          //  Log.d("hiiii",categories[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.category.text = categories[position]
        var text = holder.category.text
        if(text == "Sports")
        { holder.categoryImage.setImageResource(R.drawable.sports)}
        else if(text == "Business")
        { holder.categoryImage.setImageResource(R.drawable.business)}
        else if(text == "Entertainment")
        { holder.categoryImage.setImageResource(R.drawable.entertainment)}
        else if(text == "Environment")
        { holder.categoryImage.setImageResource(R.drawable.environment)}
        else if(text == "Health")
        { holder.categoryImage.setImageResource(R.drawable.health)}
        else if(text == "Food")
        { holder.categoryImage.setImageResource(R.drawable.food)}
        else if(text == "Politics")
        { holder.categoryImage.setImageResource(R.drawable.politicsi)}
        else if(text == "science")
        { holder.categoryImage.setImageResource(R.drawable.science)}
        else if(text == "Technology")
        { holder.categoryImage.setImageResource(R.drawable.technology)}

    }

    override fun getItemCount(): Int {
      return categories.size
    }

}
interface onCategoryClick {
    fun onCategoryClicked(category: String)
}