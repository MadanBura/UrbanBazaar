package com.ex.urbanbazaar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ex.urbanbazaar.model.response.CategoryResponse
import com.ex.urbanbazaar.model.response.ProductResponse
import com.ex.urbanbazaar.repository.ProductRepository
import com.ex.urbanbazaar.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _categoryRes = MutableLiveData<ResultState<CategoryResponse>>()
    val categoryRes: LiveData<ResultState<CategoryResponse>> get() = _categoryRes

    private val _clothesRes = MutableLiveData<ResultState<ProductResponse>>()
    val clothesRes: LiveData<ResultState<ProductResponse>> get() = _clothesRes

    private val _electronicsRes = MutableLiveData<ResultState<ProductResponse>>()
    val electronicsRes: LiveData<ResultState<ProductResponse>> get() = _electronicsRes

    private val _furnitureRes = MutableLiveData<ResultState<ProductResponse>>()
    val furnitureRes: LiveData<ResultState<ProductResponse>> get() = _furnitureRes

    private val _allProducts = MutableLiveData<ResultState<ProductResponse>>()
    val allProducts: LiveData<ResultState<ProductResponse>> get() = _allProducts

    fun getProductCategories() {
        _categoryRes.postValue(ResultState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = productRepository.getProductCategories()
                if (response.isSuccessful) {
                    _categoryRes.postValue(ResultState.Success(response.body()!!))
                } else {
                    _categoryRes.postValue(ResultState.Error(Exception(response.message())))
                }
            } catch (e: Exception) {
                _categoryRes.postValue(ResultState.Error(Exception(e.message ?: "Unknown Error")))
            }
        }
    }

    fun getProductsByCategoryId(categoryId: Int) {
        when (categoryId) {
            1 -> fetchCategoryProducts(_clothesRes, categoryId)
            2 -> fetchCategoryProducts(_electronicsRes, categoryId)
            3 -> fetchCategoryProducts(_furnitureRes, categoryId)
        }
    }

    private fun fetchCategoryProducts(
        liveData: MutableLiveData<ResultState<ProductResponse>>,
        categoryId: Int
    ) {
        liveData.value = ResultState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = productRepository.getProductList(categoryId)
                if (response.isSuccessful) {
                    liveData.postValue(ResultState.Success(response.body()!!))
                } else {
                    liveData.postValue(ResultState.Error(Exception(response.message())))
                }
            } catch (e: Exception) {
                liveData.postValue(ResultState.Error(Exception(e.message)))
            }
        }
    }

    fun getAllProducts() {
        _allProducts.value = ResultState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = productRepository.getAllProducts()
                if (response.isSuccessful) {
                    _allProducts.postValue(ResultState.Success(response.body()!!))
                } else {
                    _allProducts.postValue(ResultState.Error(Exception(response.message())))
                }
            } catch (e: Exception) {
                _allProducts.postValue(ResultState.Error(Exception(e.message)))
            }
        }
    }
}
