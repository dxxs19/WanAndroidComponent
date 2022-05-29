package com.x.lib_widgets

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import com.x.lib_base.utils.util.LogUtil

class FlowLayout : ViewGroup {
    private val TAG = javaClass.simpleName

    constructor(context: Context?) : super(context!!)
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!,
        attrs
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context!!, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val childCount = childCount
        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            // 测量每个子控件的宽度和高度，只有先测量孩子，在后面才能得到孩子的宽、高
            measureChild(childView, widthMeasureSpec, heightMeasureSpec)
        }
        val widthMeasureSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMeasureSpecMode = MeasureSpec.getMode(heightMeasureSpec)
        var cusMeasureWidth = 0
        var cusMeasureHeight = 0

        var startX = paddingLeft
        var startY = paddingTop
        var childrenWidth = 0
        var childrenHeight = 0
        val parentGiveWidth = if (widthMeasureSpecMode == MeasureSpec.UNSPECIFIED) {
            Integer.MIN_VALUE
        } else {
            MeasureSpec.getSize(widthMeasureSpec)
        }
        val parentGiveHeight = MeasureSpec.getSize(heightMeasureSpec)

        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            if (childView.visibility == GONE) {
                continue
            }

            val childMeasureWidth = childView.measuredWidth
            val childMeasureHeight = childView.measuredHeight

            val layoutParams = childView.layoutParams as MarginLayoutParams

            childrenWidth = childMeasureWidth + layoutParams.leftMargin + layoutParams.rightMargin
//            LogUtil.e(TAG, "childMeasureWidth : $childMeasureWidth , childMeasureHeight : $childMeasureHeight , startX: $startX")
            if (startX + childrenWidth > parentGiveWidth - paddingRight) {
                // 换行
                startX = paddingLeft
                startY += childrenHeight
            }
            startX += childrenWidth
            childrenHeight = Math.max(childrenHeight, childMeasureHeight + layoutParams.topMargin + layoutParams.bottomMargin)

            cusMeasureWidth = Math.max(cusMeasureWidth, startX + paddingRight)
            cusMeasureHeight = startY + childrenHeight + paddingBottom
        }
        val realWidth = getSize(widthMeasureSpecMode, parentGiveWidth, cusMeasureWidth)
        val realHeight = getSize(heightMeasureSpecMode, parentGiveHeight, cusMeasureHeight)

//        LogUtil.e(TAG, "parentGiveWidth : $parentGiveWidth , parentGiveHeight : $parentGiveHeight ;" +
//                " cusMeasureWidth : $cusMeasureWidth , cusMeasureHeight : $cusMeasureHeight ;" +
//                " realWidth : $realWidth , realHeight : $realHeight")

        setMeasuredDimension(realWidth, realHeight )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val childCount = childCount

        var startX = paddingLeft
        var startY = paddingTop
        var childrenWidth = 0
        var childrenHeight = 0

        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            if (childView.visibility == GONE) {
                continue
            }
            val childMeasureWidth = childView.measuredWidth
            val childMeasureHeight = childView.measuredHeight

            val layoutParams = childView.layoutParams as MarginLayoutParams

            childrenWidth = childMeasureWidth + layoutParams.leftMargin + layoutParams.rightMargin
            if (startX + childrenWidth > measuredWidth - paddingRight) {
                // 换行
                startX = paddingLeft
                startY += childrenHeight
            }


            val cvLeftPadding = startX + layoutParams.leftMargin
            val cvTopPadding = startY + layoutParams.topMargin
            val cvRightPadding = cvLeftPadding + childMeasureWidth
            val cvBottomPadding = cvTopPadding + childMeasureHeight
            childView.layout(cvLeftPadding, cvTopPadding, cvRightPadding, cvBottomPadding)

            startX += childrenWidth
            childrenHeight = Math.max(childrenHeight, childMeasureHeight + layoutParams.topMargin + layoutParams.bottomMargin)
        }
    }

    /**
     * @param mode           测量模式
     * @param parentGiveSize 父控件指定的宽高
     * @param measureSize    自己测量的宽高
     * @return 根据实际的测量模式指定宽度和高度
     */
    private fun getSize(mode: Int, parentGiveSize: Int, measureSize: Int): Int {
        return if (MeasureSpec.EXACTLY == mode) { //父控件所的给值确定
            parentGiveSize
        } else if (MeasureSpec.AT_MOST == mode) { //取自己测量的size和父控件给的值的最小值
            Math.min(parentGiveSize, measureSize)
        } else { //MeasureSpec.UNSPECIFIED
            measureSize
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

}