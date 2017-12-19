package com.ireport.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ireport.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Girish on 12/6/2016.
 */

public class ImagePagerAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;
    List<String> viewPagerImages = new ArrayList<String>();

    public ImagePagerAdapter(Context context, List<String> viewPagerImages) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.viewPagerImages = viewPagerImages;
    }

    @Override
    public int getCount() {
        if(viewPagerImages.size() != 0){
            return viewPagerImages.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = layoutInflater.inflate(R.layout.image_viewpager_layout, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.viewPagerItem_image1);

        Picasso.with(context).load(viewPagerImages.get(position))
                .placeholder(R.drawable.refresh_loading)
                .error(R.mipmap.ic_launcher).into(imageView);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
