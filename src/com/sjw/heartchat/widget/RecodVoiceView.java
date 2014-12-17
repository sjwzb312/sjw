package com.sjw.heartchat.widget;

import java.io.IOException;

import com.sjw.heartchat.manager.RecordManager;
import com.sjw.heartchat.utils.ToastUtil;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class RecodVoiceView extends Button implements OnTouchListener {

	private RecordManager recordManage;
	private int MIX_TIME = 1500;
	private int SPACE_TIME = 200;
	private Handler handler = new Handler();
	private int recordTime;
	private Context context;
	private RecordVoiceListener recordVoiceListener;

	public RecodVoiceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}

	private void init() {
		setOnTouchListener(this);
		recordManage = new RecordManager();
	}

	public RecordVoiceListener getRecordVoiceListener() {
		return recordVoiceListener;
	}

	public void setRecordVoiceListener(RecordVoiceListener recordVoiceListener) {
		this.recordVoiceListener = recordVoiceListener;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: // 摁下录音
			try {
				setTag("开始录音");
				recordTime = 0;
				recordManage.start("");
				handler.postDelayed(runnable, SPACE_TIME);
			} catch (IllegalStateException e) {

				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case MotionEvent.ACTION_UP:// 松开录音结束
			recordTime = 0;
			recordManage.stop();
			setText("录音结束");
			if (recordTime < MIX_TIME) {
				ToastUtil.toast(context, "录音时间太短,请重新录制");
			} else {
				if (recordVoiceListener != null) {
					recordVoiceListener.onRecordComplete("", recordTime);
				}
			}
			break;

		default:
			break;
		}
		return false;
	}

	public interface RecordVoiceListener {
		// 录音完成
		void onRecordComplete(String filePath, int length);

		void onRecordCancle();
	}

	private Runnable runnable = new Runnable() {

		@Override
		public void run() {
			recordTime += SPACE_TIME;
			setText(recordTime + "");
			handler.postDelayed(this, SPACE_TIME);
			changeBg();
		}
	};

	private void changeBg() {
		switch (recordManage.getAmplitude()) {
		case 0:

			break;

		default:
			break;
		}
	}

}
