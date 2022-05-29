package com.x.lib_base.utils.util

import android.content.Context
import android.media.MediaPlayer
import java.io.File


object VoicePlayUtil : MediaPlayer.OnCompletionListener {
    private var mMediaPlayer: MediaPlayer? = null
    private var onPlayListener: OnPlayListener? = null
    private var token: String? = null

    /**
     * 播放本地音频资源
     *
     */
    fun start(context: Context, resId: Int, isLooping: Boolean = false, l: OnPlayListener?) {
        start(resId, context, isLooping)
        onPlayListener = l
    }

    fun start(path: String, l: OnPlayListener) {
        start(File(path), l)
    }

    fun start(file: File, l: OnPlayListener) {
        if (token != null) {
            if (isPlaying() && file.absolutePath == token) {
                stop()
                return
            }
        }
        onPlayListener = l
        token = file.absolutePath
        start()
    }

    /**
     * 继续播放
     */
    fun goOn(playListener: OnPlayListener) {
        try {
            if (mMediaPlayer != null) {
                mMediaPlayer?.start()
            } else {
                token?.let {
                    start(it, playListener)
                }
            }
        } catch (e: Exception) {
        }
    }

    private fun start() {
        try {
            if (isPlaying()) {
                mMediaPlayer?.stop()
                mMediaPlayer?.release()
            }
            // TODO 对象复用
            mMediaPlayer = MediaPlayer()
            mMediaPlayer?.reset()
            mMediaPlayer?.setOnCompletionListener(this)
            mMediaPlayer?.setDataSource(token)
            mMediaPlayer?.prepare()
            mMediaPlayer?.start()
        } catch (e: Exception) {
            e.printStackTrace()
            stop()
        }

    }

    private fun start(resId: Int, context: Context, isLooping: Boolean) {
        try {
            if (isPlaying()) {
                mMediaPlayer?.stop()
                mMediaPlayer?.release()
            }
            mMediaPlayer = MediaPlayer.create(context, resId)
            mMediaPlayer?.setOnCompletionListener(this)
            mMediaPlayer?.isLooping = isLooping
            mMediaPlayer?.start()
        } catch (e: Exception) {
            e.printStackTrace()
            stop()
        }
    }

    fun stop() {
        if (isPlaying()) {
            mMediaPlayer?.stop()
            mMediaPlayer?.release()
        }
        mMediaPlayer = null
        onPlayListener?.onVoiceStop()
        onPlayListener = null
    }

    private fun isPlaying(): Boolean {
        try {
            mMediaPlayer?.let {
                return it.isPlaying
            }
        } catch (e: Exception) {
            stop()
        }

        return false

    }

    fun onSwitchMode() {
        if (token != null) {
            if (isPlaying()) {
                mMediaPlayer?.stop()
                mMediaPlayer?.release()
            }
            start()
        }

    }

    fun pause() {
        if (token != null) {
            mMediaPlayer?.pause()
        }

    }

    override fun onCompletion(mediaplayer: MediaPlayer) {
        onPlayListener?.onCompletion(mediaplayer)
//        token = null
    }


    interface OnPlayListener {

        fun onCompletion(mediaPlayer: MediaPlayer)

        fun onVoiceStop()
    }
}