package com.kdw.notememo.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.kdw.notememo.db.AppDatabase
import com.kdw.notememo.model.Memo
import com.kdw.notememo.repository.MemoRepository
import kotlinx.coroutines.launch

class MemoViewModel(application: Application) : AndroidViewModel(application){

    private val repository: MemoRepository

    val allMemos: LiveData<List<Memo>>

    init {
        val memoDao = AppDatabase.getInstance(application)!!.memoDao()
        repository = MemoRepository(memoDao)
        allMemos = repository.allItems
    }

    fun insert(memo: Memo) = viewModelScope.launch {
        repository.insert(memo)
    }

    fun delete(memo: Memo) = viewModelScope.launch {
        repository.delete(memo)
    }

}