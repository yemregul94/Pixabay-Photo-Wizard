package com.moonlight.tsoft_pixabay.ui.main.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moonlight.tsoft_pixabay.data.model.Pictures
import com.moonlight.tsoft_pixabay.data.repo.PicturesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(var prepo: PicturesRepository) : ViewModel() {

    private var picturesList: MutableList<Pictures> = mutableListOf()
    private var latestFavList: MutableList<Int> = mutableListOf()

    val picturesLiveData: LiveData<List<Pictures>?> get() = _picturesLiveData
    private var _picturesLiveData: MutableLiveData<List<Pictures>?> = MutableLiveData()

    val loadingLiveData: LiveData<Boolean> get() = _loadingLiveData
    private var _loadingLiveData: MutableLiveData<Boolean> = MutableLiveData()

    suspend fun updateLiveData(list: List<Int>){
        if (latestFavList != list){
            picturesList.clear()

            _loadingLiveData.value = true
            list.forEach {
                picturesList.add(prepo.getPicture(it))
                _picturesLiveData.value = picturesList
                latestFavList = list.toMutableList()
            }
            _loadingLiveData.value = false

        }
    }
}