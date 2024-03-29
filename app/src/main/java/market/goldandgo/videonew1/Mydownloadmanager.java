package market.goldandgo.videonew1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import multithreaddownload.DownloadInfo;
import multithreaddownload.DownloadManager;
import market.goldandgo.videonew1.Adapter.RecyclerViewAdapter;
import market.goldandgo.videonew1.Object.Constant;
import market.goldandgo.videonew1.Utils.Mydatabase_down;
import market.goldandgo.videonew1.entity.AppInfo;
import market.goldandgo.videonew1.entity.DataSource;
import market.goldandgo.videonew1.listener.OnItemClickListener;
import market.goldandgo.videonew1.service.DownloadService;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Go Goal on 5/24/2017.
 */

public class Mydownloadmanager extends AppCompatActivity implements OnItemClickListener<AppInfo> {

    ArrayList<String> list, sizelist;

    static AppCompatActivity ac;

    RecyclerView recyclerView;
    LinearLayoutManager llm;

    private List<AppInfo> mAppInfos;
    private RecyclerViewAdapter mAdapter;
    static Mydatabase_down mdb;

    private DownloadReceiver mReceiver;

    LinearLayout titlebar,backlayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.downmgr);




        mdb=new Mydatabase_down(getApplicationContext());
        ac = this;
        ImageView close = (ImageView) findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();

            }
        });


        backlayout= (LinearLayout) findViewById(R.id.backlayout);
        Bitmap bitmap=MainActivity.getback();
        backlayout.setBackground(new BitmapDrawable(ac.getResources(), bitmap));

        titlebar= (LinearLayout) findViewById(R.id.titlebar);
        titlebar.setBackgroundColor(MainActivity.getbackcolor());


        recyclerView = (RecyclerView) findViewById(R.id.rv);



        mAdapter = new RecyclerViewAdapter(ac);
        mAdapter.setOnItemClickListener(this);



        mAppInfos = DataSource.getmyData(ac);

        for (AppInfo info : mAppInfos) {
            Log.e("info.getUrl()",info.getComplete()+""+info.getId());

            if (info.getComplete().equals("com")){
                info.setStatus(AppInfo.STATUS_COMPLETE);
                info.setProgress(100);
                info.setDownloadPerSize("Open");
            }else{
                DownloadInfo downloadInfo = DownloadManager.getInstance().getDownloadInfo(info.getUrl());
                if (downloadInfo != null) {
                    info.setProgress(downloadInfo.getProgress());
                    info.setDownloadPerSize(getDownloadPerSize(downloadInfo.getFinished(), downloadInfo.getLength()));
                    info.setStatus(AppInfo.STATUS_PAUSED);
                }
            }




        }


        recyclerView.setLayoutManager(new LinearLayoutManager(ac));
        recyclerView.setAdapter(mAdapter);
        mAdapter.setData(mAppInfos);




    }


    @Override
    public void onItemClick(View v, int position, AppInfo appInfo) {

        if (v.getId()==R.id.remove){
            pause(appInfo.getUrl());
           // DownloadService.cancel(appInfo.getUrl());
           // DownloadService.destory(ac);
            mdb.delete_row(appInfo.getId());
            mAppInfos.get(position).setStatus(AppInfo.STATUS_INSTALLED);

            mAdapter.setData(mAppInfos);



        }else{
            if (appInfo.getStatus() == AppInfo.STATUS_DOWNLOADING || appInfo.getStatus() == AppInfo.STATUS_CONNECTING) {
                pause(appInfo.getUrl());
            } else if (appInfo.getStatus() == AppInfo.STATUS_COMPLETE) {
                   openfolder(position);
            } else if (appInfo.getStatus() == AppInfo.STATUS_INSTALLED) {
                //  unInstall(appInfo);
            } else {
                download(position, appInfo.getUrl(), appInfo);
            }
        }


    }

    private void openfolder(int position) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri mydir = Uri.parse("file://"+Constant.downloadfolder+mAppInfos.get(position).getName() + ".mp4");
        intent.setDataAndType(mydir,"application/*");    // or use */*
        startActivity(intent);
    }


    private void download(int position, String tag, AppInfo info) {
        DownloadService.intentDownload(ac, position, tag, info);
    }





    private void pause(String tag) {
        DownloadService.intentPause(ac, tag);
    }

    private void pauseAll() {
        DownloadService.intentPauseAll(ac);
    }


    private RecyclerViewAdapter.AppViewHolder getViewHolder(int position) {
        return (RecyclerViewAdapter.AppViewHolder) recyclerView.findViewHolderForLayoutPosition(position);
    }

    private boolean isCurrentListViewItemVisible(int position) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int first = layoutManager.findFirstVisibleItemPosition();
        int last = layoutManager.findLastVisibleItemPosition();
        return first <= position && position <= last;
    }

    private static final DecimalFormat DF = new DecimalFormat("0.00");

    private String getDownloadPerSize(long finished, long total) {
        return DF.format((float) finished / (1024 * 1024)) + "M/" + DF.format((float) total / (1024 * 1024)) + "M";
    }

    @Override
    protected void onResume() {
        super.onResume();
        register();

    }

    @Override
    public void onPause() {
        super.onPause();
        unRegister();
    }

    private void unRegister() {
        if (mReceiver != null) {
            LocalBroadcastManager.getInstance(ac).unregisterReceiver(mReceiver);
        }
    }

    private void register() {
        mReceiver = new DownloadReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DownloadService.ACTION_DOWNLOAD_BROAD_CAST);
        LocalBroadcastManager.getInstance(ac).registerReceiver(mReceiver, intentFilter);
    }

    class DownloadReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {



            final String action = intent.getAction();
            if (action == null || !action.equals(DownloadService.ACTION_DOWNLOAD_BROAD_CAST)) {
                return;
            }
            final int position = intent.getIntExtra(DownloadService.EXTRA_POSITION, -1);
            final AppInfo tmpInfo = (AppInfo) intent.getSerializableExtra(DownloadService.EXTRA_APP_INFO);
            if (tmpInfo == null || position == -1) {
                return;
            }
            final AppInfo mAppInfo = mAppInfos.get(position);
            final int status = tmpInfo.getStatus();
            switch (status) {
                case AppInfo.STATUS_CONNECTING:
                    mAppInfo.setStatus(AppInfo.STATUS_CONNECTING);
                    if (isCurrentListViewItemVisible(position)) {
                        RecyclerViewAdapter.AppViewHolder holder = getViewHolder(position);
                        holder.tvstatus.setText(mAppInfo.getStatusText());
                        holder.btnDownload.setText(mAppInfo.getButtonText());
                    }
                    break;

                case AppInfo.STATUS_DOWNLOADING:
                    mAppInfo.setProgress(tmpInfo.getProgress());
                    mAppInfo.setDownloadPerSize(tmpInfo.getDownloadPerSize());
                    mAppInfo.setStatus(AppInfo.STATUS_DOWNLOADING);
                    if (isCurrentListViewItemVisible(position)) {
                        RecyclerViewAdapter.AppViewHolder holder = getViewHolder(position);
                        holder.tvDownloadPerSize.setText(mAppInfo.getDownloadPerSize());
                        holder.progressBar.setProgress(mAppInfo.getProgress());
                        holder.tvstatus.setText(mAppInfo.getStatusText());
                        holder.btnDownload.setText(mAppInfo.getButtonText());
                    }
                    break;
                case AppInfo.STATUS_COMPLETE:
                    mAppInfo.setStatus(AppInfo.STATUS_COMPLETE);
                    mAppInfo.setProgress(100);
                    mAppInfo.setDownloadPerSize("Open");

                    if (isCurrentListViewItemVisible(position)) {
                        RecyclerViewAdapter.AppViewHolder holder = getViewHolder(position);
                        holder.tvstatus.setText(mAppInfo.getStatusText());
                        holder.btnDownload.setText(mAppInfo.getButtonText());
                    }
                    break;

                case AppInfo.STATUS_PAUSED:
                    mAppInfo.setStatus(AppInfo.STATUS_PAUSED);
                    try{
                        if (isCurrentListViewItemVisible(position)) {
                            RecyclerViewAdapter.AppViewHolder holder = getViewHolder(position);
                            holder.tvstatus.setText(mAppInfo.getStatusText());
                            holder.btnDownload.setText(mAppInfo.getButtonText());
                        }
                    }catch (Exception e){

                    }


                    break;
                case AppInfo.STATUS_NOT_DOWNLOAD:
                    mAppInfo.setStatus(AppInfo.STATUS_NOT_DOWNLOAD);
                    mAppInfo.setDownloadPerSize("");
                    if (isCurrentListViewItemVisible(position)) {
                        RecyclerViewAdapter.AppViewHolder holder = getViewHolder(position);
                        holder.tvstatus.setText(mAppInfo.getStatusText());
                        holder.tvDownloadPerSize.setText("");
                        holder.btnDownload.setText(mAppInfo.getButtonText());
                    }
                    break;
                case AppInfo.STATUS_DOWNLOAD_ERROR:
                    mAppInfo.setStatus(AppInfo.STATUS_DOWNLOAD_ERROR);
                    mAppInfo.setDownloadPerSize("");
                    if (isCurrentListViewItemVisible(position)) {
                        RecyclerViewAdapter.AppViewHolder holder = getViewHolder(position);
                        holder.tvstatus.setText(mAppInfo.getStatusText());
                        holder.tvDownloadPerSize.setText("");
                        holder.btnDownload.setText(mAppInfo.getButtonText());
                    }
                    break;
            }
        }
    }



}
