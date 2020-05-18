package com.iitu.gowithme.ui.create_new_event.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iitu.gowithme.R
import com.iitu.gowithme.data.models.response.CategoryResponse
import com.iitu.gowithme.util.inflate
import kotlinx.android.synthetic.main.item_checkbox.view.*

class CategoryCheckboxRecyclerAdapter(
    private val onCheckedChangeListener: (position: Int) -> Unit
): RecyclerView.Adapter<CategoryCheckboxRecyclerAdapter.ViewHolder>() {

    private val categories = ArrayList<CategoryResponse>()

    fun setCategories(data: List<CategoryResponse>) {
        categories.clear()
        categories.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.inflate(R.layout.item_checkbox))

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(categories[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(category: CategoryResponse) {
            with(itemView.checkbox) {
                text = category.name
                isChecked = category.isChecked

                setOnClickListener { _->
                    onCheckedChangeListener.invoke(adapterPosition)
                }
            }
        }
    }
}