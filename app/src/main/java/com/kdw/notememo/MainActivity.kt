package com.kdw.notememo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.kdw.notememo.databinding.ActivityMainBinding
import com.kdw.notememo.util.AddFragment
import com.kdw.notememo.util.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragment(MainFragment.newInstance())
    }

    private fun replaceFragment(fragment : Fragment){
        val fragmentTransition = supportFragmentManager.beginTransaction()

        fragmentTransition.replace(R.id.frame_layout, fragment).addToBackStack(fragment.javaClass.simpleName)
                .commit()
    }

}