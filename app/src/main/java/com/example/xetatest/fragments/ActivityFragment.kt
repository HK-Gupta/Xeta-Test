package com.example.xetatest.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.xetatest.FoodDetails
import com.example.xetatest.R
import com.example.xetatest.databinding.FragmentActivityBinding


class ActivityFragment : Fragment() {

    private lateinit var binding: FragmentActivityBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        binding = FragmentActivityBinding.inflate(inflater, container, false)

        binding.gotoDiets.setOnClickListener {
            startActivity(Intent(context, FoodDetails::class.java))
        }

        return binding.root
    }

}