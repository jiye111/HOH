package com.example.zhang.hoh.adapter.list;

import android.content.Context;
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
import com.example.zhang.hoh.R;
import com.example.zhang.hoh.bean.collection.collectionMainListViewBean;
import com.example.zhang.hoh.bean.list.lineupMainListViewBean;

import java.util.List;

public class lineupListViewAdapter extends BaseAdapter {
    private List<lineupMainListViewBean> mList;
    private LayoutInflater mInflater;
    private Context mContext;

    public lineupListViewAdapter(Context context, List<lineupMainListViewBean> mList, ListView listView) {
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
            convertView=mInflater.inflate(R.layout.lineup_main_lv_item,null);
            viewHolder.img=convertView.findViewById(R.id.lineup_main_item_img_iv);
            viewHolder.localIcon=convertView.findViewById(R.id.lineup_main_item_local_icon);
//            viewHolder.downArraw=convertView.findViewById(R.id.lineup_main_item_downArraw_iv);
            viewHolder.name=convertView.findViewById(R.id.lineup_main_item_name_tv);
            viewHolder.local=convertView.findViewById(R.id.lineup_main_item_local_tv);
            viewHolder.mealNumber=convertView.findViewById(R.id.lineup_main_item_mealNumber);
            viewHolder.mealNumberValue=convertView.findViewById(R.id.lineup_main_item_mealNumber_tv);
            viewHolder.mealTime=convertView.findViewById(R.id.lineup_main_item_mealTime);
            viewHolder.mealTimeValue=convertView.findViewById(R.id.lineup_main_item_mealTime_tv);
            viewHolder.orderTime=convertView.findViewById(R.id.lineup_main_item_orderTime);
            viewHolder.orderTimeValue=convertView.findViewById(R.id.lineup_main_item_orderTime_tv);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        //写变化的值
        Glide.with(mContext)
                .load(mList.get(i).getImg())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                .into(viewHolder.img);//右边图片
        viewHolder.name.setText(mList.get(i).getName());//名字
        viewHolder.local.setText(mList.get(i).getLocal());//地址
        viewHolder.mealNumberValue.setText(mList.get(i).getMealNumber());//用餐人熟
        viewHolder.mealTimeValue.setText(mList.get(i).getMealTime());//用餐时间
        viewHolder.orderTimeValue.setText(mList.get(i).getOrderTime());//预定时间

       return convertView;
    }

    class ViewHolder{
        public ImageView img;
        public ImageView localIcon;
       // public ImageView downArraw;
        public TextView name;
        public TextView local;
        public TextView mealNumber;
        public TextView mealNumberValue;
        public TextView mealTime;
        public TextView mealTimeValue;
        public TextView orderTime;
        public TextView orderTimeValue;


    }

}
