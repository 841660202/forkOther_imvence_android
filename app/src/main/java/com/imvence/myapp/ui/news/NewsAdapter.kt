package com.imvence.myapp.ui.news

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import com.imvence.myapp.R

class NewsAdapter(private val datas:List<NewsItem>, private val context:Context):
    RecyclerView.Adapter<NewsAdapter.InnerHolder>()
{
    private var vtypeIndex = mutableMapOf<Int, Int>()
    private val TYPE_TXT = 1
    private val TYPE_PIC = 2
    private val TYPE_TXT_PIC = 3

    open class InnerHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val news_avatar: SimpleDraweeView = itemView.findViewById(R.id.news_avatar)
        val news_nickname: TextView = itemView.findViewById(R.id.news_nickname)
        val news_addtime:TextView = itemView.findViewById(R.id.news_addtime)
    }
    /**绑定文本视图*/
    class TxtHolder(itemView:View):InnerHolder(itemView){
        val news_txt:TextView = itemView.findViewById(R.id.news_txt)
    }
    /**绑定图片视图*/
    class PicHolder(itemView:View):InnerHolder(itemView){
        val news_thumb:SimpleDraweeView = itemView.findViewById(R.id.news_thumb)
    }
    /**绑定文本图片视图*/
    class TxtPicHolder(itemView:View):InnerHolder(itemView){
        val news_thumb:SimpleDraweeView = itemView.findViewById(R.id.news_thumb)
        val news_txt:TextView = itemView.findViewById(R.id.news_txt)
    }
    /**创建视图绑定*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerHolder {

        return when(viewType){
            /**图片*/
            TYPE_PIC->{
                val itemView:View = LayoutInflater.from(context).inflate(R.layout.news_pic_item, parent, false)

                return PicHolder(itemView)
            }
            /**文本图片*/
            TYPE_TXT_PIC->{
                val itemView:View = LayoutInflater.from(context).inflate(R.layout.news_txtpic_item, parent, false)

                return TxtPicHolder(itemView)
            }
            /**文本*/
            else->{
                val itemView:View = LayoutInflater.from(context).inflate(R.layout.news_txt_item, parent, false)

                return TxtHolder(itemView)
            }
        }

    }
    /**绑定数据*/
    override fun onBindViewHolder(holder: InnerHolder, position: Int) {
        holder.news_nickname.text = datas[position].nickname
        holder.news_addtime.text = datas[position].addtime
        holder.news_avatar.setImageURI(datas[position].avatar)
        /**绑定数据，根据不同的holder绑定不同的数据*/
        when(vtypeIndex[position]){
            TYPE_PIC->{
                (holder as PicHolder).news_thumb.setImageURI(datas[position].thumbs[0])
            }
            TYPE_TXT_PIC->{
                (holder as TxtPicHolder).news_txt.text = datas[position].content
                (holder as TxtPicHolder).news_thumb.setImageURI(datas[position].thumbs[0])
            }
            TYPE_TXT->{
                (holder as TxtHolder).news_txt.text = datas[position].content
            }
        }


    }
    /**每个item的视图类型*/
    override fun getItemViewType(position: Int): Int {
        // 返回每个item 的type
        return vtypeIndex[position]!!
    }
    /**数量*/
    override fun getItemCount(): Int {
        for(i in datas.indices){
            if(datas[i].content.isEmpty()){
                // 每个item，增加类型字段用于展示
                vtypeIndex[i] = TYPE_PIC
            }else{
                if(datas[i].thumbs.isEmpty()){
                    vtypeIndex[i] = TYPE_TXT
                }else{
                    vtypeIndex[i] = TYPE_TXT_PIC
                }
            }
        }

        return datas.size
    }


/**总结：
 * 大概执行顺序，先getItemCount，进行数据数量确认，并结合数据的类型执行不同的视图渲染
 * 视图渲染过程中 onCreateViewHolder 首先获取视图，过程中，结合viewType，渲染不同的视图
 * 然后 onBindViewHolder绑定视图
 *
 * **/
}