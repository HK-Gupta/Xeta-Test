package com.example.xetatest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.xetatest.databinding.ItemFactsBinding

class FactsAdapter (
    private val facts: List<String>
): RecyclerView.Adapter<FactsAdapter.FactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FactViewHolder {
        val binding = ItemFactsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FactViewHolder(binding)
    }

    override fun getItemCount() = facts.size

    override fun onBindViewHolder(holder: FactViewHolder, position: Int) {
        holder.bind(facts[position])
    }

    inner class FactViewHolder(private val binding: ItemFactsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(fact: String) {
            binding.factsDetails.text = fact
        }

    }

}