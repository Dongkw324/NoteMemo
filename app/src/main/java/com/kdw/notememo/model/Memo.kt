package com.kdw.notememo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "NoteEntity")
data class Memo(

    @PrimaryKey(autoGenerate = true)
    var id : Int,

    @ColumnInfo(name="title")
    var title : String,

    @ColumnInfo(name="content")
    var content : String,

    @ColumnInfo
    var memoTime : String,

    @ColumnInfo(name="img_path")
    var imagePath : String,

    @ColumnInfo(name="color_text")
    var color : String

){
    override fun toString(): String {
        return "$title : $memoTime"
    }
}