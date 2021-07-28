package com.kdw.notememo.repository

import androidx.lifecycle.LiveData
import com.kdw.notememo.dao.MemoDao
import com.kdw.notememo.model.Memo

class MemoRepository(private val memoDao: MemoDao) {

    val allItems : LiveData<List<Memo>> = memoDao.getAllMemo()

    suspend fun insert(memo: Memo) {
        memoDao.insert(memo)
    }

    suspend fun delete(memo: Memo) {
        memoDao.delete(memo)
    }
}