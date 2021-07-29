package com.kdw.notememo


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kdw.notememo.adapter.MemoAdapter
import com.kdw.notememo.databinding.ActivityMainBinding
import com.kdw.notememo.viewModel.MemoViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var memoAdapter: MemoAdapter
    private lateinit var memoViewModel: MemoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        memoAdapter = MemoAdapter(this)
        binding.memoRecyclerView.adapter = memoAdapter

        val manager = LinearLayoutManager(this)
        binding.memoRecyclerView.layoutManager = manager
        binding.memoRecyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        memoViewModel = ViewModelProvider(this).get(MemoViewModel::class.java)
        memoViewModel.allMemos.observe(this, { items ->
            items.let {
                memoAdapter.setMemoItem(items)
            }
        })

        binding.addMemoButton.setOnClickListener {
            val intent = Intent(this, AddMemoActivity::class.java)
            startActivity(intent)
        }
    }

}