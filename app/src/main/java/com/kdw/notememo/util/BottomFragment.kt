package com.kdw.notememo.util

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kdw.notememo.databinding.BottomSheetItemBinding
import com.kdw.notememo.util.function.ItemClickListener


class BottomFragment(private var itemClickListener: ItemClickListener) : BottomSheetDialogFragment(){

    private var _binding : BottomSheetItemBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = BottomSheetItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTextColor()
    }

    private fun setTextColor(){
        binding.colorGreen.setOnClickListener {
            itemClickListener.onGreenColorClick()
        }

        binding.colorBlue.setOnClickListener {
            itemClickListener.onBlueColorClick()
        }

        binding.colorRed.setOnClickListener {
            itemClickListener.onRedColorClick()
        }

        binding.colorYellow.setOnClickListener {
            itemClickListener.onYellowColorClick()
        }
    }

}