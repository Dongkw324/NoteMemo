package com.kdw.notememo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "MemoEntity")
data class Memo(
    @PrimaryKey(autoGenerate = true)
    var id : Int? = null,
    @ColumnInfo(name = "memoTitle")
    var title: String,
    @ColumnInfo(name = "memoContent")
    var content: String,
    @ColumnInfo(name = "images")
    var imagePath: String = ""
): Serializable