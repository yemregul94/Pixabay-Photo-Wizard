package com.moonlight.tsoft_pixabay.ui.main.itemdetails

import androidx.lifecycle.ViewModel
import com.moonlight.tsoft_pixabay.data.repo.PicturesRepository
import com.moonlight.tsoft_pixabay.data.repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ItemDetailsViewModel @Inject constructor(var prepo: PicturesRepository, var urepo: UserRepository) : ViewModel() {

}