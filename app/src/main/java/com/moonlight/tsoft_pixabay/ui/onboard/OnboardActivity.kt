package com.moonlight.tsoft_pixabay.ui.onboard

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.moonlight.tsoft_pixabay.R
import com.moonlight.tsoft_pixabay.data.model.OnboardItem
import com.moonlight.tsoft_pixabay.databinding.ActivityOnboardBinding
import com.moonlight.tsoft_pixabay.ui.auth.AuthActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardActivity : AppCompatActivity() {

    private var _binding: ActivityOnboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var  onboardAdapter: OnboardAdapter

    //private val onboardViewModel: OnboardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityOnboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnboardItems()
        buttonClick()
        observeViewPager()
    }

    private fun setOnboardItems(){
        onboardAdapter = OnboardAdapter(
            listOf(
                OnboardItem(R.drawable.ic_welcome, getString(R.string.onboard_first_title), getString(R.string.onboard_first_desc)),
                OnboardItem(R.drawable.ic_help, getString(R.string.onboard_second_title), getString(R.string.onboard_second_desc)),
            )
        )
        binding.viewPagerOnboard.adapter = onboardAdapter
    }

    private fun observeViewPager(){
        val myPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
               if(position + 1 == onboardAdapter.itemCount){
                   binding.btnNext.text = getString(R.string.done)
               }
               else{
                   binding.btnNext.text = getString(R.string.next)
               }
            }
        }
        binding.viewPagerOnboard.apply {
            registerOnPageChangeCallback(myPageChangeCallback)
            (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }
    }

    private fun buttonClick(){
        binding.btnNext.setOnClickListener {
            if(binding.viewPagerOnboard.currentItem + 1 < onboardAdapter.itemCount){
                binding.viewPagerOnboard.currentItem += 1
            }
            else {
                goToAuth()
            }
        }
        binding.tvSkip.setOnClickListener {
            goToAuth()
        }
    }

    private fun goToAuth(){
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }
}