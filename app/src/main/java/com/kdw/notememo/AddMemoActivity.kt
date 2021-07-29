package com.kdw.notememo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kdw.notememo.databinding.ActivityAddMemoBinding

class AddMemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAddMemoBinding.inflate(layoutInflater)

        setContentView(binding.root)


    }
}