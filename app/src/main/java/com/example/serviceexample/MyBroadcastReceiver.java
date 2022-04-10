package com.example.serviceexample;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Iterator;
import java.lang.Math;

public class MyBroadcastReceiver extends BroadcastReceiver {
    public static volatile int count = 0;

    private final Handler handler;

    public MyBroadcastReceiver(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("DOWNLOAD_COMPLETE")) { //Wait until DOWNLOAD_COMPLETE is received by this

            handler.post(new Runnable() {
                @Override
                public void run() {
                    int tempcount = count++; //Temporary variable responsible for handling the indexing and cursor position
                    Uri CONTENT_URI = Uri.parse("content://com.example.serviceexample.HistoricalDataProvider/history");
                    TextView result0 = (TextView) ((Activity)context).findViewById(R.id.textview_result0);
                    TextView result1 = (TextView) ((Activity)context).findViewById(R.id.textview_result1);
                    TextView result2 = (TextView) ((Activity)context).findViewById(R.id.textview_result2);
                    TextView result3 = (TextView) ((Activity)context).findViewById(R.id.textview_result3);
                    TextView result4 = (TextView) ((Activity)context).findViewById(R.id.textview_result4);
                    TextView sd0 = (TextView) ((Activity)context).findViewById(R.id.sd_0);
                    TextView sd1 = (TextView) ((Activity)context).findViewById(R.id.sd_1);
                    TextView sd2 = (TextView) ((Activity)context).findViewById(R.id.sd_2);
                    TextView sd3 = (TextView) ((Activity)context).findViewById(R.id.sd_3);
                    TextView sd4 = (TextView) ((Activity)context).findViewById(R.id.sd_4);
                    ArrayList diff_values = new ArrayList<Double>(6);
                    TextView[] resArr = {result0, result1,result2, result3,result4} ;
                    TextView[] sdArr = {sd0,sd1,sd2,sd3,sd4};
                        double diff_price = 0.0;
                        Cursor cursor = context.getContentResolver().query(CONTENT_URI, null, null, null, null);
                        if (cursor.moveToFirst()) {
                            double close = cursor.getDouble(cursor.getColumnIndexOrThrow("close"));
                            double open = cursor.getDouble(cursor.getColumnIndexOrThrow("open"));

                            for (int k = 0; k < 6 * tempcount;k++){ //To move cursor to start of data for next ticker
                                cursor.moveToNext();
                            }
                            for (int j = 0; j < 6; j++) {

                                int res;
                                int id = cursor.getColumnIndex("id");
                                close = cursor.getDouble(cursor.getColumnIndexOrThrow("close"));
                                open = cursor.getDouble(cursor.getColumnIndexOrThrow("open"));
                                diff_price += (close-open)/open;
                                diff_values.add(diff_price);
                                cursor.moveToNext();
                                Log.v("data", close + "");
                            }
                        } else {
                            result0.setText("No Records Found");
                            result1.setText("No Records Found");
                            result2.setText("No Records Found");
                            result3.setText("No Records Found");
                            result4.setText("No Records Found");
                        }

                        double avg_diff_price = diff_price/6;
                        Iterator diff_values_iterator = diff_values.iterator();
                        double sd_sum = 0.0;
                        while (diff_values_iterator.hasNext()) {
                            double diff_values_val = (double)diff_values_iterator.next();
                            sd_sum += Math.pow((diff_values_val-avg_diff_price), 2);
                        }
                        double sd = Math.sqrt(sd_sum/5);
                        double annualized_volatility = (Math.sqrt(12) * sd) * 100;

                        double annualized_return = ((diff_price/6) * 12) * 100;

                        if (annualized_return == 0.00){
                            resArr[tempcount].setText("NA"); //To set the results to NA in case the annualized return or volatility returns 0
                            sdArr[tempcount].setText("NA");
                        }
                        else{
                            resArr[tempcount].setText(String.format("%.2f%s", annualized_return, "%"));
                            sdArr[tempcount].setText(String.format("%.2f%s", annualized_volatility, "%"));
                        }



                }
            });
        }
    }
}
