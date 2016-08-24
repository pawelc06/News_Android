package com.example.android.networkconnect;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.common.logger.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paweł on 2016-07-13.
 */
public class SensorsListFragment extends ListFragment implements AdapterView.OnItemClickListener {
    private List exampleListItemList; // at the top of your fragment list
   View view;
    public boolean appRunning=false;

    private void startTimerThread2() {
        final Handler handler = new Handler();


        Runnable runnable = new Runnable() {
            private long startTime = System.currentTimeMillis();
            public void run() {

                while (appRunning) {
                    try {
                        Thread.sleep(10000);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable(){
                        public void run() {


                            new GetSensorsDataTask(view).execute("http://192.168.0.120/gettemp.cgi?format=json");





                        }
                    });
                }
            }
        };
        new Thread(runnable).start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sensor_list_fragment, container, false);

        new GetSensorsDataTask(view).execute("http://192.168.0.120/gettemp.cgi?format=json");
        appRunning = true;
        this.startTimerThread2();

        Log.i("LISTSENSORS","startTimerThread");
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
    }

    private class GetSensorsDataTask extends AsyncTask<String, Void, List <SensorData>> {
        View viewTask;
        String sensorsDataTxt = null;
        List<SensorData> listOfSensorData;

        public GetSensorsDataTask(View tv) {
            this.viewTask = tv;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("LISTSENSORS","PRE");

        }

        protected List<SensorData> doInBackground(String... urls) {

            listOfSensorData = new ArrayList<>();
            String urldisplay = urls[0];

            String paramsStr = null;




            InputStream in;
            SensorData sd;

            try {
                in = new java.net.URL(urldisplay).openStream();




                listOfSensorData = JsonSensorDataParser.parseData(in);









            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return listOfSensorData;
        }

        protected void onPostExecute(List<SensorData> result) {
            SensorDataListAdapter  mAdapter = new SensorDataListAdapter(getActivity(),R.layout.sensor_row_layout, result);


            Log.i("LISTSENSORS","POST");
            setListAdapter(mAdapter);


        }
    }
}
