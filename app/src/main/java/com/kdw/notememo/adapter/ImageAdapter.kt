package com.kdw.notememo.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kdw.notememo.databinding.ItemImageBinding

class ImageAdapter(context: Context, private var imageList: MutableList<Uri>):
        RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    class ImageViewHolder(val binding : ItemImageBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.binding.imageInsert.setImageURI(imageList[position])

        holder.binding.imageInsert.setOnLongClickListener {
            imageList.removeAt(position)
            notifyDataSetChanged()
            true
        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    internal fun setImages(images: List<Uri>){
        this.imageList.addAll(images)
        notifyDataSetChanged()
    }

    internal fun addImage(uri: Uri){
        this.imageList.add(uri)
        notifyDataSetChanged()
    }
}