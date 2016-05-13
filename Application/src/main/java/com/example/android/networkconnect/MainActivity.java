/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.networkconnect;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.common.logger.Log;
import com.example.android.common.logger.LogFragment;
import com.example.android.common.logger.LogWrapper;
import com.example.android.common.logger.MessageOnlyLogFilter;
import com.example.android.util.DateTimeUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;



/**
 * Sample application demonstrating how to connect to the network and fetch raw
 * HTML. It uses AsyncTask to do the fetch on a background thread. To establish
 * the network connection, it uses HttpURLConnection.
 *
 * This sample uses the logging framework to display log output in the log
 * fragment (LogFragment).
 */
public class MainActivity extends FragmentActivity {

    public static final String TAG = "LOG:";
    ImageView i;
    private Bitmap bitmap;
    ImageView imageView1;

    private static final int PROGRESS = 0x1;

    private ProgressBar mProgress1, mProgress2;
    private int mProgressStatus1 = 0;

    private Handler mHandler = new Handler();
    public String meteoURL;
    public boolean appRunning=false;
    public String resHtml,voltage,lTimestamp;
    public TempSensorData tsd1;


    // Reference to the fragment showing events, so we can clear it with a button
    // as necessary.
    private LogFragment mLogFragment;

    private void startTimerThread() {
        final Handler handler = new Handler();


        Runnable runnable = new Runnable() {
            private long startTime = System.currentTimeMillis();
            public void run() {

                while (appRunning) {
                    try {
                        Thread.sleep(180000);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable(){
                        public void run() {
                            mProgress1 = (ProgressBar) findViewById(R.id.progressBarMeteo);
                            mProgress2 = (ProgressBar) findViewById(R.id.progressBarPogodynka);

                            mProgress1.setVisibility(View.INVISIBLE);
                            mProgress2.setVisibility(View.INVISIBLE);





                            new DownloadImageTaskFromHtml((ImageView) findViewById(R.id.imageView1),mProgress1).execute("http://www.meteo.pl/um/ramka_um_city_pl.php");

                            new DownloadImageTaskProgress((ImageView) findViewById(R.id.imageView2),mProgress2) .execute("http://pogodynka.pl/http/assets/products/main_page_maps/day_v2_radarmode_0000-00-00d.jpg#0.007399700165709233");

                            new GetTempTask((TextView) findViewById(R.id.outTemperatureView)) .execute("http://192.168.0.120/gettemp.cgi?format=json");

                            new GetParamsTask((TextView) findViewById(R.id.paramTextView)) .execute("http://192.168.0.120/gettemp.cgi?format=json");



                        }
                    });
                }
            }
        };
        new Thread(runnable).start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_main);

        mProgress1 = (ProgressBar) findViewById(R.id.progressBarMeteo);
        mProgress2 = (ProgressBar) findViewById(R.id.progressBarPogodynka);

        mProgress1.setVisibility(View.INVISIBLE);
        mProgress2.setVisibility(View.INVISIBLE);



        new DownloadImageTaskFromHtml((ImageView) findViewById(R.id.imageView1),mProgress1).execute("http://www.meteo.pl/um/ramka_um_city_pl.php");

        new DownloadImageTaskProgress((ImageView) findViewById(R.id.imageView2),mProgress2) .execute("http://pogodynka.pl/http/assets/products/main_page_maps/day_v2_radarmode_0000-00-00d.jpg#0.007399700165709233");



        new GetTempTask((TextView) findViewById(R.id.outTemperatureView)) .execute("http://192.168.0.120/gettemp.cgi?format=json");

        new GetParamsTask((TextView) findViewById(R.id.paramTextView)) .execute("http://192.168.0.120/gettemp.cgi?format=json");

        appRunning = true;
        this.startTimerThread();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        final AsyncTask dt1, dt2;

