package com.moonlight.tsoft_pixabay.ui.onboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.moonlight.tsoft_pixabay.R
import com.moonlight.tsoft_pixabay.data.model.OnboardItem
import com.moonlight.tsoft_pixabay.databinding.ItemOnboardingBinding

class OnboardAdapter(private val onboardItems: List<OnboardItem>) : RecyclerView.Adapter<OnboardAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ItemOnboardingBinding) : RecyclerView.ViewHolder(binding.root){
        var binding: ItemOnboardingBinding
        init {
            this.binding = binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemOnboardingBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_onboarding, parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = onboardItems[position]
        val bind = holder.binding
        bind.onboardItem = item

    }

    override fun getItemCount(): Int {
        return onboardItems.size
    }
}