package com.example.serviceexample;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



public class MyService extends Service{
    private Looper serviceLooper;
    private ServiceHandler serviceHandler;

    private static final String REQUEST_METHOD = "GET";
    private static final int READ_TIMEOUT = 15000;
    private static final int CONNECTION_TIMEOUT = 15000;

    private String ticker0 ;
    private String ticker1;
    private String ticker2;
    private String ticker3;
    private String ticker4;
    private String[] TArr = new String[5];
    private String token ="c94mbs2ad3if4j517ve0"; // put your own token

    private final class ServiceHandler extends Handler{
        public ServiceHandler(Looper looper){
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {

            // url to get historical data

            String stringUrl0 = "https://finnhub.io/api/v1/stock/candle?symbol=" + ticker0
                    + "&resolution=M&from=1625097601&to=1640995199&token=" + token;
            String result0;
            String inputLine0;
            try {

                // make GET requests

                URL myUrl0 = new URL(stringUrl0);
                HttpURLConnection connection0 = (HttpURLConnection) myUrl0.openConnection();

                connection0.setRequestMethod(REQUEST_METHOD);
                connection0.setReadTimeout(READ_TIMEOUT);
                connection0.setConnectTimeout(CONNECTION_TIMEOUT);

                connection0.connect();

                // store json string from GET response

                InputStreamReader streamReader = new InputStreamReader(connection0.getInputStream());
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();

                while ((inputLine0 = reader.readLine()) != null) {
                    stringBuilder.append(inputLine0);
                }

                reader.close();
                streamReader.close();

                result0 = stringBuilder.toString();

            } catch (IOException e) {
                e.printStackTrace();
                result0 = null;

                Thread.currentThread().interrupt();
            }

            // parse the json string into 'close' and 'volume' array

            JSONObject jsonObject0 = null;
            JSONArray jsonArrayClose0 = null;
            JSONArray jsonArrayOpen0 = null;


            try {
                jsonObject0 = new JSONObject(result0);
                jsonArrayClose0 = jsonObject0.getJSONArray("c");
                jsonArrayOpen0 = jsonObject0.getJSONArray("o");

            } catch (JSONException e) {
                e.printStackTrace();
            }


            try {
                try {
                    for (int i = 0; i < jsonArrayClose0.length(); i++) {
                        double close = jsonArrayClose0.getDouble(i);
                        double open = jsonArrayOpen0.getDouble(i);

                        Log.v("data", i + ":, c: " + close);

                        ContentValues values = new ContentValues();
                        values.put(HistoricalDataProvider.TICKER_NAME, "0");
                        values.put(HistoricalDataProvider.CLOSE, close);
                        values.put(HistoricalDataProvider.OPEN, open);

                        getContentResolver().insert(HistoricalDataProvider.CONTENT_URI, values);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // broadcast message that download is complete

                Intent intent0 = new Intent("DOWNLOAD_COMPLETE");
                sendBroadcast(intent0);

                stopSelf(msg.arg1);

            }catch (NullPointerException e){
                for (int i = 0; i < 6; i++) {
                    double close = 0;
                    double open = 0;
                    Log.v("data", i + ":, c: " + close );

                    ContentValues values = new ContentValues();
                    values.put(HistoricalDataProvider.TICKER_NAME, "0");
                    values.put(HistoricalDataProvider.CLOSE, close);
                    values.put(HistoricalDataProvider.OPEN, open);

//                    values.put(HistoricalDataProvider.VOLUME, volume);
                    getContentResolver().insert(HistoricalDataProvider.CONTENT_URI, values);

                }
                Intent intent0 = new Intent("DOWNLOAD_COMPLETE");
                sendBroadcast(intent0);

                stopSelf(msg.arg1);
            }
//-------------------------------------------------------------------------------------------------------------------------------

            // url to get historical data

            String stringUrl1 = "https://finnhub.io/api/v1/stock/candle?symbol=" + ticker1
                    + "&resolution=M&from=1625097601&to=1640995199&token=" + token;
            String result1;
            String inputLine1;
            try {

                // make GET requests

                URL myUrl1 = new URL(stringUrl1);
                HttpURLConnection connection1 = (HttpURLConnection) myUrl1.openConnection();

                connection1.setRequestMethod(REQUEST_METHOD);
                connection1.setReadTimeout(READ_TIMEOUT);
                connection1.setConnectTimeout(CONNECTION_TIMEOUT);

                connection1.connect();

                // store json string from GET response

                InputStreamReader streamReader = new InputStreamReader(connection1.getInputStream());
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();

                while ((inputLine1 = reader.readLine()) != null) {
                    stringBuilder.append(inputLine1);
                }

                reader.close();
                streamReader.close();

                result1 = stringBuilder.toString();

            } catch (IOException e) {
                e.printStackTrace();
                result1 = null;

                Thread.currentThread().interrupt();
            }

            // parse the json string into 'close' and 'volume' array

            JSONObject jsonObject1 = null;
            JSONArray jsonArrayClose1 = null;
            JSONArray jsonArrayOpen1 = null;


            try {
                jsonObject1 = new JSONObject(result1);
                jsonArrayClose1 = jsonObject1.getJSONArray("c");
                jsonArrayOpen1 = jsonObject1.getJSONArray("o");

            } catch (JSONException e) {
                e.printStackTrace();
            }


            try {
                try {
                    for (int i = 0; i < jsonArrayClose1.length(); i++) {
                        double close = jsonArrayClose1.getDouble(i);
                        double open = jsonArrayOpen1.getDouble(i);

//                        Log.v("data", i + ":, c: " + close);

                        ContentValues values = new ContentValues();
                        values.put(HistoricalDataProvider.TICKER_NAME, "1");
                        values.put(HistoricalDataProvider.CLOSE, close);
                        values.put(HistoricalDataProvider.OPEN, open);

                        getContentResolver().insert(HistoricalDataProvider.CONTENT_URI, values);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // broadcast message that download is complete

                Intent intent0 = new Intent("DOWNLOAD_COMPLETE");
                sendBroadcast(intent0);

                stopSelf(msg.arg1);

            }catch (NullPointerException e){
                for (int i = 0; i < 6; i++) {
                    double close = 0;
                    double open = 0;
                    Log.v("data", i + ":, c: " + close );

                    ContentValues values = new ContentValues();
                    values.put(HistoricalDataProvider.TICKER_NAME, "1");
                    values.put(HistoricalDataProvider.CLOSE, close);
                    values.put(HistoricalDataProvider.OPEN, open);

                    getContentResolver().insert(HistoricalDataProvider.CONTENT_URI, values);

                }
                Intent intent1 = new Intent("DOWNLOAD_COMPLETE");
                sendBroadcast(intent1);

                stopSelf(msg.arg1);
            }
            //-------------------------------------------------------------------------------------------------------------------------------

            // url to get historical data

            String stringUrl2 = "https://finnhub.io/api/v1/stock/candle?symbol=" + ticker2
                    + "&resolution=M&from=1625097601&to=1640995199&token=" + token;
            String result2;
            String inputLine2;
            try {

                // make GET requests

                URL myUrl2 = new URL(stringUrl2);
                HttpURLConnection connection2 = (HttpURLConnection) myUrl2.openConnection();

                connection2.setRequestMethod(REQUEST_METHOD);
                connection2.setReadTimeout(READ_TIMEOUT);
                connection2.setConnectTimeout(CONNECTION_TIMEOUT);

                connection2.connect();

                // store json string from GET response

                InputStreamReader streamReader = new InputStreamReader(connection2.getInputStream());
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();

                while ((inputLine2 = reader.readLine()) != null) {
                    stringBuilder.append(inputLine2);
                }

                reader.close();
                streamReader.close();

                result2 = stringBuilder.toString();

            } catch (IOException e) {
                e.printStackTrace();
                result2 = null;

                Thread.currentThread().interrupt();
            }

            // parse the json string into 'close' and 'volume' array

            JSONObject jsonObject2 = null;
            JSONArray jsonArrayClose2 = null;
            JSONArray jsonArrayOpen2 = null;


            try {
                jsonObject2 = new JSONObject(result2);
                jsonArrayClose2 = jsonObject2.getJSONArray("c");
                jsonArrayOpen2 = jsonObject2.getJSONArray("o");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                try {
                    for (int i = 0; i < jsonArrayClose2.length(); i++) {
                        double close = jsonArrayClose2.getDouble(i);
                        double open = jsonArrayOpen2.getDouble(i);

                        Log.v("data", i + ":, c: " + close);

                        ContentValues values = new ContentValues();
                        values.put(HistoricalDataProvider.TICKER_NAME, "2");
                        values.put(HistoricalDataProvider.CLOSE, close);
                        values.put(HistoricalDataProvider.OPEN, open);

                        getContentResolver().insert(HistoricalDataProvider.CONTENT_URI, values);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // broadcast message that download is complete

                Intent intent0 = new Intent("DOWNLOAD_COMPLETE");
                sendBroadcast(intent0);

                stopSelf(msg.arg2);

            }catch (NullPointerException e){
                for (int i = 0; i < 6; i++) {
                    double close = 0;
                    double open = 0;
                    Log.v("data", i + ":, c: " + close );

                    ContentValues values = new ContentValues();
                    values.put(HistoricalDataProvider.TICKER_NAME, "2");
                    values.put(HistoricalDataProvider.CLOSE, close);
                    values.put(HistoricalDataProvider.OPEN, open);

                    getContentResolver().insert(HistoricalDataProvider.CONTENT_URI, values);

                }
                Intent intent2 = new Intent("DOWNLOAD_COMPLETE");
                sendBroadcast(intent2);

                stopSelf(msg.arg1);
            }
            //-------------------------------------------------------------------------------------------------------------------------------

            // url to get historical data

            String stringUrl3 = "https://finnhub.io/api/v1/stock/candle?symbol=" + ticker3
                    + "&resolution=M&from=1625097601&to=1640995199&token=" + token;
            String result3;
            String inputLine3;
            try {

                // make GET requests

                URL myUrl3 = new URL(stringUrl3);
                HttpURLConnection connection3 = (HttpURLConnection) myUrl3.openConnection();

                connection3.setRequestMethod(REQUEST_METHOD);
                connection3.setReadTimeout(READ_TIMEOUT);
                connection3.setConnectTimeout(CONNECTION_TIMEOUT);

                connection3.connect();

                // store json string from GET response

                InputStreamReader streamReader = new InputStreamReader(connection3.getInputStream());
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();

                while ((inputLine3 = reader.readLine()) != null) {
                    stringBuilder.append(inputLine3);
                }

                reader.close();
                streamReader.close();

                result3 = stringBuilder.toString();

            } catch (IOException e) {
                e.printStackTrace();
                result3 = null;

                Thread.currentThread().interrupt();
            }

            // parse the json string into 'close' and 'volume' array

            JSONObject jsonObject3 = null;
            JSONArray jsonArrayClose3 = null;
            JSONArray jsonArrayOpen3 = null;


            try {
                jsonObject3 = new JSONObject(result3);
                jsonArrayClose3 = jsonObject3.getJSONArray("c");
                jsonArrayOpen3 = jsonObject3.getJSONArray("o");

            } catch (JSONException e) {
                e.printStackTrace();
            }


            try {
                try {
                    for (int i = 0; i < jsonArrayClose3.length(); i++) {
                        double close = jsonArrayClose3.getDouble(i);
                        double open = jsonArrayOpen3.getDouble(i);

                        Log.v("data", i + ":, c: " + close);

                        ContentValues values = new ContentValues();
                        values.put(HistoricalDataProvider.TICKER_NAME, "3");
                        values.put(HistoricalDataProvider.CLOSE, close);
                        values.put(HistoricalDataProvider.OPEN, open);
                        getContentResolver().insert(HistoricalDataProvider.CONTENT_URI, values);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // broadcast message that download is complete

                Intent intent0 = new Intent("DOWNLOAD_COMPLETE");
                sendBroadcast(intent0);

                stopSelf(msg.arg1);

            }catch (NullPointerException e){
                for (int i = 0; i < 6; i++) {
                    double close = 0;
                    double open = 0;

                    Log.v("data", i + ":, c: " + close );

                    ContentValues values = new ContentValues();
                    values.put(HistoricalDataProvider.TICKER_NAME, "3");
                    values.put(HistoricalDataProvider.CLOSE, close);
                    values.put(HistoricalDataProvider.OPEN, open);
                    getContentResolver().insert(HistoricalDataProvider.CONTENT_URI, values);

                }
                Intent intent3 = new Intent("DOWNLOAD_COMPLETE");
                sendBroadcast(intent3);

                stopSelf(msg.arg1);
            }
            //-------------------------------------------------------------------------------------------------------------------------------

            // url to get historical data

            String stringUrl4 = "https://finnhub.io/api/v1/stock/candle?symbol=" + ticker4
                    + "&resolution=M&from=1625097601&to=1640995199&token=" + token;
            String result4;
            String inputLine4;
            try {

                // make GET requests

                URL myUrl4 = new URL(stringUrl4);
                HttpURLConnection connection4 = (HttpURLConnection) myUrl4.openConnection();

                connection4.setRequestMethod(REQUEST_METHOD);
                connection4.setReadTimeout(READ_TIMEOUT);
                connection4.setConnectTimeout(CONNECTION_TIMEOUT);

                connection4.connect();

                // store json string from GET response

                InputStreamReader streamReader = new InputStreamReader(connection4.getInputStream());
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();

                while ((inputLine4 = reader.readLine()) != null) {
                    stringBuilder.append(inputLine4);
                }

                reader.close();
                streamReader.close();

                result4 = stringBuilder.toString();

            } catch (IOException e) {
                e.printStackTrace();
                result4 = null;

                Thread.currentThread().interrupt();
            }

            // parse the json string into 'close' and 'volume' array

            JSONObject jsonObject4 = null;
            JSONArray jsonArrayClose4 = null;
            JSONArray jsonArrayOpen4 = null;

            try {
                jsonObject4 = new JSONObject(result4);
                jsonArrayClose4 = jsonObject4.getJSONArray("c");
                jsonArrayOpen4 = jsonObject4.getJSONArray("o");

            } catch (JSONException e) {
                e.printStackTrace();
            }


            try {
                try {
                    for (int i = 0; i < jsonArrayClose4.length(); i++) {
                        double close = jsonArrayClose4.getDouble(i);
                        double open = jsonArrayOpen4.getDouble(i);

                        Log.v("data", i + ":, c: " + close);

                        ContentValues values = new ContentValues();
                        values.put(HistoricalDataProvider.TICKER_NAME, "4");
                        values.put(HistoricalDataProvider.CLOSE, close);
                        values.put(HistoricalDataProvider.OPEN, open);

                        getContentResolver().insert(HistoricalDataProvider.CONTENT_URI, values);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // broadcast message that download is complete

                Intent intent0 = new Intent("DOWNLOAD_COMPLETE");
                sendBroadcast(intent0);

                stopSelf(msg.arg1);

            }catch (NullPointerException e){
                for (int i = 0; i < 6; i++) {
                    double close = 0;
                    double open = 0;
                    Log.v("data", i + ":, c: " + close );

                    ContentValues values = new ContentValues();
                    values.put(HistoricalDataProvider.TICKER_NAME, "4");
                    values.put(HistoricalDataProvider.CLOSE, close);
                    values.put(HistoricalDataProvider.OPEN, open);

                    getContentResolver().insert(HistoricalDataProvider.CONTENT_URI, values);

                }
                Intent intent4 = new Intent("DOWNLOAD_COMPLETE");
                sendBroadcast(intent4);

                stopSelf(msg.arg1);
            }
        }
    }

    @Override
    public void onCreate(){
        HandlerThread thread = new HandlerThread("Service", Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        serviceLooper = thread.getLooper();
        serviceHandler = new ServiceHandler(serviceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        // Receive the tickers from MainActivity
        ticker0 = intent.getStringExtra("ticker0");
        ticker1 = intent.getStringExtra("ticker1");
        ticker2 = intent.getStringExtra("ticker2");
        ticker3 = intent.getStringExtra("ticker3");
        ticker4 = intent.getStringExtra("ticker4");
        TArr[0] = ticker0;
        TArr[1] = ticker1;
        TArr[2] = ticker2;
        TArr[3] = ticker3;
        TArr[4] = ticker4;
        Toast.makeText(this, "download starting", Toast.LENGTH_SHORT).show();

        Message msg = serviceHandler.obtainMessage();
        msg.arg1 = startId;
        serviceHandler.sendMessage(msg);

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

    @Override
    public void onDestroy(){ Toast.makeText(this, "download done", Toast.LENGTH_SHORT).show(); }
}
