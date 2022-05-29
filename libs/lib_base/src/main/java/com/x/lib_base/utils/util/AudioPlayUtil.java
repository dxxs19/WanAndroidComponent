package com.x.lib_base.utils.util;

import android.content.Context;
import android.media.MediaPlayer;

import java.io.File;
import java.io.IOException;

/**
 * @desc  播放音效文件
 * @author wei
 * @date 2021/12/30
 **/
public class AudioPlayUtil implements MediaPlayer.OnCompletionListener {

    private MediaPlayer mediaPlayer;
    private OnPlayListener onPlayListener;
    private String audioPath;

    // 使用静态内部类单例模式。既能确保线程安全，也能保证单例对象的唯一性，同时也延迟了单例的实例化，所以这是
    // 推荐使用的单例模式实现方式。
    public static AudioPlayUtil getInstance() {
        return AudioPlayUtilHolder.instance;
    }

    private static class AudioPlayUtilHolder {
        private static final AudioPlayUtil instance = new AudioPlayUtil();
    }

    public void play(Context context, int resID, boolean isLooping, OnPlayListener onPlayListener) {
        this.onPlayListener = onPlayListener;
        play(context, resID, isLooping);
    }

    public void play(Context context, int resID, boolean isLooping) {
        if (isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(context, resID);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setLooping(isLooping);
        mediaPlayer.start();
    }

    public void playByPath(String path, OnPlayListener onPlayListener) {
        playByFile(new File(path), onPlayListener);
    }

    public void playByFile(File file, OnPlayListener onPlayListener) {
        if (audioPath != null) {
            if (isPlaying() && file.getAbsolutePath() == audioPath) {
                stop();
                return;
            }
        }
        this.onPlayListener = onPlayListener;
        audioPath = file.getAbsolutePath();
        play();
    }

    public void play() {
        if (isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        mediaPlayer = new MediaPlayer();
        mediaPlayer.reset();
        mediaPlayer.setOnCompletionListener(this);
        try {
            mediaPlayer.setDataSource(audioPath);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if (isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        mediaPlayer = null;
        onPlayListener.onStop();
        onPlayListener = null;
    }

    /**
     * 继续播放
     * @param onPlayListener
     */
    public void goOn(OnPlayListener onPlayListener) {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        } else {
            if (audioPath != null) {
                playByPath(audioPath, onPlayListener);
            }
        }
    }

    /**
     * 是否正在播放
     * @return
     */
    public boolean isPlaying() {
        if (mediaPlayer == null) {
            return false;
        }
        return mediaPlayer.isPlaying();
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        if (onPlayListener != null) {
            onPlayListener.onCompletion(mediaPlayer);
        }
    }

    interface OnPlayListener {
        void onCompletion(MediaPlayer mediaPlayer);
        void onStop();
    }
}
