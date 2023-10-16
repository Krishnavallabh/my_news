package com.example.mynews

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class newsAdapter(private val inter : newsClicked) :
    RecyclerView.Adapter<newsAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    private val dataSet = ArrayList<news>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView
        val author: TextView
        val imageView: ImageView

        init {

            // Define click listener for the ViewHolder's View
            title = view.findViewById(R.id.title)
            author = view.findViewById(R.id.author)
            imageView = view.findViewById(R.id.imageView)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_news, viewGroup, false)
        val viewHolder = ViewHolder(view)
        view.setOnClickListener {
        inter.onNewsClicked(dataSet[viewHolder.adapterPosition])
        }


        return viewHolder
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.title.text = dataSet[position].title
        viewHolder.author.text = dataSet[position].author
        Glide.with(viewHolder.itemView).load(dataSet[position].imgToUrl).error(R.drawable.image_not_available).into(viewHolder.imageView)


    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    // for update news
    fun updateNews(updatedNews: ArrayList<news>) {

        dataSet.clear()
        dataSet.addAll(updatedNews)
        notifyDataSetChanged()

    }

}
interface newsClicked {
    fun onNewsClicked(news : news)

}