package com.example.xetatest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.xetatest.R
import com.example.xetatest.databinding.ItemSimilarBinding
import com.squareup.picasso.Picasso

class SimilarItemsAdapter (
    private val items: List<SimilarItems>
): RecyclerView.Adapter<SimilarItemsAdapter.SimilarViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarViewHolder {
        val binding = ItemSimilarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SimilarViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: SimilarViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class SimilarViewHolder(private val binding: ItemSimilarBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(similarItems: SimilarItems) {
            val name = similarItems.name
            binding.similarName.text = name
            var img = R.drawable.chicken_biryani
            if (name == "Veg Biryani")
                img = R.drawable.veg_biryani
            else if(name == "Paneer Biryani")
                img = R.drawable.paneer_biryani
            else if(name == "Mutton Biryani")
                img = R.drawable.mutton_biryani

            binding.similarImage.setImageResource(img)
        }

    }


}



data class SimilarItems(val name: String, val image: String)