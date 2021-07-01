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

class MainFragment : Fragment(R.layout.fragment_main) {

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

    /*
    companion object{
        @JvmStatic
        fun newInstance() =
                MainFragment().apply{
                    arguments = Bundle().apply {
                    }
                }
    }
*/
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addMemo.setOnClickListener {
            var addFragment = Fragment()
            var transaction : FragmentTransaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_layout, addFragment).addToBackStack(addFragment.javaClass.simpleName)
            transaction.commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}