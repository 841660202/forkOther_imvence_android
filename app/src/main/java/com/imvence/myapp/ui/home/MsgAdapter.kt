package com.imvence.myapp.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.imvence.myapp.R

class MsgAdapter(private val datas: List<MsgItem>, private val context: Context) :RecyclerView.Adapter<MsgAdapter.InnerHolder>() {


    // 创建视图持有者
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MsgAdapter.InnerHolder {
        var itemView: View = LayoutInflater.from(context).inflate(R.layout.msg_item, p0, false)
        return InnerHolder(itemView)
    }

    override fun getItemCount(): Int = datas.size

    // 视图持有者类【作用是为了，从子视图获取控件并处理】
    class InnerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val msg_avatar: SimpleDraweeView = itemView.findViewById(R.id.msg_avatar)
        val msg_nickname: TextView = itemView.findViewById(R.id.msg_nickname)
        val msg_day: TextView = itemView.findViewById(R.id.msg_day)
        val msg_content: TextView = itemView.findViewById(R.id.msg_content)
    }
    // 绑定数据
    override fun onBindViewHolder(p0: MsgAdapter.InnerHolder, p1: Int) {
        p0.msg_nickname.text = datas[p1].nickname
        p0.msg_day.text = datas[p1].day
        p0.msg_content.text = datas[p1].content
        p0.msg_avatar.setImageURI(datas[p1].avatar)
    }

}