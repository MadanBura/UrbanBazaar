package com.ex.urbanbazaar.view.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.ex.urbanbazaar.R
import com.ex.urbanbazaar.databinding.FragmentRegistrationScreenBinding
import com.ex.urbanbazaar.model.request.UserRequest
import com.ex.urbanbazaar.utils.ResultState
import com.ex.urbanbazaar.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File


@AndroidEntryPoint
class RegistrationScreenFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationScreenBinding
    private var imageUri: Uri? = null
    private val userViewModel : UserViewModel by viewModels()

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
        private const val PERMISSION_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRegistrationScreenBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val registerbtn = binding.btnSignUp
        registerbtn.setOnClickListener {
            findNavController().navigate(R.id.action_registrationScreenFragment_to_complateProfileFragment)
        }
        checkPermissions()


        binding.profileContainer.setOnClickListener {
            openGallery()
        }


        binding.btnSignUp.setOnClickListener {
            registerUser()
        }


        binding.tvSignIn.setOnClickListener {
            naviGateToSignIn()
        }


        userViewModel.ImageUploadRes.observe(viewLifecycleOwner){ result ->
            when(result){
                is ResultState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnSignUp.visibility = View.GONE
                }
                is ResultState.Success ->{
                    binding.progressBar.visibility = View.GONE
                    binding.btnSignUp.visibility = View.VISIBLE

                    val imageUrl = result.data.location
                    registerUserWithImage(imageUrl)
                }

                is ResultState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnSignUp.visibility = View.VISIBLE

                    Toast.makeText(requireContext(), "Failed To Upload Image: ${result.exception.message}", Toast.LENGTH_SHORT).show()

                }
            } }

        // Observe User Registration Response
       userViewModel.registrationRes.observe(viewLifecycleOwner){ result ->
           when(result) {
               is ResultState.Loading -> {
                   binding.progressBar.visibility = View.VISIBLE
               }

               is ResultState.Success -> {
                   binding.progressBar.visibility = View.GONE
//                   val intent = Intent(requireContext())
                   Toast.makeText(requireContext(), "Registration SuccessFull", Toast.LENGTH_SHORT).show()
                   clearFields()

               }

               is ResultState.Error -> {
                   binding.progressBar.visibility = View.GONE
                   Toast.makeText(requireContext(), "Failed To Upload Image: ${result.exception.message}", Toast.LENGTH_SHORT).show()
               }
           } }


    }

    private fun clearFields(){
        binding.etName.text.clear()
        binding.etEmail.text.clear()
        binding.etPassword.text.clear()
    }


    private fun naviGateToSignIn(){
        findNavController().navigate(R.id.action_registrationScreenFragment_to_loginFragment)
    }


    private fun registerUser(){
        if (imageUri != null) {
            uploadImageToServer(imageUri!!)
        } else {
            registerUserWithImage(null)
        }
    }

    private fun registerUserWithImage(imageUrl: String?) {

        val name = binding.etName.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

       if( validateAllFields(name,email, password)){
           val userRegistrationRequest = UserRequest(
               name = binding.etName.text.toString(),
               email = binding.etEmail.text.toString(),
               password = binding.etPassword.text.toString(),
               avatar = imageUrl ?: ""
           )
           userViewModel.registerUser(userRegistrationRequest)
       }else{
           Toast.makeText(requireContext(), "Check all credentials again", Toast.LENGTH_SHORT).show()
       }
    }


    private fun validateAllFields(name: String, email: String, password: String): Boolean {
        return when {
            name.isBlank() -> {
                println("Name cannot be empty")
                false
            }
            email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                println("Invalid email address")
                false
            }
            password.length < 6 -> {
                println("Password must be at least 6 characters long")
                false
            }
            else -> true
        }
    }

    private fun uploadImageToServer(uri: Uri) {
        val file = getFileFromUri(uri)
        if (file == null) {
            Log.e("Upload", "Failed to get file from URI")
            return
        }
        userViewModel.uploadImageToServer(file)
    }
    private fun getFileFromUri(uri: Uri): File? {
        val inputStream = requireContext().contentResolver.openInputStream(uri) ?: return null
        val tempFile = File.createTempFile("upload", ".jpg", requireContext().cacheDir)
        tempFile.outputStream().use { output -> inputStream.copyTo(output) }
        return tempFile
    }


    private fun openGallery(){
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.data
            imageUri?.let { uri ->
                Glide.with(binding.ivProfile.context)
                        .load(uri)
                        .circleCrop()
                        .into(binding.ivProfile)
            }
        }
    }


    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    PERMISSION_REQUEST_CODE
                )
            }
        }
    }

}