package com.kdw.notememo.util

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kdw.notememo.R
import com.kdw.notememo.databinding.BottomSheetItemBinding
import com.kdw.notememo.databinding.FragmentAddmemoBinding

class BottomFragment : BottomSheetDialogFragment() {

    private var _binding : BottomSheetItemBinding? = null
    private val binding get() = _binding!!

    companion object{
        fun newInstance() : BottomFragment{
            var args = Bundle()
            val fragment = BottomFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = BottomSheetItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTextColor()
    }

    private fun setTextColor(){
        binding.colorGreen.setOnClickListener {
            Toast.makeText(requireContext(), "Green", Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }
}