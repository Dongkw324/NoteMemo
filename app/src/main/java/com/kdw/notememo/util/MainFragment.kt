package com.kdw.notememo.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.kdw.notememo.R
import com.kdw.notememo.adapter.MemoAdapter
import com.kdw.notememo.databinding.FragmentMainBinding
import com.kdw.notememo.model.Memo
import com.kdw.notememo.model.MemoDatabase
import com.kdw.notememo.util.function.DeleteMemo
import com.kdw.notememo.util.function.ItemUpdate
import kotlinx.coroutines.launch
import java.util.*

class MainFragment : BaseFragment(), DeleteMemo, ItemUpdate {

    private var _binding : FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

        binding.memoRecycler.setHasFixedSize(true)
        binding.memoRecycler.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        val db = MemoDatabase.getInstance(requireContext())

        launch {
            context?.let {
                val memos = db.memoDao().displayMemo()
                binding.memoRecycler.adapter = MemoAdapter(memos, this@MainFragment, this@MainFragment)

            }
        }

        binding.addMemo.setOnClickListener {
            replaceFragment(AddFragment.newInstance())
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding.memoRecycler.adapter = null
        _binding = null

    }


    private fun replaceFragment(fragment : Fragment){
        val fragmentTransition = requireActivity().supportFragmentManager.beginTransaction()

        fragmentTransition.replace(R.id.frame_layout, fragment).addToBackStack(fragment.javaClass.simpleName)
                .commit()
    }

    override fun deleteMemo(memo: Memo){
        launch {
            context?.let{
                MemoDatabase.getInstance(it).memoDao().deleteMemo(memo)
                val memos = MemoDatabase.getInstance(it).memoDao().displayMemo()
                binding.memoRecycler.adapter = MemoAdapter(memos, this@MainFragment, this@MainFragment)

            }
        }
    }

    override fun itemUpdateClick(memoId: Int) {
        val fragment: Fragment
        val bundle = Bundle()
        bundle.putInt("memoId", memoId)
        fragment = AddFragment.newInstance()
        fragment.arguments = bundle

        replaceFragment(fragment)
    }
}