package com.moonlight.tsoft_pixabay.ui.main.itemdetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.moonlight.tsoft_pixabay.databinding.FragmentItemDetailsBinding
import com.moonlight.tsoft_pixabay.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemDetailsFragment : Fragment() {

    private var _binding: FragmentItemDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ItemDetailsViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentItemDetailsBinding.inflate(inflater, container, false)

        val bundle: ItemDetailsFragmentArgs by navArgs()
        val pictures = bundle.picture

        binding.pictures = pictures
        binding.isChecked = bundle.checked

        binding.checkDetails.setOnClickListener {
            val check = it as CheckBox
            toggleFav(bundle.picture.id, check.isChecked)
        }

        binding.imageViewDetails.setOnClickListener {
            goToLink(pictures.largeImageURL)
        }
        binding.tvDetailsLinkTitle.setOnClickListener {
            goToLink(pictures.pageURL)
        }
        binding.tvDetailsLink.setOnClickListener {
            goToLink(pictures.pageURL)
        }

        Glide.with(requireContext()).load(pictures.largeImageURL).into(binding.imageViewDetails)

        return binding.root
    }

    private fun toggleFav(id: Int, check: Boolean){
        val favList = mainViewModel.user.value!!.favList.toMutableList()
        if(check){
            favList.add(id)
        }
        else {
            favList.remove(id)
        }

        val user = mainViewModel.user.value!!.copy(favList = favList)
        mainViewModel.updateUserData(user, user.uid)
    }

    private fun goToLink(link: String){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(intent)
    }

}