package com.kdw.notememo.util


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.kdw.notememo.adapter.ImageAdapter
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

    private var selectedColor = "#000000"
    private var selectedImageUri: String? = null

    private var memoId = -1
    private lateinit var imageAdapter: ImageAdapter
    private var imageList = mutableListOf<Uri>()

    private val resultLauncher =
        this.registerForActivityResult(
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
        ActivityResultContracts.StartActivityForResult()){
            result: ActivityResult ->
        val selectedUrl: Uri = result.data?.data ?: return@registerForActivityResult

        imageAdapter.addImage(selectedUrl)

       // binding.noteImage.setImageURI(selectedUrl)
       // binding.noteImage.layoutParams.height=300
       // binding.noteImage.visibility = View.VISIBLE
       // selectedImageUri = getPathFromUri(selectedUrl!!)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        memoId = requireArguments().getInt("memoId", -1)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddmemoBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        state = "Active"
        updateTime()

        imageAdapter = ImageAdapter(requireContext(), imageList)
        binding.imageRecyclerview.adapter = imageAdapter
        binding.imageRecyclerview.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.imageRecyclerview.addItemDecoration(DividerItemDecoration(requireActivity(), LinearLayoutManager.HORIZONTAL))

        if(memoId != -1){
            launch {
                context?.let {
                    val memo = MemoDatabase.getInstance(it).memoDao().getSpecificMemo(memoId)
                    binding.inputTitle.setText(memo.title)
                    binding.inputMemo.setText(memo.content)
                    imageAdapter.setImages(makeUriList(memo.imagePath!!))
                    if(memo.color != null){
                        binding.noteColor.setBackgroundColor(Color.parseColor(memo.color))
                        selectedColor = memo.color!!
                    }

                    /*
                    if(memo.imagePath != null){
                        selectedImageUri = memo.imagePath!!
                        binding.noteImage.setImageBitmap(BitmapFactory.decodeFile(memo.imagePath))
                        binding.noteImage.visibility = View.VISIBLE
                    } else {
                        binding.noteImage.visibility = View.GONE
                    }

                     */
                }
            }
        }

        binding.backButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.saveMemo.setOnClickListener {
            if(memoId != -1){
                updateMemo()
            } else {
                saveMemo()
            }
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
                resultLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }

    private fun makeString(list: List<Uri>): String{
        if(list.isEmpty()){
            return ""
        }
        val sb = StringBuffer()

        val sz = list.size
        for(i in 0 until sz){
            sb.append(list[i].toString())
            if(i != sz-1){
                sb.append('\n')
            }
        }

        return sb.toString()
    }

    private fun makeUriList(str: String): List<Uri>{
        if(str.equals("")){
            return emptyList()
        }

        return str.split('\n').map{ Uri.parse(it) }.toList()
    }

    /*
    private fun getPathFromUri(contentUri: Uri): String?{
        var filePath: String? = null
        var cursor = requireActivity().contentResolver.query(contentUri, null, null, null, null)
        if(cursor == null){
            filePath = contentUri.path
        } else {
            cursor.moveToFirst()
            var index = cursor.getColumnIndex("_data")
            filePath = cursor.getString(index)
            cursor.close()
        }
        return filePath
    }


     */

    private fun startContent(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        intent.type = "image/*"
        getContent.launch(intent)
    }

    private fun updateMemo(){
        selectedImageUri = makeString(imageList)

        launch {
            context?.let {
                val memo = MemoDatabase.getInstance(it).memoDao().getSpecificMemo(memoId)
                memo.title = binding.inputTitle.text.toString()
                memo.content = binding.inputMemo.text.toString()
                memo.memoTime = currentDate
                memo.color = selectedColor
                memo.imagePath = selectedImageUri

                MemoDatabase.getInstance(it).memoDao().updateMemo(memo)
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
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

        selectedImageUri = makeString(imageList)
        Log.i("DEBUG", selectedImageUri.toString())

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
        var loop = true

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
