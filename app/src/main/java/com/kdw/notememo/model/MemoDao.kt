package com.kdw.notememo.model

import androidx.room.*

@Dao
interface MemoDao {
    @get: Query("SELECT * FROM NoteEntity ORDER BY id DESC")
    val displayMemo : List<Memo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMemo(memo : Memo)

    @Delete
    fun deleteMemo(memo : Memo)

}