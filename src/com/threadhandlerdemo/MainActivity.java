package com.threadhandlerdemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	static int time = 0;
	TextView tv;
	Button start, stop;
	boolean bool;
	MyHandler handler;

	// private static final SimpleDateFormat simpleDateFormat = new
	// SimpleDateFormat(
	// "HH:mm:ss");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		tv = (TextView) findViewById(R.id.showtime);
		start = (Button) findViewById(R.id.startB);
		stop = (Button) findViewById(R.id.stopB);

		start.setOnClickListener(onClickListener);
		stop.setOnClickListener(onClickListener);

		handler = new MyHandler();

	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.startB:
				bool = true;
				startThread();
				break;
			case R.id.stopB:
				bool = false;
				break;
			}
		}
	};

	private void startThread() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub

				Message message = new Message();
				message.obj = time;
				message.what = 1;
				handler.sendMessage(message);
				Log.i(Thread.currentThread().getName(), "is running");
				time++;

				Looper.prepare();
				final Handler myhandler = new Handler() {

					@Override
					public void handleMessage(Message msg) {
						// TODO Auto-generated method stub
						super.handleMessage(msg);
						Log.i("!!!!!!!!!!!", msg.what + "!!!!!!!!");
						Looper.myLooper().quit();
					}

				};

				Runnable myRunnble = new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						myhandler.sendEmptyMessage(1);
					}

				};

				new Thread(myRunnble).start();
				Looper.loop();
				Log.i("", "Looper.loop is ");
			}
		}, "子线程").start();
	}

	Handler handlers = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

		}

	};

	@SuppressLint("HandlerLeak")
	class MyHandler extends Handler {
		public void handleMessage(Message msg) {
			Log.i("!!!!!!!!!!!!!!!!!time " + msg.obj.toString(),
					"!!!!!!!!!!!!!!!!!!!!!!");
			if (msg.what == 1) {
				tv.setText(msg.obj.toString());
			}
		//	Looper.myLooper().quit();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

/*
 * public class MainActivity extends Activity { Button startButton ; TextView
 * minuteTextView ; //contorl bool www.2cto.com static boolean sign = false;
 * Clock c = new Clock();
 * 
 * @Override public void onCreate(Bundle savedInstanceState) {
 * super.onCreate(savedInstanceState); setContentView(R.layout.activity_main);
 * 
 * startButton = (Button)findViewById(R.id.start); minuteTextView =
 * (TextView)findViewById(R.id.showtime); startButton.setOnClickListener(new
 * MyButtonListener()); } class MyButtonListener implements OnClickListener{
 * 
 * public void onClick(View v) { sign = !sign; if(sign == false) return; new
 * Thread(new Runnable() { public void run() { while( sign ){ try{ synchronized
 * (c) { minuteTextView.post(new Runnable() { public void run() {
 * minuteTextView.setText("99"); } }); c.wait(5000L); minuteTextView.post(new
 * Runnable() { public void run() { minuteTextView.setText("0"); } });
 * c.wait(5000L); } }catch(InterruptedException e){ } } } }).start(); }
 * 
 * } public class Clock { int justATmp = 0; } }
 */