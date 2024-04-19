package com.example.xetatest

import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.TableRow
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.xetatest.adapter.FactsAdapter
import com.example.xetatest.adapter.NutritionTableRow
import com.example.xetatest.adapter.SimilarItems
import com.example.xetatest.adapter.SimilarItemsAdapter
import com.example.xetatest.adapter.TableAdapter
import com.example.xetatest.databinding.ActivityFoodDetailsBinding
import com.squareup.picasso.Picasso
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class FoodDetails : AppCompatActivity() {

    private val binding by lazy {
        ActivityFoodDetailsBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val url = "http://52.25.229.242:8000/food_info/"

        val request = Request.Builder()
            .url(url)
            .build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("API_VALUES", "Failed")
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                completeView(call, response)
            }

        })

        binding.back.setOnClickListener {
            finish()
        }

    }

    private fun completeView(call: Call, response: Response) {
        val responseData = response.body?.string()

        runOnUiThread{
            if (responseData != null) {
                val jsonObject = JSONObject(responseData)
                val dataObject = jsonObject.getJSONObject("data")

                //Extracting the values
                val name = dataObject.getString("name")
                val healthRating = dataObject.getString("health_rating")
                val description = dataObject.getString("description")
                val image = dataObject.getString("image")
                val genericFacts = dataObject.getJSONArray("generic_facts")
                val nutritionInfoScaled = dataObject.getJSONArray("nutrition_info_scaled")
                val servingSizes = dataObject.getJSONArray("serving_sizes")
                val similarItems = dataObject.getJSONArray("similar_items")
                Log.d("API_VALUES", name + healthRating + description)

                binding.foodName.text = name
                binding.textHealthRating.text = healthRating
                binding.description.text = description
                Picasso.get().load(image).into(binding.foodImage)

                // Extracting data for Table adapter
                callTableAdapter(nutritionInfoScaled, servingSizes)

                // Putting the Extracted Facts in the RecyclerView
                callFactsAdapter(genericFacts)

                // Putting the Extracted Items in the RecyclerView
                callSimilarItemsAdapter(similarItems)

                binding.foodImage.setImageResource(R.drawable.chicken_biryani)
            }
        }

    }

    private fun callSimilarItemsAdapter(similarItems: JSONArray) {
        val similarItemList = mutableListOf<SimilarItems>()

        for (i in 0 until similarItems.length()) {
            val item = similarItems.getJSONObject(i)
            val name = item.getString("name")
            val image = item.getString("image")
            similarItemList.add(SimilarItems(name, image))
        }

        val adapter = SimilarItemsAdapter(similarItemList)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.recyclerViewSimilar.layoutManager = layoutManager
        binding.recyclerViewSimilar.adapter = adapter
    }

    private fun callFactsAdapter(genericFacts: JSONArray) {
        val facts = mutableListOf<String>()

        for (i in 0 until genericFacts.length()) {
            val fact = genericFacts.getString(i)
            facts.add(fact)
        }

        val adapter = FactsAdapter(facts)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.recyclerViewFacts.layoutManager = layoutManager
        binding.recyclerViewFacts.adapter = adapter
    }

    private fun callTableAdapter(nutritionInfoScaled: JSONArray, nutritionInfo: JSONArray) {

        val nutritionInfoMapping = mapOf(
            "Calories" to "Energy",
            "Protein" to "Proteins",
            "Total Fat" to "Fat",
            "Carbohydrates" to "Carbs",
            "Vitamin C" to "Vitaminc",
            "Fiber" to "Fibre"
        )
        
        val items = mutableListOf<NutritionTableRow>()

        for (i in 0 until nutritionInfo.length()) {
            val obj = nutritionInfo.getJSONObject((i))
            val name = obj.getString("name")
            val value = obj.getDouble("value").toString()
            val units = obj.getString("units")

            var scaledValue = ""

            for (j in 0 until nutritionInfoScaled.length()) {
                val scaledObj = nutritionInfoScaled.getJSONObject(j)
                val mappedName = nutritionInfoMapping[scaledObj.getString("name")] ?: scaledObj.getString("name")
                if(name == mappedName) {
                    scaledValue = String.format("%.2f", scaledObj.getDouble("value"))
                    break
                }
            }

            items.add(NutritionTableRow("$name ($units)", value, scaledValue))
        }

        val adapter = TableAdapter(items)
        binding.recyclerViewTable.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewTable.adapter = adapter

    }
}