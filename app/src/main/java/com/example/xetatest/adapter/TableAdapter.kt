package com.example.xetatest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.xetatest.databinding.ItemNutrientsBinding

class TableAdapter(
    private val items: List<NutritionTableRow>
): RecyclerView.Adapter<TableAdapter.TableViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TableViewHolder {
        val binding = ItemNutrientsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TableViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: TableViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class TableViewHolder(private val binding: ItemNutrientsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                textViewName.text = items[position].name
                textViewPerServe.text = items[position].perServe
                textViewPerScaled.text = items[position].scaled
            }
        }

    }

}



data class NutritionTableRow(val name: String, val perServe: String, val scaled: String)