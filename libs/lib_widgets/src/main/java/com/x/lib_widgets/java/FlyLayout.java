package com.x.lib_widgets.java;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by MG_ZXC on 2018/3/26.
 * 1、自己的padding
 * 2、孩子的margin
 * 3、解决子控件的margin需要获取LayoutParams lp = child.getLayoutParams();
 * 4、考虑孩子gone情况
 * 5、孩子的宽度不会超过此控件的宽度,超出部分不显示
 * 6、所有孩子的高度都一致，宽度可能不一致
 * 7、此控件具有运行时确定的高度（固定或是match_parent）
 */

public class FlyLayout extends ViewGroup {
    public FlyLayout(Context context) {
        super(context,null);
    }

    public FlyLayout(Context context, AttributeSet attrs) {
        super(context, attrs,0);
    }

    public FlyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec 不是控件的宽度和高度是测量模式和宽度、高度的组合值
     *  1、ViewGroup没有重新onMeasure方法去遍历测量孩子的宽度和高度
     *  所以需要手动测量， 遍历所有的子控件并测量每个子控件的宽度和高度
     *  2、测量孩子的宽度和高度用ViewGroup的measureChild方法
     *  3、获取测量模式。测量模式是32int值的高两位来表示  00 -->UNSPECIFIED  01--->EXACTLY  10---->AT_MOST
     *      public static int getMode(int measureSpec) {
     *           return (measureSpec & MODE_MASK);
     *      }
     *  4、获取测量的高度或宽度值，widthMeasureSpec， heightMeasureSpec低30位表示具体高度或宽度
     *      public static int getSize(int measureSpec) {
     *           return (measureSpec & ~MODE_MASK);
     *      }
     *  5、widthMeasureSpecStr--->MeasureSpec: AT_MOST 320      ---->  android:layout_width="wrap_content"
     *      heightMeasureSpecStr--->MeasureSpec: EXACTLY 325   ----->  android:layout_height="match_parent"
     *     heightMeasureSpecStr--->MeasureSpec: UNSPECIFIED  0 ------>控件的父控件 ScrollView HorizontalScrollView
     *
     *  控件测量模式：
     *   A、UNSPECIFIED 如果控件的父控件是 ScollView那么子控件的高度的测测量模式就是UNSPECIFIED，如果父控件是HorizontalScrollView
     *      那么子控件的宽度就是UNSPECIFIED,当控件的测量模式是UNSPECIFIED那么控件的宽度和高度的值都是0,UNSPECIFIED控件的宽度和高度父不能指定，
     *      由子控件自己来决定
     *   B、EXACTLY  当控件的宽度或者高度是match_parent或指定具体的值
     *   C、AT_MOST  当控件的宽度或高度是wrap_content 的时候，控件的宽度或者高度由控件内容来决定，但是宽度或者高度不能超过父控件的宽度或高度
     *      如果测量模式是AT_MOST（wrap_content ） 那么最终得到的值===> 是自己内容的大小和父控件的大小的最小值
     *      size ==Math.min(自己的内容的大小， 父控件的大小)
     *
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {

            View childView = getChildAt(i);
            //测量每个子控件的宽度和高度，只有先测量孩子，那么在后面才能得到孩子的宽、高 childView.getMeasuredWidth();
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
        //获得宽高的测量模式
        int widthMeasureSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMeasureSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        //如果测量模式是UNSPECEPCCET 宽度myParentGiveWidth=0 父控件的给予值
        int myParentGiveWidth = widthMeasureSpecMode == MeasureSpec.UNSPECIFIED ? Integer.MAX_VALUE : MeasureSpec.getSize(widthMeasureSpec);
        int myParentGiveHeight = MeasureSpec.getSize(heightMeasureSpec);
        //孩子的宽高
        int myMeasureWidth = 0; //测量的宽度，最宽那一行
        int myMeadureHeight = 0; //

        int startX = getPaddingLeft();
        int startY = getPaddingTop();
        int childViewUseWidth = 0;
        int childViewUseLineHight = 0;

        for (int i = 0; i < childCount; i++) {

            View childView = getChildAt(i);

            if (childView.getVisibility() == GONE) {

                continue;
            }
            //获取每个子控件的layoutParams
            // java.lang.ClassCastException: android.view.ViewGroup$LayoutParams cannot be cast to android.view.ViewGroup$MarginLayoutParams
            MarginLayoutParams layoutParams = (MarginLayoutParams) childView.getLayoutParams(); //复写 generateLayoutParams方法

            int childViewMeasuredWidth = childView.getMeasuredWidth();
            int childViewMeasuredHeight = childView.getMeasuredHeight();

            //startX 变化为0 就换行， 每个子控件在摆放之前，判断剩余控件是否足够，用startX + childViewMeasuredWidth是否大于整个控件的宽度
            //判断的时候考虑PaddingRight
            //考虑了子控件自己的margin值，每个子控件占据的宽度：childViewMeasuredWidth + leftMargin + rightMargin
            childViewUseWidth = childViewMeasuredWidth + layoutParams.leftMargin + layoutParams.rightMargin;
            if (startX + /*childViewMeasuredWidth*/childViewUseWidth > myParentGiveWidth - getPaddingRight()) {

                startX = getPaddingLeft();

                //换行的时候，上一行使用的高度以一行的最高的为准
                startY += /*childViewMeasuredHeight*/childViewUseLineHight; //y左边累加，因为现在所有的子控件高度都一样
            }

            //子控件摆放之后累加startX的值, 考虑每个孩子占据的宽度要加上marginLeft , marginRingt
            startX += /*childViewMeasuredWidth*/childViewUseWidth;

            //计算每一行使用的高度
            childViewUseLineHight = Math.max(childViewUseLineHight, childViewMeasuredHeight + layoutParams.topMargin + layoutParams.bottomMargin);

            //获取记录宽度的最大值
            myMeasureWidth = Math.max(myMeasureWidth, startX + getPaddingRight());

            myMeadureHeight = startY + childViewUseLineHight + getPaddingBottom();
        }


        //调用View的测量方法测量了自己的宽度和高度
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //自己根据测量模式去指定宽度和高度
        setMeasuredDimension(getSize(widthMeasureSpecMode, myParentGiveWidth, myMeasureWidth), getSize(heightMeasureSpecMode, myParentGiveHeight, myMeadureHeight));


    }
    /**
     * @param mode           测量模式
     * @param parentGiveSize 父控件指定的宽高
     * @param measureSize    自己测量的宽高
     * @return 根据实际的测量模式指定宽度和高度
     */

