package com.ex.urbanbazaar.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.ex.urbanbazaar.databinding.FragmentLoginBinding
import com.ex.urbanbazaar.utils.ResultState
import com.ex.urbanbazaar.view.activity.DashboardActivity
import com.ex.urbanbazaar.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvSignUp.setOnClickListener {
            navigateToSignUp()
        }

        binding.btnSignIn.setOnClickListener {
            handleLogin()
        }

        observeLoginState()
    }

    private fun navigateToSignUp() {
        try {
            findNavController().navigate(com.ex.urbanbazaar.R.id.action_loginFragment_to_registrationScreenFragment)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun handleLogin() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            userViewModel.login(email, password)
        } else {
            Toast.makeText(requireContext(), "Please enter email and password", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeLoginState() {
        userViewModel.loginState.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is ResultState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnSignIn.visibility = View.GONE

                }

                is ResultState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnSignIn.visibility = View.VISIBLE

                    Toast.makeText(requireContext(), "Login successful!", Toast.LENGTH_SHORT).show()
                    launchDashboard()
                }

                is ResultState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnSignIn.visibility = View.VISIBLE

                    Toast.makeText(requireContext(), "Login failed: ${result.exception.message}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun launchDashboard() {
        val intent = Intent(requireContext(), DashboardActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}
