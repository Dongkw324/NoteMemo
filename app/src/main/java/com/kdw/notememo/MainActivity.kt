package com.kdw.notememo


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.kdw.notememo.adapter.MemoAdapter
import com.kdw.notememo.databinding.ActivityMainBinding
import com.kdw.notememo.model.Memo
import com.kdw.notememo.viewModel.MemoViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var memoAdapter: MemoAdapter
    private lateinit var memoViewModel: MemoViewModel
    private var pressedTime: Int = 0
    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        permissionCheck()

        memoAdapter = MemoAdapter(this, {memo ->
            val intent = Intent(this, AddMemoActivity::class.java)
            intent.putExtra("memo", memo)
            startActivity(intent)
        }, {
            memo ->
            deleteDialog(memo)
        })

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

    private fun permissionCheck() {

        val permission : PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                return
            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                finish()
            }

        }

        TedPermission.with(this)
                .setPermissionListener(permission)
                .setRationaleMessage("더 많은 기능을 사용하려면 권한이 필요합니다.")
                .setDeniedMessage("[설정] -> [권한] 에서 권한을 설정할 수 있습니다.")
                .setPermissions(android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE,
                                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check()
    }

    private fun deleteDialog(memo: Memo) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("메모를 삭제하겠습니까?")
                .setNegativeButton("아니요") {_, _ ->}
                .setPositiveButton("예") {_, _ ->
                    memoViewModel.delete(memo)
                }
        builder.show()
    }

    override fun onBackPressed() {
        if(pressedTime == 0){
            Toast.makeText(this, "한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
            pressedTime = System.currentTimeMillis().toInt()
        } else {
            val seconds = System.currentTimeMillis().toInt() - pressedTime

            if(seconds > 2000) {
                Toast.makeText(this, "한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
                pressedTime = 0
            } else{
                super.onBackPressed()
            }
        }

    }

    override fun onDestroy() {
        mBinding = null
        super.onDestroy()
    }
}