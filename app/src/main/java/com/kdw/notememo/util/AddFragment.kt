package com.kdw.notememo.util


import android.Manifest
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.kdw.notememo.databinding.FragmentAddmemoBinding
import com.kdw.notememo.model.Memo
import com.kdw.notememo.model.MemoDatabase
import com.kdw.notememo.util.function.ItemClick
import com.kdw.notememo.util.function.ItemClickListener
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AddFragment: BaseFragment(), ItemClickListener {

    private var _binding : FragmentAddmemoBinding? = null
    private val binding get() = _binding!!

    private lateinit var thread: Thread
    private lateinit var state : String
    private lateinit var bottomFragment: BottomFragment

    val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.getDefault())
    var currentDate: String? = null

    var selectedColor = "#000000"
    var selectedImageUri = ""

    private val resultLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()){
            isGranted ->
            if(isGranted){
                Log.i("DEBUG", "permission granted")
            } else {
                Log.i("DEBUG", "permission denied")
                Snackbar.make(binding.root, "permission required", Snackbar.LENGTH_SHORT).show()
            }
        }

    private val getContent = registerForActivityResult(
        ActivityResultContracts.GetContent()){
        uri ->
        if(uri != null){
            binding.noteImage.setImageURI(uri)
            binding.noteImage.visibility = View.VISIBLE
            selectedImageUri = uri.toString()
            Log.i("DEBUG", uri.toString())
            Log.i("DEBUG", selectedImageUri.toString())
        } else {
            Toast.makeText(requireContext(), "not image", Toast.LENGTH_SHORT).show()
        }
    }

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

        binding.colorLetter.setOnClickListener {
            bottomFragment = ItemClick.newInstance(this)
            bottomFragment.show(requireActivity().supportFragmentManager, "bottom sheet dialog")

        }

        binding.imageInsert.setOnClickListener {
            if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_GRANTED){
                startContent()
            } else {
                Toast.makeText(requireContext(), "not permission", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startContent(){
        getContent.launch("image/*")
    }

    private fun saveMemo(){
        currentDate = sdf.format(Date())

        if(binding.inputTitle.text.isNullOrEmpty()){
            Toast.makeText(context, "Write the memo title", Toast.LENGTH_SHORT).show()
            return
        }

        if(binding.inputMemo.text.isNullOrEmpty()){
            Toast.makeText(context, "Write the memo content", Toast.LENGTH_SHORT).show()
            return
        }

        launch {
            val memo = Memo()
            memo.title = binding.inputTitle.text.toString()
            memo.content = binding.inputMemo.text.toString()
            memo.memoTime = currentDate
            memo.color = selectedColor
            memo.imagePath = selectedImageUri

            context?.let {
                MemoDatabase.getInstance(it).memoDao()
                        .insertMemo(memo)

                requireActivity().supportFragmentManager.popBackStack()
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


    override fun onGreenColorClick() {
        Toast.makeText(requireContext(), "Green", Toast.LENGTH_SHORT).show()
        selectedColor = "#00FF0A"
        binding.noteColor.setBackgroundColor(Color.parseColor(selectedColor))
        bottomFragment.dismiss()
    }

    override fun onBlueColorClick() {
        Toast.makeText(requireContext(), "Blue", Toast.LENGTH_SHORT).show()
        selectedColor = "#0027FD"
        binding.noteColor.setBackgroundColor(Color.parseColor(selectedColor))
        bottomFragment.dismiss()
    }

    override fun onRedColorClick() {
        Toast.makeText(requireContext(), "Red", Toast.LENGTH_SHORT).show()
        selectedColor = "#FF0000"
        binding.noteColor.setBackgroundColor(Color.parseColor(selectedColor))
        bottomFragment.dismiss()
    }

    override fun onYellowColorClick() {
        Toast.makeText(requireContext(), "Yellow", Toast.LENGTH_SHORT).show()
        selectedColor = "#FFE500"
        binding.noteColor.setBackgroundColor(Color.parseColor(selectedColor))
        bottomFragment.dismiss()
    }

    override fun onOrangeColorClick() {
        Toast.makeText(requireContext(), "Orange", Toast.LENGTH_SHORT).show()
        selectedColor = "#FF9800"
        binding.noteColor.setBackgroundColor(Color.parseColor(selectedColor))
        bottomFragment.dismiss()
    }

    override fun onPinkColorClick() {
        Toast.makeText(requireContext(), "Pink", Toast.LENGTH_SHORT).show()
        selectedColor = "#DD5CF3"
        binding.noteColor.setBackgroundColor(Color.parseColor(selectedColor))
        bottomFragment.dismiss()
    }

    override fun onPurpleColorClick() {
        Toast.makeText(requireContext(), "Purple", Toast.LENGTH_SHORT).show()
        selectedColor = "#7832F4"
        binding.noteColor.setBackgroundColor(Color.parseColor(selectedColor))
        bottomFragment.dismiss()
    }

}
