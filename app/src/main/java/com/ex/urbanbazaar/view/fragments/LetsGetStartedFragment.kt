package com.ex.urbanbazaar.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.ex.urbanbazaar.R

class LetsGetStartedFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_lets_get_started, container, false)

        val letGSBtn = view.findViewById<Button>(R.id.btnLGS)

        letGSBtn.setOnClickListener {
            try {
                findNavController().navigate(R.id.action_letsGetStartedFragment_to_onBoardScreenFragment)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        return view
    }

}