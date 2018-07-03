package com.example.administrator.myvideo.MyAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.myvideo.R;
import com.example.administrator.myvideo.bean.Content;
import com.marshon.swipe.SwipeWraper;

import java.util.List;
import java.util.Map;

/**
 * Created by Marshon.Chen on 2016/7/26.
 * DESC:
 */
public abstract class MarshonRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Content> mDatas;
    private  Context mContext;

    private MyItemClickListener mItemClickListener;

    public MarshonRecyclerAdapter(Context context, List<Content> mDatas){
        this.mContext=context;
        this.mDatas=mDatas;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView=View.inflate(mContext, R.layout.listitem,null);
        return new MViewHolder(itemView,mItemClickListener);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        convert(holder,position);
    }

    /**
     * 璁剧疆Item鐐瑰嚮鐩戝惉
     * @param listener
     */
    public void setOnItemClickListener(MyItemClickListener listener){
        this.mItemClickListener = listener;
    }

    public abstract void convert(RecyclerView.ViewHolder holder, final int position);

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    class  MViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        //鐩戝惉浜嬩欢
        private MyItemClickListener mListener;
        //宸︿晶鍥剧墖


        public MViewHolder(View itemView,MyItemClickListener mListener) {
            super(itemView);

            this.mListener= mListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mListener != null){
                mListener.onItemClick(v,getAdapterPosition());
            }
        }
    }
    /**
     * 姣忛」鐨勭偣鍑讳簨浠�
     */
    public interface MyItemClickListener {
        public void onItemClick(View view, int postion);
    }
}
