package com.example.evaluate_sqlite3_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ES";

    private String dbPath = null;
	private String logDirPath = null;
	private String logTag = null;
	private int test001Count = 1000;
	private int test002Count = 1000;

    private static final int TestType_001_01 = 1;
    private static final int TestType_002_01 = 2;
    private static final int TestType_002_02 = 3;

    private static final int TestStepNone = 0;
    private static final int TestStep001 = 1;
    private static final int TestStep002 = 2;

    private Timer timer;
	private Object mThreadCount = new Object();
	private int threadCount = 0;
	private int testStep = TestStepNone;

    private TextView statusLabel;
    private Button runButton;

	public native int TestFunc(String dbPath, String logDirPath, String logTag, int testCount, long tid);
	public native void ExecTestInit(String dbPath, String logDirPath);
	public native void ExecTest001N01(String dbPath, String logDirPath, String logTag, int testCount, long tid);
	public native void ExecTest002N01(String dbPath, String logDirPath, String logTag, int testCount, long tid);
	public native void ExecTest002N02(String dbPath, String logDirPath, String logTag, int testCount, long tid);


	static {
		System.loadLibrary("testmain");
	}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.statusLabel = (TextView)findViewById(R.id.statusLabel);

        this.runButton = (Button)findViewById(R.id.runButton);
		{
			MainActivity self = this;
			this.runButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					self.onRunButtonPressed();
				}
			});
		}

		if (this.initApp()) {
        	this.statusLabel.setText("status is good.");
		} else {
			this.statusLabel.setText("!!! has error !!!");
		}
		this.testStep = TestStepNone;
    }

    private void onRunButtonPressed() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
		LocalDateTime now = LocalDateTime.now();
		this.logTag = dtf.format(now);

		// long tid = 123450000;
		// int ret = TestFunc(this.dbPath, this.logDirPath, this.logTag, this.testCount, tid);
        // Log.d(TAG, "returned ? " + ret);

		this.runButton.setEnabled(false);
		this.statusLabel.setText("running test.");

		ExecTestInit(this.dbPath, this.logDirPath);

		this.runTest001();

		if (this.timer != null) {
			this.timer.cancel();
		}

		Handler handler = new Handler();

		this.timer = new Timer();
		{
			MainActivity self = this;
			this.timer.schedule(new TimerTask() {
				@Override
				public void run() {
					self.checkThread(handler);
				}
			}, 1000, 1000);
		}
    }

    private void checkThread(Handler handler) {
		boolean isFinished = false;
		synchronized(mThreadCount) {
			if (this.threadCount == 0) {
				isFinished = true;
			}
		}
		if (!isFinished) {
			return;
		}
		if (this.testStep == TestStep001) {
			Log.i(TAG, "checkThread: TestStep001 is finished.\n");
			this.runTest002();
			return;
		}
		if (this.testStep == TestStep002) {
			Log.i(TAG, "checkThread: TestStep002 is finished.\n");
		}

		// テスト終了
		this.timer.cancel();
		this.timer = null;
		this.testStep = TestStepNone;

		MainActivity self = this;
		handler.post(new Runnable() {
			@Override
			public void run() {
				self.statusLabel.setText("status is good.");
				self.runButton.setEnabled(true);
			}
		});

		Log.i(TAG, "### All thread is finished ###\n");
	}

    private void runTest001() {
		final int Test001ThreadCount = 5;

		this.testStep = TestStep001;
		for (int i = 0; i < Test001ThreadCount; i++) {
			try {
				MainActivity self = this;
				new Thread(new Runnable() {
					public void run() {
						__runTest(self.TestType_001_01);
					}
				}).start();
			} catch (Exception e) {
				Log.e(TAG, "runTest001: create thread failed.");
				return;
			}
		}
	}

    private void runTest002() {
		MainActivity self = this;

		this.testStep = TestStep002;
		try {
			new Thread(new Runnable() {
				public void run() {
					__runTest(self.TestType_002_01);
				}
			}).start();
		} catch (Exception e) {
			Log.e(TAG, "runTest002: create 002_01 thread failed.");
			return;
		}
		try {
			new Thread(new Runnable() {
				public void run() {
					__runTest(self.TestType_002_02);
				}
			}).start();
		} catch (Exception e) {
			Log.e(TAG, "runTest001: create 002_02 thread failed.");
			return;
		}
	}

    private void __runTest(int testType) {
		synchronized(mThreadCount) {
			this.threadCount++;
		}

		long tid = android.os.Process.myTid();

		switch (testType) {
			case TestType_001_01:
				{
					String tmpLogTag = this.logTag + "Test001_01";
					ExecTest001N01(this.dbPath, this.logDirPath, tmpLogTag, this.test001Count, tid);
					break;
				}
			case TestType_002_01:
				{
					String tmpLogTag = this.logTag + "Test002_01";
					ExecTest002N01(this.dbPath, this.logDirPath, tmpLogTag, this.test002Count, tid);
					break;
				}
			case TestType_002_02:
				{
					String tmpLogTag = this.logTag + "Test002_02";
					ExecTest002N02(this.dbPath, this.logDirPath, tmpLogTag, this.test002Count, tid);
					break;
				}
			default:
				Log.e(TAG, "Invalid test type !");
				break;
		}
		synchronized(mThreadCount) {
			this.threadCount--;
		}
	}

    private boolean initApp() {
        Context context = getApplicationContext();
        String externalDoc = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).toString();
        Log.d(TAG, "pass ? " + externalDoc);

		this.dbPath = externalDoc + "/db.sqlite3";
		this.logDirPath = externalDoc + "/logs/";

		// dbPath
		try {
			File f = new File(this.dbPath);
			if (f.exists()) {
				Log.i(TAG, "already copied dbfile. [" + this.dbPath + "]");
			} else {
				InputStream inputStream = getAssets().open("db.sqlite3");
				FileOutputStream fileOutputStream = new FileOutputStream(new File(this.dbPath), false);
				byte[] buffer = new byte[1024];
				int length = 0;
				while ((length = inputStream.read(buffer)) >= 0) {
					fileOutputStream.write(buffer, 0, length);
				}
				fileOutputStream.close();
				inputStream.close();
				Log.i(TAG, "copied dbfile. [" + this.dbPath + "]");
			}
		} catch (FileNotFoundException e) {
			// 何かテキトーに
			Log.e(TAG, "cannot create log directory. [" + this.dbPath + "] (" + e.toString() + ")");
			return false;
		} catch (IOException e) {
			Log.e(TAG, "cannot create log directory. [" + this.dbPath + "] (" + e.toString() + ")");
			return false;
        }

        // logDirPath
		{
			File dir = new File(this.logDirPath);
			if (dir.exists()) {
				if (!dir.isDirectory()) {
					Log.e(TAG, "log directory is exists file. [" + this.logDirPath + "]");
					return false;
				}
				String[] children = dir.list();
				for (int i = 0; i < children.length; i++) {
					new File(dir, children[i]).delete();
				}
			} else {
				if (!dir.mkdirs()) {
					return false;
				}
			}
			Log.i(TAG, "initialized log directory. [" + this.logDirPath + "]");
		}

		return true;
	}

}
