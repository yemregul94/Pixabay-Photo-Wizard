package com.moonlight.tsoft_pixabay.ui.main.dashboard

import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moonlight.tsoft_pixabay.databinding.FragmentDashboardBinding
import com.moonlight.tsoft_pixabay.ui.main.MainViewModel
import com.moonlight.tsoft_pixabay.util.SwipeDecorator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DashboardViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    private val adapter = DashboardAdapter()
    private var favList : MutableList<Int> = mutableListOf()
    private var uid: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        adapter.onFavClick = { position, checked ->
            toggleFav(adapter.snapshot().items[position].id, position, checked)
        }

        binding.searchList.setOnQueryTextListener(this@DashboardFragment)

        setAdapter()
        observeUser()
        observePager()

        return binding.root
    }

    private fun setAdapter() {
        binding.rvLists.adapter = adapter
        binding.rvLists.layoutManager = LinearLayoutManager(requireContext())
        binding.rvLists.itemAnimator = null
    }

    private fun observeUser(){
        mainViewModel.user.observe(viewLifecycleOwner){
            if (it != null) {
                uid = it.uid
                favList = it.favList.toMutableList()
                adapter.favList = favList
            }
        }
    }

    private fun observePager(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.pictureList.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun toggleFav(id: Int, position: Int, check: Boolean){
        if(check){
            favList.add(id)
        }
        else {
            favList.remove(id)
        }

        val user = mainViewModel.user.value!!.copy(favList = favList)
        mainViewModel.updateUserData(user, uid)
        adapter.notifyItemChanged(position)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        searchImages(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        searchImages(newText)
        return true
    }

    private fun searchImages(query: String?){
        viewModel.changeQuery(query.toString())
    }

}