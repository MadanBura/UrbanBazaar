package com.ex.urbanbazaar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ex.urbanbazaar.model.response.CategoryResponse
import com.ex.urbanbazaar.repository.ProductRepository
import com.ex.urbanbazaar.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _categoryRes = MutableLiveData<ResultState<CategoryResponse>>()
    val categoryRes : LiveData<ResultState<CategoryResponse>> get() = _categoryRes



    fun getProductCategories(){

        _categoryRes.value = ResultState.Loading

        viewModelScope.launch {

            try {
                val response = productRepository.getProductCategories()

                if(response.isSuccessful){
                    _categoryRes.value = ResultState.Success(response.body()!!)
                }else{
                    _categoryRes.value = ResultState.Error(Exception("Error while fetching ProductCategories : ${response.message()}"))
                }

            }catch (e : Exception){
                _categoryRes.value = ResultState.Error(Exception("Login failed: ${e.message}"))

            }

        }


    }

}