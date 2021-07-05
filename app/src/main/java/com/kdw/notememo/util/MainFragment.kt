package com.kdw.notememo.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.kdw.notememo.MainActivity
import com.kdw.notememo.R
import com.kdw.notememo.databinding.FragmentMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainFragment : BaseFragment() {

    private var _binding : FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }


    companion object{
        @JvmStatic
        fun newInstance() =
                MainFragment().apply{
                    arguments = Bundle().apply {
                    }
                }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addMemo.setOnClickListener {
            replaceFragment(AddFragment.newInstance())
        }

        //val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm:ss", java.util.Locale.getDefault())
        //val currentDate = sdf.format(Date())

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun replaceFragment(fragment : Fragment){
        val fragmentTransition = requireActivity().supportFragmentManager.beginTransaction()

        fragmentTransition.replace(R.id.frame_layout, fragment).addToBackStack(fragment.javaClass.simpleName)
                .commit()
    }
}