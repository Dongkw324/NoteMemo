package com.kdw.notememo.util

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.kdw.notememo.databinding.FragmentAddmemoBinding
import com.kdw.notememo.model.Memo
import com.kdw.notememo.model.MemoDatabase
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class AddFragment: BaseFragment() {

    private var _binding : FragmentAddmemoBinding? = null
    private val binding get() = _binding!!

    private lateinit var thread: Thread
    private lateinit var state : String

    val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.getDefault())
    var currentDate: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAddmemoBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        state = "Active"
        updateTime()

        binding.backButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.saveMemo.setOnClickListener {
            saveMemo()
        }
    }

    private fun saveMemo(){
        currentDate = sdf.format(Date())

        if(binding.inputTitle.text.isNullOrEmpty()){
            Toast.makeText(context, "Write the memo title", Toast.LENGTH_SHORT).show()
        }

        if(binding.inputMemo.text.isNullOrEmpty()){
            Toast.makeText(context, "Write the memo content", Toast.LENGTH_SHORT).show()
        }



        launch {
            val memo = Memo()
            memo.title = binding.inputTitle.text.toString()
            memo.content = binding.inputMemo.text.toString()
            memo.memoTime = currentDate

            context?.let {
                MemoDatabase.getInstance(it).memoDao()
                        .insertMemo(memo)
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object{
        @JvmStatic
        fun newInstance() =
                AddFragment().apply{
                    arguments = Bundle().apply {
                    }
                }
    }

    private fun updateTime(){
        var loop : Boolean = true

        thread = Thread(object : Runnable {
            override fun run() {
                while (loop) {
                    try {
                        update()
                        Thread.sleep(1000)
                    } catch (e: InterruptedException) {
                        loop = false
                    }
                }
            }

            fun update() {
                requireActivity().runOnUiThread {
                    currentDate = sdf.format(Date())
                    binding.memoTime.text = currentDate
                }
            }
        })

        thread.start()
    }

    override fun onStop() {
        super.onStop()
        state = "DeActive"
        thread.interrupt()
    }

    override fun onResume() {
        super.onResume()
        state="Active"
    }
}
