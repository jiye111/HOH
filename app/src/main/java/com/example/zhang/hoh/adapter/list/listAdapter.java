package com.example.zhang.hoh.adapter.list;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.sdk.utils.BitmapUtils;
import com.example.zhang.hoh.R;
import com.example.zhang.hoh.bean.list.listBottomInRVBean;

import org.w3c.dom.Text;

import java.util.List;

public class listAdapter extends RecyclerView.Adapter<listAdapter.ViewHolder> {
    private List<listBottomInRVBean> mList;
    private Context mContext;

    public listAdapter(Context context,List<listBottomInRVBean> mList) {
        this.mContext=context;
        this.mList = mList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView bg;
        TextView name;
        TextView local;
        ImageView isCollected;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bg=itemView.findViewById(R.id.list_main_item_bg);
            name=itemView.findViewById(R.id.list_main_item_name);
            local=itemView.findViewById(R.id.list_main_item_local);
            isCollected=itemView.findViewById(R.id.list_main_item_iscollected);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_main_item,
                viewGroup,false);
        ViewHolder holder =new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        listBottomInRVBean bean=mList.get(i);

        //减小图片大小
        Bitmap bitmap= BitmapUtils.decodeSampledBitmapFromResoure(Resources.getSystem(),bean.getSrc()
                ,300,400);
        Glide.with(mContext)
                .load(bean.getSrc())
                .placeholder(R.drawable.halfwhite)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(60)))
                .into(viewHolder.bg);
        viewHolder.name.setText(bean.getName());
        viewHolder.local.setText(bean.getLocal());
        if (bean.isCollected()){
            Glide.with(mContext)
                    .load(R.drawable.collection_item_collected)
                    .into(viewHolder.isCollected);
        }else {
            Glide.with(mContext)
                    .load(R.drawable.collection_item_uncollected)
                    .into(viewHolder.isCollected);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


}
