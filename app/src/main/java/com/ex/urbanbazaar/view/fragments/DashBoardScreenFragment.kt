package com.ex.urbanbazaar.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ex.urbanbazaar.R
import com.ex.urbanbazaar.databinding.FragmentDashBoardScreenBinding
import com.ex.urbanbazaar.databinding.FragmentLoginBinding
import com.ex.urbanbazaar.model.response.CategoryResponseItem
import com.ex.urbanbazaar.utils.ResultState
import com.ex.urbanbazaar.viewmodel.ProductViewModel


class DashBoardScreenFragment : Fragment() {

    private lateinit var binding : FragmentDashBoardScreenBinding

    private val productViewModel : ProductViewModel by viewModels()
//    private lateinit var categoryAdapter : DashBoardScreenCategoryAdapters


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        categoryAdapter = DashBoardScreenCategoryAdapters()


        binding = FragmentDashBoardScreenBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productViewModel.getProductCategories()

        productViewModel.categoryRes.observe(viewLifecycleOwner){result->
            when(result){
                is ResultState.Loading -> {

                }

                is ResultState.Success -> {
                    val categoryRes = result.data.toList()
                    updateCategoryRecyclerUi(categoryRes)
                }

                is ResultState.Error -> {

                }
            }
        }
    }


    private fun updateCategoryRecyclerUi(category : List<CategoryResponseItem>){

    }

}