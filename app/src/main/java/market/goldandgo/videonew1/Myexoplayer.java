package market.goldandgo.videonew1;

/**
 * Created by Go Goal on 1/24/2018.
 */

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;

import com.wang.avi.AVLoadingIndicatorView;

import market.goldandgo.videonew1.Object.Constant;

public class Myexoplayer extends AppCompatActivity {

    String url;
    SimpleExoPlayer player;

    private BandwidthMeter bandwidthMeter;
    private DefaultTrackSelector trackSelector;
    private boolean shouldAutoPlay;
    private DataSource.Factory mediaDataSourceFactory;
    AVLoadingIndicatorView pg;
    AppCompatActivity ac;
    long seekvalue = 0;
    long avaliablebuffer = 0;
    long watedtime = 0;
    FrameLayout fm;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE); //Remove title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.exo_main);
        pg = (AVLoadingIndicatorView) findViewById(R.id.imgpg);
        fm = (FrameLayout) findViewById(R.id.ad_vi1ew);
        pg.show();
        ac = this;

//Remove notification bar
        url = getIntent().getExtras().getString("url");


        shouldAutoPlay = true;
        bandwidthMeter = new DefaultBandwidthMeter();
        mediaDataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "mediaPlayerSample"), (TransferListener<? super DataSource>) bandwidthMeter);


        SimpleExoPlayerView simpleExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.video_view);
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

        simpleExoPlayerView.setPlayer(player);

        player.setPlayWhenReady(shouldAutoPlay);

        DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();


        MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(url),
                mediaDataSourceFactory, extractorsFactory, null, null);
        player.prepare(mediaSource);


     /*   player.setVideoListener(new SimpleExoPlayer.VideoListener() {
            @Override
            public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
                Log.e("size",width+" > "+height);

            }

            @Override
            public void onRenderedFirstFrame() {

            }
        });*/


        player.addListener(new ExoPlayer.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {
                avaliablebuffer = player.getBufferedPosition();
            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                switch (playbackState) {
                    case ExoPlayer.STATE_BUFFERING:
                        Log.e("STATE_BUFFERING","STATE_BUFFERING");
                        //    Log.e("e", player.getCurrentPosition() + " > buffer " + avaliablebuffer);

                        if (player.getCurrentPosition() > avaliablebuffer) {

                            pg.setVisibility(View.VISIBLE);
                            pg.show();
                        }

                        break;
                    case ExoPlayer.STATE_ENDED:
                        Log.e("STATE_ENDED","STATE_ENDED");
                        //do what you want
                        break;
                    case ExoPlayer.STATE_IDLE:
                        Log.e("STATE_IDLE","STATE_IDLE");
                        break;

                    case ExoPlayer.STATE_READY:
                        Log.e("STATE_READY","STATE_READY");
                        pg.setVisibility(View.GONE);
                        seekvalue = player.getCurrentPosition();

                        break;


                    default:
                        Log.e("default","default");
                        pg.setVisibility(View.GONE);

                        break;
                }

                if (playWhenReady && playbackState == player.STATE_READY) {
                    fm.setVisibility(View.GONE);
                } else if (playWhenReady) {
                    fm.setVisibility(View.GONE);
                } else {
                   // Setbanner_Ads();
                }
            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {
                seekvalue = player.getCurrentPosition();
                pg.setVisibility(View.VISIBLE);
                pg.show();
                DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
                MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(url),
                        mediaDataSourceFactory, extractorsFactory, null, null);
                player.prepare(mediaSource);
                player.seekTo(seekvalue);
            }

            @Override
            public void onPositionDiscontinuity() {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }
        });


        simpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        player.seekTo(0);

    }



    private void pausePlayer() {
        player.setPlayWhenReady(false);
        player.getPlaybackState();
    }

    private void startPlayer() {
        player.setPlayWhenReady(true);
        player.getPlaybackState();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pausePlayer();

    }

    @Override
    protected void onResume() {
        super.onResume();
        startPlayer();
    }


}