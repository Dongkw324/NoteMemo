package com.kdw.notememo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kdw.notememo.databinding.ItemMemoBinding
import com.kdw.notememo.model.Memo
import com.kdw.notememo.util.DeleteMemo

class MemoAdapter(private val arrayList: List<Memo>,
                  private val delete: DeleteMemo) :
    RecyclerView.Adapter<MemoAdapter.MemoViewHolder>() {


    class MemoViewHolder(val binding : ItemMemoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder {
        return MemoViewHolder(ItemMemoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
        holder.binding.noteTitle.text = arrayList[position].title
        holder.binding.noteContent.text = arrayList[position].content
        holder.binding.noteDateTime.text = arrayList[position].memoTime

        holder.binding.root.setOnLongClickListener {
            delete.deleteMemo(arrayList[position])
            true
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }


}