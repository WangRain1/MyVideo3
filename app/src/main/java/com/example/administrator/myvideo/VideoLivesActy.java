package com.example.administrator.myvideo;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myvideo.util.weight.HeartLayout;
import com.superplayer.library.SuperPlayer;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/3/6.
 */

public class VideoLivesActy extends AppCompatActivity implements SuperPlayer.OnNetChangeListener {

    SuperPlayer superPlayer;

    String url;

    String text;


    private Random mRandom = new Random();
    private Timer mTimer = new Timer();
    private HeartLayout mHeartLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lives_video);

        superPlayer = (SuperPlayer) findViewById(R.id.view_super_player);

        mHeartLayout = (HeartLayout) findViewById(R.id.heart_layout);
        mTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                mHeartLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mHeartLayout.addHeart(randomColor());
                    }
                });
            }
        }, 500, 200);


        superPlayer.showCenterControl(false);
        superPlayer.setHideControl(true);
        superPlayer.setShowTopControl(false);
        url = getIntent().getStringExtra("url");
        text = getIntent().getStringExtra("text");

        init();

    }

    public void init(){
        superPlayer.setLive(true);//设置该地址是直播的地址
        superPlayer.setNetChangeListener(true)//设置监听手机网络的变化
                .setOnNetChangeListener(this)//实现网络变化的回调
                .setSupportGesture(true)//设置手势调节
                .onPrepared(new SuperPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared() {
                        /**
                         * 监听视频是否已经准备完成开始播放。（可以在这里处理视频封面的显示跟隐藏）
                         */
                    }
                }).onComplete(new Runnable() {
            @Override
            public void run() {
                /**
                 * 监听视频是否已经播放完成了。（可以在这里处理视频播放完成进行的操作）
                 */
            }
        }).onInfo(new SuperPlayer.OnInfoListener() {
            @Override
            public void onInfo(int what, int extra) {
                /**
                 * 监听视频的相关信息。
                 */

            }
        }).onError(new SuperPlayer.OnErrorListener() {
            @Override
            public void onError(int what, int extra) {
                /**
                 * 监听视频播放失败的回调
                 */
            }
        }).setTitle(url)//设置视频的titleName
                .play(url);//开始播放视频
        superPlayer.setScaleType(SuperPlayer.SCALETYPE_FITXY);

    }


    @Override
    public void onWifi() {
        Toast.makeText(this,"当前使用无线网",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onMobile() {
        Toast.makeText(this,"当前使用3g/4g",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDisConnect() {
        Toast.makeText(this,"网络断开",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNoAvailable() {

    }

    /**
     * 下面的这几个Activity的生命状态很重要
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (superPlayer != null) {
            superPlayer.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (superPlayer != null) {
            superPlayer.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
        if (superPlayer != null) {
            superPlayer.onDestroy();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (superPlayer != null) {
            superPlayer.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public void onBackPressed() {
        if (superPlayer != null && superPlayer.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }


    private int randomColor() {
        return Color.rgb(mRandom.nextInt(255), mRandom.nextInt(255), mRandom.nextInt(255));
    }

}
