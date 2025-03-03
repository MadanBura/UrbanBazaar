package com.ex.urbanbazaar.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ex.urbanbazaar.R


class LoginFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        val tvSignUp = view.findViewById<TextView>(R.id.tv_sign_up)
        tvSignUp.setOnClickListener {

            try {
                findNavController().navigate(R.id.action_loginFragment_to_registrationScreenFragment)
            }catch (e : Exception){
                e.printStackTrace()
            }

        }

        return view
    }

}