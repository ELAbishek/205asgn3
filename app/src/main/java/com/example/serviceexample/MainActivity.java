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

        // start service, pass ticker info via an intent

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyService.class);
                intent.putExtra("ticker0", String.valueOf(ticker0.getText()));
//                intent.putExtra()
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

    public void submit0(View view){
        String myInputText = ticker0.getText().toString();
//        Toast.makeText(getApplicationContext(), "ticker is : " + ticker, Toast.LENGTH_SHORT ).show();
    }
    public void submit1(View view){
        String myInputText = ticker1.getText().toString();
//        Toast.makeText(getApplicationContext(), "ticker is : " + ticker, Toast.LENGTH_SHORT ).show();
    }
    public void submit2(View view){
        String myInputText = ticker2.getText().toString();
//        Toast.makeText(getApplicationContext(), "ticker is : " + ticker, Toast.LENGTH_SHORT ).show();
    }
    public void submit3(View view){
        String myInputText = ticker3.getText().toString();
//        Toast.makeText(getApplicationContext(), "ticker is : " + ticker, Toast.LENGTH_SHORT ).show();
    }
    public void submit4(View view){
        String myInputText = ticker4.getText().toString();
//        Toast.makeText(getApplicationContext(), "ticker is : " + ticker, Toast.LENGTH_SHORT ).show();
    }

}