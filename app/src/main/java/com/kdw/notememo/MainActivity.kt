package com.kdw.notememo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kdw.notememo.util.MainFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var mainFragment = MainFragment()
        supportFragmentManager.beginTransaction().replace(R.id.frame_layout, mainFragment).commit()
    }
}