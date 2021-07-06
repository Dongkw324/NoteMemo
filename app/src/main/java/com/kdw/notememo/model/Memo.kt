package com.kdw.notememo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "NoteEntity")
data class Memo(

    @PrimaryKey(autoGenerate = true)
    var id : Int? = null,

    @ColumnInfo(name="title")
    var title : String? = null,

    @ColumnInfo(name="content")
    var content : String? = null,

    @ColumnInfo(name="memoTime")
    var memoTime : String? = null,

    @ColumnInfo(name="img_path")
    var imagePath : String? = null,

    @ColumnInfo(name="color_text")
    var color : String? = null

){

    override fun toString(): String {
        return "$title : $memoTime"
    }


}