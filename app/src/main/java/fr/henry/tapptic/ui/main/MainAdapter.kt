package fr.henry.tapptic.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.henry.tapptic.R
import fr.henry.tapptic.data.Numbers
import fr.henry.tapptic.ui.details.DetailsActivity
import fr.henry.tapptic.ui.details.DetailsFragment
import java.lang.StringBuilder

class MainAdapter(private val parentActivity: MainActivity,
                  private val values: List<Numbers>,
                  private val twoPane: Boolean,private val itemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    var selectedItem =""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.name.text = item.name
        Glide.with(parentActivity).load(item.image).into(holder.image)
        holder.container.isSelected=false

        if(twoPane && item.name==selectedItem)
            holder.container.isSelected=true

        holder.bind(values[position],itemClickListener)

        with(holder.itemView) {
            tag = item
        }
    }

    override fun getItemCount() = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.list_number_name)
        val image: ImageView = view.findViewById(R.id.list_number_image)
        val container : LinearLayout = view.findViewById((R.id.recycler_row))

        fun bind(number: Numbers,clickListener: OnItemClickListener)
        {
            itemView.setOnClickListener {
                clickListener.onItemClicked(number)
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClicked(number: Numbers)
    }


}