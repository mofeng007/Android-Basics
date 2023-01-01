package com.mofeng.viewpager;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

/**
 * @author 陌风
 * @create 2022-10-15 15:05
 **/
public class MyViewPagerAdapter extends PagerAdapter {
    List<View> myViewList;
    List<String> tabs;
    public MyViewPagerAdapter(List<View> views,List<String> tabs){
        myViewList=views;
        this.tabs=tabs;
    }

    @Override
    public int getCount() {
        return myViewList.size();
    }

    //判断当先显示的视图和你传进来的是不是同一个
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(myViewList.get(position));
        return myViewList.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(myViewList.get(position));
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position);
    }
}
