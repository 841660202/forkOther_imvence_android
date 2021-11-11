package com.imvence.myapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ScaleGestureDetector
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.imvence.myapp.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class HomeFragment : Fragment() {
    // vm提供数据监听
    private lateinit var homeViewModel: HomeViewModel
    // 数据存放
    private val msgList: MutableList<MsgItem> = ArrayList() //列表内容容器
    // 适配器
    private var msgAdapter: RecyclerView.Adapter<*>? = null
    // 刷新
    private lateinit var homeRefresh:SwipeRefreshLayout
    // 当前页面
    private var page = 1
    /**创建视图*/
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        //homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        /**加载fragment布局*/
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        //val textView: TextView = root.findViewById(R.id.text_home)
        //homeViewModel.text.observe(viewLifecycleOwner, Observer {
        //    textView.text = it
        //})

        //val contentView: ConstraintLayout = root.findViewById(R.id.HomeFragment)

        //contentView.addView(inflater.inflate(R.layout.msg_item, container, false))
        /**给视图增加管理*/
        val messageContent: RecyclerView = root.findViewById(R.id.messageContent)
        messageContent.layoutManager = LinearLayoutManager(root.context)
        /**适配器*/
        msgAdapter = MsgAdapter(msgList, root.context)
        messageContent.adapter = msgAdapter
        /**刷新*/
        homeRefresh = root.findViewById(R.id.msgRefresh)
        homeRefresh.setOnRefreshListener {
            // 刷新，重置和获取新的数据
            this.page = 1
            this.msgList.clear()
            this.initRequest()

        }
        /**监听recycle滚动*/
        messageContent.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if(!messageContent.canScrollVertically(0)){
                    page += 1
                    initRequest()
                }
            }
        })

        this.initRequest()  //假设从这里开始加载数据

        return root
    }

    private fun initRequest(){
        var start = (page-1)*10
        var end   = page*10

        for (i in start..end){
            var rand = (0..10).random()

            msgList.add(i, MsgItem(
                    i.toString(),
                    "关注程序阿源，带你从头到尾开发APP",
                    "$i 小时前",
                    "程序阿源$i",
                    "https://asm.api.qt750.com/Uploads/image/tt/$rand.jpg"
            ))
        }

        msgAdapter?.notifyDataSetChanged()
        /**设置SwipRefreshLayout的刷新状态*/
        if(homeRefresh.isRefreshing){
            homeRefresh.isRefreshing = false
        }
    }
}