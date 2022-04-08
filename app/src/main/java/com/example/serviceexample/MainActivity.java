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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity{

    private Button start, calc;
    private TextView result;
    private EditText ticker0;
    private EditText ticker1;
    private EditText ticker2;
    private EditText ticker3;
    private EditText ticker4;
//    Uri CONTENT_URI = Uri.parse("content://com.example.serviceexample.HistoricalDataProvider/history");
    private BroadcastReceiver myBroadcastReceiver;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set up layout

        setContentView(R.layout.activitymain);

        start = (Button) findViewById(R.id.start_button);
        calc = (Button) findViewById(R.id.calc_button);
        result = (TextView) findViewById(R.id.textview_result);
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
                result.setText("Waiting for data.. ");
                myBroadcastReceiver = new MyBroadcastReceiver(new Handler(Looper.getMainLooper()));
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