package com.laraguzman.ecommercegapsi.ui.main.adapters

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.laraguzman.ecommercegapsi.databinding.ItemPhotoEcommerceBinding
import com.laraguzman.gapsiecommerce.data.models.PhotosEcommerceModel

class PhotosAdapter() : RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder>() {

    var items = ArrayList<PhotosEcommerceModel>()

    fun setDataList(data: ArrayList<PhotosEcommerceModel>){
        this.items = data
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhotosAdapter.PhotosViewHolder {
        return PhotosViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PhotosAdapter.PhotosViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount() = items.size

    class PhotosViewHolder(val binding: ItemPhotoEcommerceBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data: PhotosEcommerceModel){
            binding.itemRecyclerPhoto = data

            Glide.with(binding.imageItem)
                .load(data.image)
                .listener(object: RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.itemProgressBar.visibility = View.GONE
                        return false;
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.itemProgressBar.visibility = View.GONE
                        return false;
                    }

                })
                .into(binding.imageItem)

        }

        companion object {
            fun from(parent: ViewGroup) : PhotosViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemPhotoEcommerceBinding.inflate(layoutInflater)
                return PhotosViewHolder(binding)
            }
        }
    }
}