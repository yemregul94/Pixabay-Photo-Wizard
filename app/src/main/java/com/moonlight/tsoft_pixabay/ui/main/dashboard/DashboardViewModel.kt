package com.moonlight.tsoft_pixabay.ui.main.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moonlight.tsoft_pixabay.data.model.Pictures
import com.moonlight.tsoft_pixabay.data.repo.PicturesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(var prepo: PicturesRepository) : ViewModel() {

    private var currentPage = 1
    private var currentQuery = "null"

    val movieListLiveData = MutableLiveData<List<Pictures>>()
    val loadingLiveData = MutableLiveData<Boolean>()
    val errorLiveData = MutableLiveData<String>()
    private val picturesList: MutableList<Pictures> = mutableListOf()

    fun searchPictures(query: String) {
        if (query != currentQuery) {
            currentQuery = query
            currentPage = 1
            picturesList.clear()
            loadPictures()
        }
    }

    init {
        searchPictures("")
    }

    fun loadPictures() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                loadingLiveData.postValue(true)
                val response = prepo.searchPictures(currentQuery, currentPage).hits
                if (response.isNotEmpty()) {
                    picturesList.addAll(response)
                    movieListLiveData.postValue(picturesList)
                    currentPage++
                } else {
                    errorLiveData.postValue("Error loading data")
                }
                loadingLiveData.postValue(false)
            } catch (e: Exception) {
                errorLiveData.postValue("Error: ${e.message}")
                loadingLiveData.postValue(false)
            }
        }
    }

}