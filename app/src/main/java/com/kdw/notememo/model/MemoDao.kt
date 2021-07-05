package com.kdw.notememo.model

import androidx.room.*

@Dao
interface MemoDao {
    @Query("SELECT * FROM NoteEntity ORDER BY id DESC")
    suspend fun displayMemo() : List<Memo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMemo(memo : Memo)

    @Delete
    suspend fun deleteMemo(memo : Memo)

}