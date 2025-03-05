package com.ex.urbanbazaar.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ex.urbanbazaar.R
import com.ex.urbanbazaar.databinding.ActivityDashboardBinding
import com.ex.urbanbazaar.view.fragments.DashBoardScreenFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDashboardBinding
    private lateinit var bottomNavigationView: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNavigationView = binding.bottomNavigationView

        if (savedInstanceState==null){
            loadFragment(DashBoardScreenFragment())
        }

        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {

                R.id.nav_home ->{
                    loadFragment(DashBoardScreenFragment())
                    true
                }

                else -> false
            }
        }



    }

    private fun loadFragment(fragment: androidx.fragment.app.Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerViewDashBoard, fragment)
        transaction.commit()
    }

}