package com.example.android.networkconnect;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.common.logger.Log;

import java.io.InputStream;

public class SensorsFragment extends Fragment {
    View view;

    public static final String EXTRA_URL ="url";
    public boolean appRunning=false;

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
                            new GetSensorsDataTask((TextView) view.findViewById(R.id.sensorDataView)) .execute("http://192.168.0.120/gettemp.cgi?format=json");
                        }
                    });
                }
            }
        };
        new Thread(runnable).start();
    }


      @Override
      public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sensor_data_layout,
                container, false);
          new GetSensorsDataTask((TextView) view.findViewById(R.id.sensorDataView)) .execute("http://192.168.0.120/gettemp.cgi?format=json");


          //view.findViewById(R.id.listView);

          appRunning = true;
          this.startTimerThread();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String link = bundle.getString("url");
            //setText(link);



        }
    }

    public void setText(String url) {
        TextView view1 = (TextView) view.findViewById(R.id.sensorDataView);
        view1.setText(url);
    }

    private class GetSensorsDataTask extends AsyncTask<String, Void, String> {
        TextView txtView;
        String sensorsDataTxt = null;

        public GetSensorsDataTask(TextView tv) {
            this.txtView = tv;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            TextView tv = (TextView) view.findViewById(R.id.sensorDataView);
            tv.setText("Odbieram dane...");

        }

        protected String doInBackground(String... urls) {
            String urldisplay = urls[0];

            String paramsStr = null;

            String volStr = null;
            String dateStr=null;
            String internalTemp;
            String lastFrameTimestamp;
            String outTemp;
            String outHum;
            String temp1;
            String hum1;
            String temp2;
            String hum2;


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
                outTemp = tsd.getOutTemp();
                outHum = tsd.getOutHum();
                temp1 = tsd.getTemp1();
                temp2 = tsd.getTemp2();
                hum1 = tsd.getHum1();
                hum2 = tsd.getHum2();


                sensorsDataTxt = "Pokój Kasi:   "+temp1 +" °C   "+hum1 +"% \n"+ "Pokój Basi:   "+temp2+" °C   "+hum2+"% \n"+ "Balkon:          "+outTemp+" °C   "+outHum+"% \n\n" + "Bateria:          " + volStr + " V";


            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return sensorsDataTxt;
        }

        protected void onPostExecute(String result) {
            txtView.setText(result);

        }
    }

}
