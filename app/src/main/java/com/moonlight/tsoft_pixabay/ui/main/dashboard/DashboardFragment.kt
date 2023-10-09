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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moonlight.tsoft_pixabay.databinding.FragmentDashboardBinding
import com.moonlight.tsoft_pixabay.ui.main.MainViewModel
import com.moonlight.tsoft_pixabay.util.SwipeDecorator
import dagger.hilt.android.AndroidEntryPoint

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
            toggleFav(adapter.differ.currentList[position].id, position, checked)
        }

        binding.searchList.setOnQueryTextListener(this@DashboardFragment)

        setAdapter()
        observeLiveDatas()
        observeUser()
        observeScroll()
        setOnRecyclerViewItemSwipedListener()

        return binding.root
    }

    private fun setAdapter() {
        binding.rvLists.adapter = adapter
        binding.rvLists.layoutManager = LinearLayoutManager(requireContext())
        binding.rvLists.itemAnimator = null
    }

    private fun observeLiveDatas() {
        viewModel.movieListLiveData.observe(viewLifecycleOwner) {
            adapter.differ.submitList(it.toList())
        }

        viewModel.loadingLiveData.observe(viewLifecycleOwner) {
            if(it){
                binding.progressBarDashboard.visibility = View.VISIBLE
            }
            else {
                binding.progressBarDashboard.visibility = View.GONE
            }
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }
    }

    private fun observeScroll(){
        binding.rvLists.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!binding.rvLists.canScrollVertically(1)) {
                    viewModel.loadPictures()
                }
            }
        })
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
        viewModel.searchPictures(query.toString())
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
                    toggleFav(swipedListItem.id, position, !favList.contains(swipedListItem.id))
                    adapter.notifyDataSetChanged()
                } else if (direction == ItemTouchHelper.RIGHT) {
                    toggleFav(swipedListItem.id, position, !favList.contains(swipedListItem.id))
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

                val itemView = viewHolder.itemView

                SwipeDecorator().decorateSwiper(itemView, c, dX, recyclerView)

            }

        }).attachToRecyclerView(binding.rvLists)
    }


}