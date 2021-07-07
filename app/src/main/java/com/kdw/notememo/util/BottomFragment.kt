package com.kdw.notememo.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kdw.notememo.R

class BottomFragment : BottomSheetDialogFragment() {

    companion object{
        fun newInstance() : BottomFragment{
            var args = Bundle()
            val fragment = BottomFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bottom_sheet_item, container, false)
    }
}