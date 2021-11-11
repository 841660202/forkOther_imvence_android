package com.imvence.myapp.ui.friends

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
/**继承的是view*/
class FriendsQuickIndex(context: Context, attrs: AttributeSet, defStyleAttr:Int): View(context, attrs, defStyleAttr) {
    private var scanvas:Canvas?=null // 画布
    private var paint = Paint() // 笔
    private var groups = mutableMapOf<Int, String>() // 字母数据
    private var cellWidth = 0 // 元素宽度
    private var cellHeight = 0 // 元素高度
    private var indexChangeListener:OnIndexChangeListener?=null // 下标改变监听
    private var curIndex = -1 // 下标

    constructor(context: Context, attrs: AttributeSet):this(context,attrs,0){}
/**绘制*/
    override fun onDraw(canvas: Canvas){
        scanvas = canvas

        for(i in 0 until groups.size){
            val bound = Rect() // 矩形
            paint.getTextBounds(groups[i], 0, 1, bound)
            // 确定每个文字在画布中的位置
            val x = (cellWidth-bound.width())/2
            val y = i*cellHeight + (cellWidth+bound.width())/2
            // 画布上绘制文字
            scanvas!!.drawText(groups[i]!!, x.toFloat(), y.toFloat(), paint)
        }
    }
/**onTouchEvent，触摸事件*/
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(event!!.action == MotionEvent.ACTION_DOWN){
            val y = event.y.toInt()
            val index = y/cellHeight

            if(index<groups.size){
                if(index != curIndex){
                    curIndex = index
                    if(indexChangeListener!=null){
                        indexChangeListener!!.onIndexChange(groups[curIndex])
                    }
                }
            }
        }

        return super.onTouchEvent(event)
    }

    init{
        paint.isAntiAlias = true
        paint.textSize = 26F
    }
/**重新绘制*/
    fun redraw(datas:MutableMap<Int, String>){
        groups = datas
        cellWidth = measuredWidth
        cellHeight = measuredHeight/datas.size
        draw(scanvas)
    }

    interface OnIndexChangeListener{
        fun onIndexChange(group:String?)
    }

    fun setOnIndexChangeListener(changeListener:OnIndexChangeListener){
        indexChangeListener = changeListener
    }
}