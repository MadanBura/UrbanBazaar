package com.ex.urbanbazaar.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ex.urbanbazaar.R

class RegistrationScreenFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =  inflater.inflate(R.layout.fragment_registration_screen, container, false)

        val registerbtn = view.findViewById<Button>(R.id.btnSignUp)
        registerbtn.setOnClickListener {
            findNavController().navigate(R.id.action_registrationScreenFragment_to_complateProfileFragment)
        }

        return  view
    }
}