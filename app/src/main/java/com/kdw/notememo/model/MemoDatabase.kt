package com.kdw.notememo.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Memo::class], version = 1, exportSchema = false)
abstract class MemoDatabase: RoomDatabase() {

    abstract fun memoDao(): MemoDao

    companion object{
        var memoDatabase : MemoDatabase? = null

        fun getInstance(context : Context) : MemoDatabase{
            if(memoDatabase == null){
                memoDatabase = Room.databaseBuilder(context,
                MemoDatabase::class.java,
                    "memo_db")
                    .build()
            }

            return memoDatabase!!
        }
    }

}