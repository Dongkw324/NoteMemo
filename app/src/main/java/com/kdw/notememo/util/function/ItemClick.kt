package com.kdw.notememo.util.function

import com.kdw.notememo.util.BottomFragment

class ItemClick {
    companion object{
        fun newInstance(itemClickListener: ItemClickListener): BottomFragment{
            return BottomFragment(itemClickListener)
        }
    }
}