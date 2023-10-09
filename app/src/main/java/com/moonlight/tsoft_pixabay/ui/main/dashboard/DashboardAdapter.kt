package com.moonlight.tsoft_pixabay.ui.main.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.moonlight.tsoft_pixabay.R
import com.moonlight.tsoft_pixabay.data.model.Pictures
import com.moonlight.tsoft_pixabay.databinding.ItemListItemBinding

class DashboardAdapter : RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {

    var onFavClick: ((Int, Boolean) -> Unit)? = null
    var favList = emptyList<Int>()

    inner class ViewHolder(binding: ItemListItemBinding) : RecyclerView.ViewHolder(binding.root){
        var binding: ItemListItemBinding
        init {
            this.binding = binding
        }
    }

    private val differCallBack = object : DiffUtil.ItemCallback<Pictures>() {
        override fun areItemsTheSame(oldItem: Pictures, newItem: Pictures): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Pictures, newItem: Pictures): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_list_item, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        val listItem = differ.currentList[position]
        val bind = holder.binding
        bind.pictures = listItem

        val isFavorite = favList.contains(listItem.id)
        bind.checkFav.isChecked = isFavorite

        Glide.with(holder.itemView).load(listItem.webformatURL)
            .placeholder(R.drawable.ic_downloading)
            .error(R.drawable.ic_error)
            .override(bind.imageView.maxWidth, bind.imageView.maxHeight)
            .into(bind.imageView)
        //listItem.webformatURL 640p
        //listItem.imageView 150p

        bind.checkFav.setOnClickListener {
            onFavClick?.invoke(holder.adapterPosition, bind.checkFav.isChecked)
        }

        bind.layoutListItem.setOnClickListener {
            val nav = DashboardFragmentDirections.actionDashboardToDetails(listItem, bind.checkFav.isChecked)
            Navigation.findNavController(it).navigate(nav)
        }

        bind.layoutListItem.setOnLongClickListener {
            val popupMenu = PopupMenu(it.context, it)
            popupMenu.inflate(R.menu.menu_popup)
            popupMenu.menu.findItem(R.id.menu_fav).isChecked = isFavorite

            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu_fav -> {
                        menuItem.isChecked = !menuItem.isChecked
                        onFavClick?.invoke(holder.adapterPosition, menuItem.isChecked)
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
            true
        }
    }

}