    private int getSize(int mode, int parentGiveSize, int measureSize) {
        if (MeasureSpec.EXACTLY == mode) {//父控件所的给值确定
            return parentGiveSize;
        } else if (MeasureSpec.AT_MOST == mode) {//取自己测量的size和父控件给的值的最小值
            return Math.min(parentGiveSize, measureSize);
        } else { //MeasureSpec.UNSPECIFIED
            return measureSize;
        }
    }

//    java.lang.ClassCastException: android.view.ViewGroup$LayoutParams cannot be cast to android.view.ViewGroup$MarginLayoutParams
    //问题的解决，重新generateLayoutParams方法，默认方法返回LayoutParams
    @Override
    public MarginLayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int startX = getPaddingLeft();
        int startY = getPaddingTop();
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();

        //每个子控件占据的宽度
        int childViewUseWidth = 0;
        int childViewUseLineHight = 0;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {


            View childView = getChildAt(i);

            if (childView.getVisibility() == GONE) {

                continue;
            }
            //获取每个子控件的layoutParams
            // java.lang.ClassCastException: android.view.ViewGroup$LayoutParams cannot be cast to android.view.ViewGroup$MarginLayoutParams
            MarginLayoutParams layoutParams = (MarginLayoutParams) childView.getLayoutParams();

            int childViewMeasuredWidth = childView.getMeasuredWidth();
            int childViewMeasuredHeight = childView.getMeasuredHeight();

            //startX 变化为0 就换行， 每个子控件在摆放之前，判断剩余控件是否足够，用startX + childViewMeasuredWidth是否大于整个控件的宽度
            //判断的时候考虑PaddingRight
            //考虑了子控件自己的margin值，每个子控件占据的宽度：childViewMeasuredWidth + leftMargin + rightMargin
            childViewUseWidth = childViewMeasuredWidth + layoutParams.leftMargin + layoutParams.rightMargin;
            if (startX + /*childViewMeasuredWidth*/childViewUseWidth > measuredWidth - getPaddingRight()) {

                startX = getPaddingLeft();

                //换行的时候，上一行使用的高度以一行的最高的为准
                startY += /*childViewMeasuredHeight*/childViewUseLineHight; //y左边累加，因为现在所有的子控件高度都一样
            }


            //摆放子控件
            int leftChildView = startX + layoutParams.leftMargin;//考虑自己的margin
            int topChildView = startY + layoutParams.topMargin;
            int rightChildView = leftChildView + childViewMeasuredWidth;
            int bottomChildView = topChildView + childViewMeasuredHeight;
            //摆放孩子
            childView.layout(leftChildView, topChildView, rightChildView, bottomChildView);

            //子控件摆放之后累加startX的值, 考虑每个孩子占据的宽度要加上marginLeft , marginRingt
            startX += /*childViewMeasuredWidth*/childViewUseWidth;

            //计算每一行使用的高度
            childViewUseLineHight = Math.max(childViewUseLineHight, childViewMeasuredHeight + layoutParams.topMargin + layoutParams.bottomMargin);
        }
    }
}