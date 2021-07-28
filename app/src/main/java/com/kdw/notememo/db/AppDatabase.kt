package com.kdw.notememo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kdw.notememo.dao.MemoDao
import com.kdw.notememo.model.Memo

@Database(entities = [Memo::class], version = 1)
abstract class AppDatabase() : RoomDatabase() {

    abstract fun memoDao() : MemoDao

    companion object{

        /* @volatile = 접근 가능한 변수의 값을 cache를 통해 사용하지 않고 thread가 직접 main memory에
        접근하게 하여 동기화*/
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context) : AppDatabase? {
            if(INSTANCE == null){
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "memo_db"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}