package com.example.zhang.hoh.adapter.collection;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.sdk.utils.BitmapUtils;
import com.example.zhang.hoh.R;
import com.example.zhang.hoh.bean.collection.collectionMainListViewBean;

import java.util.List;

public class collectionListViewAdapter extends BaseAdapter {
    private List<collectionMainListViewBean> mList;
    private LayoutInflater mInflater;
    private Context mContext;

    public collectionListViewAdapter(Context context, List<collectionMainListViewBean> mList, ListView listView) {
        this.mList = mList;
        //加载布局
        mInflater=LayoutInflater.from(context);
        mContext=context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        //第一次加载
        if (convertView==null){
            //找到布局文件，给viewHolder中的控件初始化
            viewHolder=new ViewHolder();
            //加载item
            convertView=mInflater.inflate(R.layout.collection_main_lv_item,null);
            viewHolder.itemBg=convertView.findViewById(R.id.collection_main_lv_item_bg_iv);
            viewHolder.itemName=convertView.findViewById(R.id.collection_main_lv_item_name_tv);
            viewHolder.itemLocal=convertView.findViewById(R.id.collection_main_lv_item_local_tv);
            viewHolder.itemSelected=convertView.findViewById(R.id.collection_main_lv_item_collected_iv);
            viewHolder.itemLocalIcon=convertView.findViewById(R.id.collection_main_lv_item_local_iv);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }


        //写值
        Glide.with(mContext)
                .load(mList.get(i).getBg())
                 .transform(new com.bumptech.glide.load.resource.bitmap.CenterCrop(), new com.example.sdk.utils.GlideUtils.GlideRoundTransform(mContext,50))
                .into(viewHolder.itemBg);//背景



        
        viewHolder.itemName.setText(mList.get(i).getName());//名字
        Glide.with(mContext)
                .load(R.drawable.collection_local)
                .into(viewHolder.itemLocalIcon);//local图标
        viewHolder.itemLocal.setText(mList.get(i).getLocal());//local名字
        Glide.with(mContext)
                .load(R.drawable.collection_item_collected)
                .into(viewHolder.itemSelected);//右上角图标


        return convertView;
    }

    class ViewHolder{
        public ImageView itemBg;
        public TextView itemName;
        public TextView itemLocal;
        public ImageView itemLocalIcon;
        public ImageButton itemSelected;
    }

}
