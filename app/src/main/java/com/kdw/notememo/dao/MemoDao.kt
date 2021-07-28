package com.kdw.notememo.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kdw.notememo.model.Memo

@Dao
interface MemoDao {
    @Query("SELECT * FROM MemoEntity ORDER BY id")
    fun getAllMemo() : LiveData<List<Memo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(memo: Memo)

    @Delete
    suspend fun delete(memo: Memo)

}