package com.tony.utils.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;
/**
 * GridView
 * Tony 2018-07-07 13:40:24
 * 
 * **/
public class GridViewForScrollView  extends GridView {   
    public GridViewForScrollView(Context context, AttributeSet attrs) {   
        super(context, attrs);   
    }   
   
    public GridViewForScrollView(Context context) {   
        super(context);   
    }   
   
    public GridViewForScrollView(Context context, AttributeSet attrs, int defStyle) {   
        super(context, attrs, defStyle);   
    }   
   
    @Override   
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {   
   
        int expandSpec = MeasureSpec.makeMeasureSpec(   
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);   
        super.onMeasure(widthMeasureSpec, expandSpec);   
    }   
}