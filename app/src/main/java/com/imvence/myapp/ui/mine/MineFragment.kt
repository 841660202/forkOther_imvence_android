package com.imvence.myapp.ui.mine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.imvence.myapp.R

class MineFragment : Fragment() {
    /**定义文件中一些全局变量*/
    private lateinit var mineViewModel: MineViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /**获取viewModel*/
        mineViewModel = ViewModelProvider(this).get(MineViewModel::class.java)
        /**获取根视图*/
        val root = inflater.inflate(R.layout.fragment_mine, container, false)
        /**获取其他视图*/
        val textView: TextView = root.findViewById(R.id.text_mine)

        /**监听viewModel数据变化，作出相应的改变*/
        mineViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        return root
    }
}