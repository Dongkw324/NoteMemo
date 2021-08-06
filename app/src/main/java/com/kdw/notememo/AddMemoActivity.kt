package com.kdw.notememo

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kdw.notememo.adapter.ImageAdapter
import com.kdw.notememo.databinding.ActivityAddMemoBinding
import com.kdw.notememo.model.Memo
import com.kdw.notememo.viewModel.MemoViewModel
import gun0912.tedbottompicker.TedBottomPicker

class AddMemoActivity : AppCompatActivity() {
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var memoViewModel: MemoViewModel
    private var memoId : Int? = null
    private var mBinding: ActivityAddMemoBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityAddMemoBinding.inflate(layoutInflater)

        setContentView(binding.root)

        init(binding)

        binding.saveMemoBtn.setOnClickListener {
            if(TextUtils.isEmpty(binding.inputTitle.text)){
                Toast.makeText(this, "제목을 입력하세요.", Toast.LENGTH_SHORT).show()
            }
            else {
                val title = binding.inputTitle.text.toString()
                val content = binding.inputContent.text.toString()
                val images = imageAdapter.images

                val memo = Memo(memoId, title, content, makeString(images))
                memoViewModel.insert(memo)
                finish()
                /*
                if(intent.hasExtra("memo")){
                    var memo = intent.getSerializableExtra("memo") as Memo
                    memo = Memo(memoId, title, content, makeString(images))
                    memoViewModel.insert(memo)
                    finish()
                }
                else {
                    val memo = Memo(memoId, title, content, makeString(images))
                    memoViewModel.insert(memo)
                    finish()
                }

                 */

            }
        }
    }

    private fun init(binding: ActivityAddMemoBinding) {

        imageAdapter = ImageAdapter(this)
        binding.imgRecyclerView.adapter = imageAdapter
        binding.imgRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.imgRecyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL))

        memoViewModel = ViewModelProvider(this).get(MemoViewModel::class.java)

        if(intent.hasExtra("memo")) {
            val memo = intent.getSerializableExtra("memo") as Memo
            memoId = memo.id
            binding.inputTitle.setText(memo.title)
            binding.inputContent.setText(memo.content)
            imageAdapter.setImages(makeUri(memo.imagePath))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.add_image_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.camera_gallery ->{
                selectPictures()
                true
            }

            R.id.add_url -> {
                val builder = AlertDialog.Builder(this)
                val dialogView = layoutInflater.inflate(R.layout.url_dialog, null)
                val dialogText = dialogView.findViewById<EditText>(R.id.input_url)

                builder.setView(dialogView)
                        .setPositiveButton("추가") { positive, i ->
                            val url = dialogText.text.toString()
                            imageAdapter.addImage(Uri.parse(url))
                        }
                        .setNegativeButton("취소"){negative, i ->

                        }
                        .show()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun selectPictures() {
        TedBottomPicker.with(this)
                .show {
                    imageAdapter.addImage(it)
                }
    }

    private fun makeString(list: List<Uri>) : String{
        if(list.isEmpty())
            return ""

        val s = StringBuffer()
        val listSize = list.size

        for(i in 0 until listSize) {
            s.append(list[i].toString())
            if(i != listSize - 1)
                s.append('\n')
        }

        return s.toString()
    }

    private fun makeUri(str: String): List<Uri> {
        return if(str == "")
            emptyList()
        else
            str.split('\n').map { Uri.parse(it) }.toList()
    }

    override fun onDestroy() {
        mBinding = null
        super.onDestroy()
    }
}