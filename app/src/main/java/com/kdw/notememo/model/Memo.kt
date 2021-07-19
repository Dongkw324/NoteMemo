package com.kdw.notememo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "NoteEntity")
data class Memo(

    @PrimaryKey(autoGenerate = true)
    var id : Int? = null,

){

}