        switch (item.getItemId()) {
            // When the user clicks FETCH, fetch the first 500 characters of
            // raw HTML from www.google.com.
            case R.id.fetch_action:


                new DownloadImageTaskFromHtml((ImageView) findViewById(R.id.imageView1),mProgress1).execute("http://www.meteo.pl/um/ramka_um_city_pl.php");



                //new DownloadImageTask((ImageView) findViewById(R.id.imageView2)).execute("http://pogodynka.pl/http/assets/products/main_page_maps/day_v2_radarmode_0000-00-00d.jpg#0.007399700165709233");
                new DownloadImageTaskProgress((ImageView) findViewById(R.id.imageView2),mProgress2).execute("http://pogodynka.pl/http/assets/products/main_page_maps/day_v2_radarmode_0000-00-00d.jpg#0.007399700165709233");

                new GetTempTask((TextView) findViewById(R.id.outTemperatureView)).execute("http://192.168.0.120/gettemp.cgi?format=json");

                new GetParamsTask((TextView) findViewById(R.id.paramTextView)) .execute("http://192.168.0.120/gettemp.cgi?format=json");

                return true;
            // Clear the log view fragment.
            case R.id.clear_action:
                //mLogFragment.getLogView().setText("");
                return true;
        }
        return false;
    }


    private class GetParamsTask extends AsyncTask<String, Void, String> {
        TextView txtView;
        String paramStr = null;

        public GetParamsTask(TextView tv) {
            this.txtView = tv;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            TextView tv = (TextView) findViewById(R.id.paramTextView);
            tv.setText("Odbieram dane...");

        }

        protected String doInBackground(String... urls) {
            String urldisplay = urls[0];

            String paramsStr = null;

            String volStr = null;
            String dateStr=null;
            String internalTemp;
            String lastFrameTimestamp;
            InputStream in;
            TempSensorData tsd;

            try {
                in = new java.net.URL(urldisplay).openStream();

                /*
                Reader reader = null;
                reader = new InputStreamReader(in, "UTF-8");
                char[] buffer = new char[100];
                reader.read(buffer);
                tempJson = new String(buffer);
                */
                tsd = JsonTempParser.parseData(in);

                //dateStr = tsd.getTimeStamp();

                lastFrameTimestamp = tsd.getLastFrameTimestamp();

                volStr = tsd.getVoltage();

                paramStr = lastFrameTimestamp + "   Bat:" + volStr + "V";


            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return paramStr;
        }

        protected void onPostExecute(String result) {
            txtView.setText(result);

        }
    }


    private class GetTempTask extends AsyncTask<String, Void, String> {
        TextView txtView;
        String paramStr = null;

        public GetTempTask(TextView tv) {
            this.txtView = tv;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            TextView tv = (TextView) findViewById(R.id.outTemperatureView);
            tv.setText("Odbieram dane...");

        }

        protected String doInBackground(String... urls) {
            String urldisplay = urls[0];
            String tempJson = null;
            String tempStr = null;

            String volStr = null;
            String dateStr=null;
            String internalTemp;
            String lastFrameTimestamp;
            InputStream in;
            TempSensorData tsd;

            try {
                in = new java.net.URL(urldisplay).openStream();

                /*
                Reader reader = null;
                reader = new InputStreamReader(in, "UTF-8");
                char[] buffer = new char[100];
                reader.read(buffer);
                tempJson = new String(buffer);
                */
                tsd = JsonTempParser.parseData(in);
                tempStr = tsd.getTemp();
                volStr = tsd.getVoltage();
                dateStr = tsd.getTimeStamp();
                internalTemp = tsd.getInternalTemp();
                lastFrameTimestamp = tsd.getLastFrameTimestamp();
                tempStr = "Balkon: " + tempStr + " \u00b0C\r\n";
                tempStr += "Salon:   +" +internalTemp  + " \u00b0C\n";
                paramStr = lastFrameTimestamp + "   Bat:" + volStr;


            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return tempStr;
        }

        protected void onPostExecute(String result) {
            txtView.setText(result);

        }
    }


    private class DownloadHtmlTask extends AsyncTask<String, Void, String> {
        String url;
        String  resHtml1;

        DownloadHtmlTask(String  resHtml1) {
            this.resHtml1 = resHtml1;
        }

        protected String doInBackground(String... urls) {
            String url = urls[0];
            String htmlResponse=null;
            InputStream in;

            try {


                in = new java.net.URL(url).openStream();


                Reader reader = null;
                reader = new InputStreamReader(in, "UTF-8");
                char[] buffer = new char[4096];
                reader.read(buffer);
                htmlResponse = new String(buffer);

            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return htmlResponse;
        }

        protected void onPostExecute(String result) {

            resHtml = parseParams(result);

        }

        private String parseParams(String res){
            String parStr="";
            int ix;

            ix = res.indexOf("UM_SYYYY=");
            parStr+=res.substring(ix+10,ix+14);

            ix = res.indexOf("UM_SMM=");
            parStr+=res.substring(ix+8,ix+10);

            ix = res.indexOf("UM_SDD=");
            parStr+=res.substring(ix+8,ix+10);

            ix = res.indexOf("UM_SST");
            parStr+=res.substring(ix+8,ix+10);

            return parStr;
        }

    }

    private class DownloadImageTaskFromHtml extends AsyncTask<String, Integer, Bitmap> {

        ImageView bmImage;
        ProgressBar mPb;

        public DownloadImageTaskFromHtml(ImageView iv, ProgressBar pb){
            this.bmImage = iv;
            this.mPb = pb;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mPb.setVisibility(View.VISIBLE);
            //params[0].setVisibility(View.VISIBLE);
        }

        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            String urldisplay="";
            Bitmap mIcon11 = null;
            String htmlResponse="";
            String params="";
            InputStream in;

            try {

                in = new java.net.URL(url).openStream();


                Reader reader = null;
                reader = new InputStreamReader(in, "UTF-8");
                char[] buffer = new char[4096];
                reader.read(buffer);
                htmlResponse = new String(buffer);

               params = parseParams(htmlResponse);

                meteoURL = DateTimeUtil.getDateInURL(params);


                in = new java.net.URL(meteoURL).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // Executes whenever publishProgress is called from doInBackground
            // Used to update the progress indicator
            mPb.setProgress(values[0]);

        }

        protected void onPostExecute(Bitmap result) {
            // This method is executed in the UIThread
            // with access to the result of the long running task
            this.bmImage.setImageBitmap(result);
            // Hide the progress bar
            mPb.setVisibility(ProgressBar.INVISIBLE);
        }

        private String parseParams(String res){
            String parStr="";
            int ix;

            ix = res.indexOf("UM_SYYYY=");
            parStr+=res.substring(ix+10,ix+14);

            ix = res.indexOf("UM_SMM=");
            parStr+=res.substring(ix+8,ix+10);

            ix = res.indexOf("UM_SDD=");
            parStr+=res.substring(ix+8,ix+10);

            ix = res.indexOf("UM_SST");
            parStr+=res.substring(ix+8,ix+10);

            return parStr;
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }



    private class DownloadImageTaskProgress extends AsyncTask<String, Integer, Bitmap> {

        ImageView bmImage;
        ProgressBar mPb;

        public DownloadImageTaskProgress(ImageView iv, ProgressBar pb){
            this.bmImage = iv;
            this.mPb = pb;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mPb.setVisibility(View.VISIBLE);
            //params[0].setVisibility(View.VISIBLE);
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // Executes whenever publishProgress is called from doInBackground
            // Used to update the progress indicator
            mPb.setProgress(values[0]);

        }

        protected void onPostExecute(Bitmap result) {
            // This method is executed in the UIThread
            // with access to the result of the long running task
            this.bmImage.setImageBitmap(result);
            // Hide the progress bar
            mPb.setVisibility(ProgressBar.INVISIBLE);
        }


        /**
         * Create a chain of targets that will receive log data
         */


    }

}





