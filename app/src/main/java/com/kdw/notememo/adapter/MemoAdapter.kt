package com.kdw.notememo.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kdw.notememo.R
import com.kdw.notememo.databinding.MemoItemBinding
import com.kdw.notememo.model.Memo

class MemoAdapter(val context: Context) : RecyclerView.Adapter<MemoAdapter.ViewHolder>() {
    var memos = emptyList<Memo>()

    inner class ViewHolder(private val binding: MemoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(memo: Memo) {
            binding.memoTitle.text = memo.title
            binding.memoContent.text = memo.content

            if(memo.imagePath != "") {
                val list = memo.imagePath.split('\n').map { it }.toList()
                Glide.with(context).load(Uri.parse(list[0])).error(R.drawable.img_error).into(binding.memoImage)
            }
            else
                binding.memoImage.setImageResource(R.drawable.insert_photo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoAdapter.ViewHolder {
        return ViewHolder(MemoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MemoAdapter.ViewHolder, position: Int) {
        holder.bind(memos[position])
    }

    override fun getItemCount() = memos.size

}