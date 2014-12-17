package com.sjw.heartchat.manager;

import java.io.IOException;

import android.media.MediaRecorder;

/**
 * 语音录制的manager
 * 
 * @author gg
 * 
 */
public class RecordManager {

	private MediaRecorder mRecorder;

	/**
	 * 开始录制
	 * 
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	public void start(String filePath) throws IllegalStateException,
			IOException {

		if (mRecorder == null) {
			mRecorder = new MediaRecorder();
		}
		mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		mRecorder.setOutputFile(filePath);
		mRecorder.prepare();
		mRecorder.start();
	}

	/**
	 * 停止录音
	 */
	public void stop() {
		if (mRecorder != null) {
			mRecorder.stop();
			mRecorder.release();
			mRecorder = null;
		}
	}

	/**
	 * 获取音量
	 * 
	 * @return
	 */
	public int getAmplitude() {
		if (mRecorder != null) {
			return (mRecorder.getMaxAmplitude());
		} else
			return 0;
	}

}
