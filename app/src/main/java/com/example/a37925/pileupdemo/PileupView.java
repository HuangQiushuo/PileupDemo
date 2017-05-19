package com.example.a37925.pileupdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Qiushuo Huang on 2017/5/17.
 */

public class PileupView extends ViewGroup {
    private List<String> images;
    private int imageSize = 70;
    private int maxNum = Integer.MAX_VALUE;
    private float ratio = 0.5f;

    public PileupView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public PileupView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public PileupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr){
        images = new ArrayList<>();
        TypedArray arr = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PileupView, defStyleAttr, 0);
        try {
            imageSize = (int)arr.getDimension(R.styleable.PileupView_pileup_imageSize, imageSize);
            maxNum = arr.getInt(R.styleable.PileupView_pileup_maxNum, maxNum);
            ratio = arr.getFloat(R.styleable.PileupView_pileup_ratio, ratio);
        } finally {
            arr.recycle();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int nextLeft = 0;
        for(int i = 0; i < getChildCount(); i++) {
            final View child = getChildAt(i);
            child.layout(nextLeft, 0, nextLeft+imageSize, imageSize);
            nextLeft += imageSize*(1-ratio);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        for(int i=0;i<getChildCount();i++){
            getChildAt(i).getLayoutParams().height = imageSize;
            getChildAt(i).getLayoutParams().width = imageSize;
        }

        int width = 0;
        int height = 0;
        int count = getChildCount();
        if(count!=0){
            width = (int)(imageSize*(count-1)*(1-ratio)+imageSize);
            height = imageSize;
        }

        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? sizeWidth
                : width, (heightMode == MeasureSpec.EXACTLY) ? sizeHeight
                :  height);
    }

    public void setImages(List<String> images) {
        this.images = images;
        this.removeAllViews();
        for(int i=0;i<images.size()&&i<maxNum;i++){
            addView(getImageView(images.get(i)));
        }
        requestLayout();
    }

    public View getImageView(String s){
        View imageView = new View(getContext());
        ColorDrawable colorDrawable = new ColorDrawable();
        colorDrawable.setColor(Color.parseColor(s));
        imageView.setBackground(colorDrawable);
        return imageView;
    }

    public void setImageSize(int imageSize) {
        this.imageSize = imageSize;
        requestLayout();
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
        requestLayout();
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
        requestLayout();
    }
}
