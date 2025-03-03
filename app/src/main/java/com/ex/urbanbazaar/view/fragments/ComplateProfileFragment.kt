package com.ex.urbanbazaar.view.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ex.urbanbazaar.R


class ComplateProfileFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_complate_profile, container, false)

        val spinnerGender: Spinner = view.findViewById(R.id.spinnerGender)
        val genderList = resources.getStringArray(R.array.gender_options).toList()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, genderList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerGender.adapter = adapter

        spinnerGender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position == 0) {
                    (view as? TextView)?.setTextColor(Color.GRAY) // Make hint text gray
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        return view
    }
}