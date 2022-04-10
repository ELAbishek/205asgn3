package com.example.serviceexample;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity{

    private Button start, calc; //To read the buttons and set up variables
    private TextView result0;
    private TextView result1;
    private TextView result2;
    private TextView result3;
    private TextView result4;
    private EditText ticker0;
    private EditText ticker1;
    private EditText ticker2;
    private EditText ticker3;
    private EditText ticker4;
    private TextView sdresult0;
    private TextView sdresult1;
    private TextView sdresult2;
    private TextView sdresult3;
    private TextView sdresult4;

    private BroadcastReceiver myBroadcastReceiver;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myBroadcastReceiver = new MyBroadcastReceiver(new Handler(Looper.getMainLooper())); // Broadcast receiver instance


        // set up layout

        setContentView(R.layout.activitymain);

        start = (Button) findViewById(R.id.start_button);
        calc = (Button) findViewById(R.id.calc_button);
        result0 = (TextView) findViewById(R.id.textview_result0);
        result1 = (TextView) findViewById(R.id.textview_result1);
        result2 = (TextView) findViewById(R.id.textview_result2);
        result3 = (TextView) findViewById(R.id.textview_result3);
        result4 = (TextView) findViewById(R.id.textview_result4);
        result1 = (TextView) findViewById(R.id.textview_result1);
        sdresult0 = (TextView) findViewById(R.id.sd_0);
        sdresult1 = (TextView) findViewById(R.id.sd_1);
        sdresult2 = (TextView) findViewById(R.id.sd_2);
        sdresult3 = (TextView) findViewById(R.id.sd_3);
        sdresult4 = (TextView) findViewById(R.id.sd_4);
        result1 = (TextView) findViewById(R.id.textview_result1);
        result2 = (TextView) findViewById(R.id.textview_result2);
        result3 = (TextView) findViewById(R.id.textview_result3);
        result4 = (TextView) findViewById(R.id.textview_result4);
        result1 = (TextView) findViewById(R.id.textview_result1);
        ticker0 = (EditText) findViewById(R.id.ticker_input0);
        ticker1 = (EditText) findViewById(R.id.ticker_input1);
        ticker2 = (EditText) findViewById(R.id.ticker_input2);
        ticker3 = (EditText) findViewById(R.id.ticker_input3);
        ticker4 = (EditText) findViewById(R.id.ticker_input4);

        // start service, pass ticker info via an intent

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // This is what happens when you press download
                Intent intent = new Intent(getApplicationContext(), MyService.class);
                //Lines to send the tickers to MyService
                HistoricalDataProvider.del();
                MyBroadcastReceiver.count = 0;
                intent.putExtra("ticker0", String.valueOf(ticker0.getText()));
                intent.putExtra("ticker1", String.valueOf(ticker1.getText()));
                intent.putExtra("ticker2", String.valueOf(ticker2.getText()));
                intent.putExtra("ticker3", String.valueOf(ticker3.getText()));
                intent.putExtra("ticker4", String.valueOf(ticker4.getText()));
                startService(intent);
            }
        });

        // register broadcast receiver to get informed that data is downloaded so that we can calc

        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result0.setText("Waiting..");
                result1.setText("Waiting..");
                result2.setText("Waiting..");
                result3.setText("Waiting..");
                result4.setText("Waiting..");
                sdresult0.setText("Waiting..");
                sdresult1.setText("Waiting..");
                sdresult2.setText("Waiting..");
                sdresult3.setText("Waiting..");
                sdresult4.setText("Waiting..");
                registerReceiver(myBroadcastReceiver, new IntentFilter("DOWNLOAD_COMPLETE"));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(myBroadcastReceiver);
    }

}