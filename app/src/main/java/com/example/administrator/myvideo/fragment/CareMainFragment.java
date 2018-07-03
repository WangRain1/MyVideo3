package com.example.administrator.myvideo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myvideo.MyAdapter.MarshonRecyclerAdapter;
import com.example.administrator.myvideo.MyAdapter.MarshonRecyclerAdapter2;
import com.example.administrator.myvideo.R;
import com.example.administrator.myvideo.VideoImageActy;
import com.example.administrator.myvideo.VideoLivesActy;
import com.example.administrator.myvideo.animation.ChooseAnimatorsAdapter;
import com.example.administrator.myvideo.bean.Content;
import com.example.administrator.myvideo.bean.Lives;
import com.example.administrator.myvideo.bean.OnLineTv;
import com.example.administrator.myvideo.bean.VUrl;
import com.google.gson.Gson;
import com.marshon.mrecyclerview.MRecyclerView;
import com.marshon.swipe.SwipeWraper;
import com.squareup.picasso.Picasso;

import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpParams;

import java.util.ArrayList;
import java.util.List;


public class CareMainFragment extends Fragment {


	//zhibo视频数据
	String contentUrl = "http://116.211.167.106/api/live/aggregation?uid=133825214&interest=1";

	//item增加动画
	private ChooseAnimatorsAdapter animAdapter;

	//对象
	MRecyclerView mRecyclerView ;

	int start =0;
	int limit =10;
	int loadTime =0 ;

	MarshonRecyclerAdapter2 marshonRecyclerAdapter;

	List<Lives> datas = new ArrayList<>();


	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
							 @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		Log.e("fragment", "CareMainFragment==> onCreateView");
		
		return inflater.inflate(R.layout.fra_care, container,
				false);
	}


	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		//创建recycleview对象
		mRecyclerView = (MRecyclerView) view.findViewById(R.id.mRecyclerView);

		//-----------------recycleview的配置--------------
		//可刷新
		mRecyclerView.setPullRefreshEnabled(true);
		//可加载更多
		mRecyclerView.setLoadingMoreEnabled(true);
		final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
		linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		// recycleview 的 展示形式，listview形式，gridview的形式，瀑布流形式
		mRecyclerView.setLayoutManager(linearLayoutManager);

		// 加载数据
		mRecyclerView.setLoadingListener(new MRecyclerView.LoadingListener() {
			@Override
			public void onRefresh() {
				refresh();
			}

			@Override
			public void onLoadMore() {
				loaddata();
				mRecyclerView.refreshComplete();
				mRecyclerView.loadMoreComplete();
			}
		});

		/**
		 * 构造器，adapter 是通用的，只要修改不同的item布局就行了
		 */
		marshonRecyclerAdapter = new MarshonRecyclerAdapter2(getContext(),datas) {

			/**
			 * 此方法相当于 onbindview 方法
			 * @param holder
			 * @param position
			 */
			@Override
			public void convert(final RecyclerView.ViewHolder holder, final int position) {

				//整个 item 的 布局id
				final SwipeWraper swipelayout = (SwipeWraper) holder.itemView.findViewById(R.id.swipelayout);
				ImageView ima = (ImageView) holder.itemView.findViewById(R.id.ima);

				Picasso.with(getContext()).load(datas.get(position).getCreator().getPortrait()).into(ima);

//				Picasso.with(getContext())
//						.load(datas.get(position).getCreator().getPortrait())
//						.resize(50, 50)
//						.centerCrop()
//						.into(ima);
				TextView textView = (TextView) holder.itemView.findViewById(R.id.tex);

				textView.setText(datas.get(position).getCreator().getNick());

				//滑动删除的 按钮id
				View deleteView = holder.itemView.findViewById(R.id.tv_delete);

				/**
				 * 增加手势
				 */
				final GestureDetector mGestureDetector = new GestureDetector(new GestureDetector.OnGestureListener() {

					@Override
					public boolean onDown(MotionEvent e) {
						return false;
					}

					@Override
					public void onShowPress(MotionEvent e) {

					}

					/**
					 * 单机跳转
					 * @param e
					 * @return
					 */
					@Override
					public boolean onSingleTapUp(MotionEvent e) {
						Intent intent = new Intent(getActivity(),VideoLivesActy.class);
						intent.putExtra("url",datas.get(position).getStream_addr());
						intent.putExtra("text",datas.get(position).getName());
						startActivity(intent);
						return false;
					}

					@Override
					public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
						return false;
					}

					@Override
					public void onLongPress(MotionEvent e) {

					}

					@Override
					public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
						return false;
					}
				});

				/**
				 * 给整个滑动布局增加，手势，处理手势冲突
				 */
				swipelayout.setOnTouchListener(new View.OnTouchListener() {
					@Override
					public boolean onTouch(View v, MotionEvent event) {

						//手势触摸
						mGestureDetector.onTouchEvent(event);
						//此处必须设置成false，不然滑动和点击会冲突
						return false;
					}
				});

				//删除的监听
				deleteView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						//动画
						swipelayout.close(true);
						//删除集合数据
						datas.remove(holder.getLayoutPosition()-1);
						//原始的加载数据没动画
//                        notifyItemRemoved(holder.getLayoutPosition());
						//自定义动画刷新数据
						animAdapter.notifyItemRemoved(holder.getLayoutPosition());
					}
				});
			}
		};

		//给 item 增加动画效果
		animAdapter =new ChooseAnimatorsAdapter(marshonRecyclerAdapter);
		animAdapter.setAnimatorFlag(ChooseAnimatorsAdapter.ANIMATORFLAG_SCALE);
		mRecyclerView.setAdapter(animAdapter);
		refresh();

	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		Log.e("fragment", "CareMainFragment==> onDestroyView");
	}

	/**
	 * 加载数据
	 */
	public void refresh(){
		/**
		 * 任意加长刷新时间，实现京东等刷新效果
		 */
		mRecyclerView.postDelayed(new Runnable() {
			@Override
			public void run() {
				start = 0;
				loadTime =0 ;
//				request ();
				loaddata();
				mRecyclerView.refreshComplete();
				mRecyclerView.loadMoreComplete();
			}
		}, 2000);

	}



	/**
	 * 加载视频
	 */
	public void loaddata (){

		loadTime++;
		KJHttp kjHttp = new KJHttp();
		HttpParams httpParams = new HttpParams();
		httpParams.put("start",start);
		httpParams.put("limit",limit);
		kjHttp.post(contentUrl, httpParams, new HttpCallBack() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);

				Gson gson = new Gson();

				OnLineTv vUrl = gson.fromJson(t, OnLineTv.class);

				List<Lives> contents = vUrl.getLives();


				if (start == 0 && datas.size()>0){
					datas.clear();
				}
				if ((contents != null && contents.size() > 0)) {
					datas.addAll(contents);
					marshonRecyclerAdapter.notifyDataSetChanged();
					start += contents.size();
				}
				mRecyclerView.refreshComplete();
				mRecyclerView.loadMoreComplete();
			}

			@Override
			public void onFailure(int errorNo, String strMsg) {
				super.onFailure(errorNo, strMsg);
			}

			@Override
			public void onFinish() {
				super.onFinish();
			}
		});
	}

}
