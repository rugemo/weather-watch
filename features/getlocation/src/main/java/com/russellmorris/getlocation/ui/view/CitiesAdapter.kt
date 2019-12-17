package com.russellmorris.getlocation.ui.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.russellmorris.extensions.inflate
import com.russellmorris.getlocation.R
import com.russellmorris.getlocation.ui.model.City
import kotlinx.android.synthetic.main.item_launch.view.*

class CitiesAdapter constructor(private val itemClick: (City) -> Unit) :
    ListAdapter<City, CitiesAdapter.ViewHolder>(LaunchDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class ViewHolder(parent: ViewGroup) :
        RecyclerView.ViewHolder(parent.inflate(R.layout.item_launch)) {

        fun bind(item: City) {
            itemView.cityName.text = itemView.context.getString(R.string.city_country, item.name, item.countryCode)
            itemView.icon.setImageDrawable(getIconDrawable(item.icon, itemView.context))
            itemView.setOnClickListener { itemClick.invoke(item) }
        }
    }
}

private class LaunchDiffCallback : DiffUtil.ItemCallback<City>() {
    override fun areItemsTheSame(oldItem: City, newItem: City): Boolean =
        oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: City, newItem: City): Boolean =
        oldItem == newItem
}

private fun getIconDrawable(iconName: String, context: Context): Drawable? {
    val drawableName = "ic_$iconName"
    val resourceId: Int = context.resources.getIdentifier(drawableName, "drawable", context.packageName)
    return ContextCompat.getDrawable(context, resourceId)
}