package com.example.evaluate_sqlite3_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "ES";

    private TextView statusLabel;
    private Button runButton;

	public native void TestFunc();

	static {
		System.loadLibrary("testmain");
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.statusLabel = (TextView)findViewById(R.id.statusLabel);
        this.statusLabel.setText("test");

        this.runButton = (Button)findViewById(R.id.runButton);
        this.runButton.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  onRunButtonPressed();
                                              }
                                          }

        );
    }

    private void onRunButtonPressed() {
        Log.d(TAG, "pressed.");
		TestFunc();
    }
}
