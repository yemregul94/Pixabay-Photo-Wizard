package com.moonlight.tsoft_pixabay.ui.main.favorites

import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moonlight.tsoft_pixabay.databinding.FragmentFavoritesBinding
import com.moonlight.tsoft_pixabay.ui.main.MainViewModel
import com.moonlight.tsoft_pixabay.util.SwipeDecorator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoritesViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    private val adapter = FavoritesAdapter()
    private var favList : MutableList<Int> = mutableListOf()
    private var uid: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)


        setAdapter()
        observeUser()
        observePicturesLiveData()
        observeLoading()
        setOnRecyclerViewItemSwipedListener()

        return binding.root
    }

    private fun setAdapter(){
        binding.rvFavorites.adapter = adapter
        binding.rvFavorites.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFavorites.itemAnimator = null

        adapter.onFavClick = { position ->
            removeFav(adapter.differ.currentList[position].id)
        }
    }

    private fun observeUser(){
        mainViewModel.user.observe(viewLifecycleOwner){
            if (it != null) {
                uid = it.uid
                favList = it.favList.toMutableList()
                updatePicturesLiveData()
            }
        }
    }

    private fun observePicturesLiveData(){
        viewModel.picturesLiveData.observe(viewLifecycleOwner){
            adapter.differ.submitList(it?.toList())
        }
    }

    private fun observeLoading(){
        viewModel.loadingLiveData.observe(viewLifecycleOwner){
            if(it){
                binding.progressBar.visibility = View.VISIBLE
            }
            else {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun updatePicturesLiveData(){
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                viewModel.updateLiveData(favList)
            }
            catch (_:Exception){
            }
        }
    }

    private fun removeFav(id: Int){
        favList.remove(id)
        val user = mainViewModel.user.value!!.copy(favList = favList)
        mainViewModel.updateUserData(user, uid)
    }

    private fun setOnRecyclerViewItemSwipedListener() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val swipedListItem = adapter.differ.currentList[position]

                if (direction == ItemTouchHelper.LEFT) {
                    removeFav(swipedListItem.id)
                } else if (direction == ItemTouchHelper.RIGHT) {
                    removeFav(swipedListItem.id)

                }
            }

            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

                val itemView = viewHolder.itemView

                SwipeDecorator().decorateSwiper(itemView, c, dX, recyclerView)

            }

        }).attachToRecyclerView(binding.rvFavorites)
    }


}