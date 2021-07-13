package com.kdw.notememo.adapter

import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kdw.notememo.databinding.ItemMemoBinding
import com.kdw.notememo.model.Memo
import com.kdw.notememo.util.function.DeleteMemo
import com.kdw.notememo.util.function.ItemUpdate

class MemoAdapter(private val arrayList: List<Memo>,
                  private val delete: DeleteMemo,
                  private val update : ItemUpdate) :
    RecyclerView.Adapter<MemoAdapter.MemoViewHolder>() {


    class MemoViewHolder(val binding : ItemMemoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder {
        return MemoViewHolder(ItemMemoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
        holder.binding.noteTitle.text = arrayList[position].title
        holder.binding.noteContent.text = arrayList[position].content
        holder.binding.noteDateTime.text = arrayList[position].memoTime

        if(arrayList[position].color != null){
            holder.binding.cardView.setCardBackgroundColor(Color.parseColor(arrayList[position].color))
        }

        if(arrayList[position].imagePath != null){
            holder.binding.noteImageView.layoutParams.height=200
            holder.binding.noteImageView.setImageBitmap(BitmapFactory.decodeFile(arrayList[position].imagePath))
            holder.binding.noteImageView.visibility = View.VISIBLE
            Log.i("DEBUG", arrayList[position].imagePath.toString())
        } else {
            holder.binding.noteImageView.visibility = View.GONE
        }

        holder.binding.root.setOnLongClickListener {
            delete.deleteMemo(arrayList[position])
            true
        }

        holder.binding.root.setOnClickListener {
            val memoId = arrayList[position].id
            update.itemUpdateClick(memoId!!)
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }


}