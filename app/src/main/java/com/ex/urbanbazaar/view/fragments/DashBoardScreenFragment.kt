package com.ex.urbanbazaar.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ex.urbanbazaar.databinding.FragmentDashBoardScreenBinding
import com.ex.urbanbazaar.model.response.CategoryResponseItem
import com.ex.urbanbazaar.model.response.ProductResponseItem
import com.ex.urbanbazaar.utils.ResultState
import com.ex.urbanbazaar.utils.wishListClickListener
import com.ex.urbanbazaar.view.adapters.AllProductsAdapter
import com.ex.urbanbazaar.view.adapters.DashBoardScreenCategoryAdapters
import com.ex.urbanbazaar.view.adapters.ProductAdapter
import com.ex.urbanbazaar.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashBoardScreenFragment : Fragment() {

    private var _binding: FragmentDashBoardScreenBinding? = null
    private val binding get() = _binding!!

    private val productViewModel: ProductViewModel by viewModels()

    private var categoryAdapter: DashBoardScreenCategoryAdapters? = null
    private var forClotheAdapter: ProductAdapter? = null
    private var forElectronicItemAdapter: ProductAdapter? = null
    private var forFurnitureItemAdapter: ProductAdapter? = null
    private var allProductsAdapter: AllProductsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashBoardScreenBinding.inflate(inflater, container, false)
        setupCategoryAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        fetchProductData()
    }

    private fun setupCategoryAdapter() {
        categoryAdapter = DashBoardScreenCategoryAdapters(listOf())
        binding.categoryRCV.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.categoryRCV.adapter = categoryAdapter
    }

    private fun observeViewModel() {
        productViewModel.categoryRes.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultState.Loading -> {}
                is ResultState.Success -> updateCategoryRecyclerUi(result.data)
                is ResultState.Error -> Log.e("DashBoardScreenFragment", "Error fetching categories: ${result.exception}")
            }
        }

        productViewModel.clothesRes.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultState.Loading -> {}
                is ResultState.Success -> setupClothesAdapter(result.data)
                is ResultState.Error -> Log.e("DashBoardScreenFragment", "Error fetching clothes: ${result.exception}")
            }
        }

        productViewModel.electronicsRes.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultState.Loading -> {}
                is ResultState.Success -> setupElectronicsAdapter(result.data)
                is ResultState.Error -> Log.e("DashBoardScreenFragment", "Error fetching electronics: ${result.exception}")
            }
        }

        productViewModel.furnitureRes.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultState.Loading -> {}
                is ResultState.Success -> setupFurnitureAdapter(result.data)
                is ResultState.Error -> Log.e("DashBoardScreenFragment", "Error fetching furniture: ${result.exception}")
            }
        }

        productViewModel.allProducts.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultState.Loading -> {}
                is ResultState.Success -> setupAllProductsAdapter(result.data)
                is ResultState.Error -> Log.e("DashBoardScreenFragment", "Error fetching all products: ${result.exception}")
            }
        }
    }

    private fun fetchProductData() {
        productViewModel.getProductCategories()
        productViewModel.getProductsByCategoryId(1) // Clothes category
        productViewModel.getProductsByCategoryId(2) // Electronics category
        productViewModel.getProductsByCategoryId(3) // Furniture category
        productViewModel.getAllProducts()
    }

    private fun setupClothesAdapter(products: List<ProductResponseItem>) {
        if (forClotheAdapter == null) {
            forClotheAdapter = ProductAdapter(products, object : wishListClickListener {
                override fun clickToAddWishList(product: ProductResponseItem) {}
            })
            binding.ProductsCorpRCV.layoutManager = GridLayoutManager(requireContext(), 2)
            binding.ProductsCorpRCV.adapter = forClotheAdapter
        } else {
            forClotheAdapter?.updateProducts(products)
        }
    }

    private fun setupElectronicsAdapter(products: List<ProductResponseItem>) {
        if (forElectronicItemAdapter == null) {
            forElectronicItemAdapter = ProductAdapter(products, object : wishListClickListener {
                override fun clickToAddWishList(product: ProductResponseItem) {}
            })
            binding.ProductsRadiantRCV.layoutManager = GridLayoutManager(requireContext(), 2)
            binding.ProductsRadiantRCV.adapter = forElectronicItemAdapter
        } else {
            forElectronicItemAdapter?.updateProducts(products)
        }
    }

    private fun setupFurnitureAdapter(products: List<ProductResponseItem>) {
        if (forFurnitureItemAdapter == null) {
            forFurnitureItemAdapter = ProductAdapter(products, object : wishListClickListener {
                override fun clickToAddWishList(product: ProductResponseItem) {}
            })
            binding.ProductsLastRCV.layoutManager = GridLayoutManager(requireContext(), 2)
            binding.ProductsLastRCV.adapter = forFurnitureItemAdapter
        } else {
            forFurnitureItemAdapter?.updateProducts(products)
        }
    }

    private fun setupAllProductsAdapter(products: List<ProductResponseItem>) {
        if (allProductsAdapter == null) {
            allProductsAdapter = AllProductsAdapter(products, object : wishListClickListener {
                override fun clickToAddWishList(product: ProductResponseItem) {}
            })
            binding.AllProductsRCV.layoutManager = GridLayoutManager(requireContext(), 2)
            binding.AllProductsRCV.adapter = allProductsAdapter
        } else {
            allProductsAdapter?.updateProducts(products)
        }
    }

    private fun updateCategoryRecyclerUi(categories: List<CategoryResponseItem>) {
        if (categories.isNotEmpty()) {
            categoryAdapter?.updateCategories(categories)
        } else {
            Log.e("DashBoardScreenFragment", "Category list is empty")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
