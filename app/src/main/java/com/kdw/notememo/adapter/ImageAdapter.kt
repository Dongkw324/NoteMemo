package com.kdw.notememo.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kdw.notememo.R
import com.kdw.notememo.databinding.ImageItemBinding

class ImageAdapter(val context: Context)
    : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    var images = mutableListOf<Uri>()

    inner class ViewHolder(val binding: ImageItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(uri: Uri) {
            Glide.with(context).load(uri).error(R.drawable.img_error).into(binding.addedImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(images[position])

        holder.itemView.setOnLongClickListener {
            images.removeAt(position)
            notifyDataSetChanged()
            true
        }
    }

    override fun getItemCount() = images.size
}