package com.ex.urbanbazaar.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.ex.urbanbazaar.R
import com.ex.urbanbazaar.view.activity.LoginActivity
import com.ex.urbanbazaar.view.adapters.OnBoardScreenImageAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class OnBoardScreenFragment : Fragment() {

    private lateinit var viewPager2: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var titleTextView: TextView
    private lateinit var descriptionTextView: TextView

    private val screenImageList = listOf(
        R.drawable.mobilesone,
        R.drawable.mobiletwo,
        R.drawable.mobilesthree
    )

    private val titleList = listOf(
        "Discover Amazing Deals",
        "Shop Smart with Us",
        "Get Started Today!"
    )

    private val descriptionList = listOf(
        "Find the best deals on top brands with exclusive discounts.",
        "Browse through our vast collection and make informed choices.",
        "Create an account to start exploring our fantastic product range."
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_on_board_screen, container, false)

        viewPager2 = view.findViewById(R.id.viewPager2)
        tabLayout = view.findViewById(R.id.tabLayout)
        nextButton = view.findViewById(R.id.nextButton)
        prevButton = view.findViewById(R.id.prevButton)
        titleTextView = view.findViewById(R.id.titleTv)
        descriptionTextView = view.findViewById(R.id.descriptionTv)

        val adapter = OnBoardScreenImageAdapter(screenImageList)
        viewPager2.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager2) { _, _ -> }.attach()

       //Add a callback that will be invoked whenever the page changes or is incrementally scrolled
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                titleTextView.text = titleList[position]
                descriptionTextView.text = descriptionList[position]

                prevButton.visibility = if (position == 0) View.INVISIBLE else View.VISIBLE

            }
        })

        nextButton.setOnClickListener {
            if (viewPager2.currentItem < screenImageList.size - 1) {
                viewPager2.currentItem += 1
            }else{
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
            }
        }

        prevButton.setOnClickListener {
            if (viewPager2.currentItem > 0) {
                viewPager2.currentItem -= 1
            }
        }

        // Set initial text
        titleTextView.text = titleList[0]
        descriptionTextView.text = descriptionList[0]

        return view
    }
}
