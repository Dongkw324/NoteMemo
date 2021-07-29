package com.kdw.notememo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kdw.notememo.adapter.ImageAdapter
import com.kdw.notememo.databinding.ActivityAddMemoBinding
import com.kdw.notememo.viewModel.MemoViewModel

class AddMemoActivity : AppCompatActivity() {
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var memoViewModel: MemoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAddMemoBinding.inflate(layoutInflater)

        setContentView(binding.root)

        imageAdapter = ImageAdapter(this)

    }
}