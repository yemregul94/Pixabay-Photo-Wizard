package com.moonlight.tsoft_pixabay.ui.main.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.moonlight.tsoft_pixabay.data.model.Pictures
import com.moonlight.tsoft_pixabay.data.repo.PicturesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(var prepo: PicturesRepository) : ViewModel() {

    private val _queryStateFlow = MutableStateFlow("")
    private val queryStateFlow: StateFlow<String> = _queryStateFlow

    val pictureList = queryStateFlow.flatMapLatest { query ->
        Pager(PagingConfig(pageSize = 1)) {
            PicturesPagingSource(prepo, query)
        }.flow.cachedIn(viewModelScope)
    }

    fun changeQuery(query: String){
        _queryStateFlow.value = query
    }

}