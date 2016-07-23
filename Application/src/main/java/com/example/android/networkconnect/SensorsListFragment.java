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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sensor_list_fragment, container, false);

        new GetSensorsDataTask(view).execute("http://192.168.0.120/gettemp.cgi?format=json");

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.Planets, android.R.layout.simple_list_item_1);

        /*
        SensorData tsd1 = new SensorData(1,"Balkon",25.7f,53.3f,3.11f, Timestamp.valueOf("2016-07-13 20:22:22"));
        SensorData tsd2 = new SensorData(2,"Pokój Kasi",25.0f,53.3f,3.11f, Timestamp.valueOf("2016-07-14 20:22:22"));
        SensorData tsd3 = new SensorData(3,"Pokój Basi",22.0f,53.3f,3.11f, Timestamp.valueOf("2016-07-14 20:22:22"));
        SensorData tsd4 = new SensorData(4,"Sypialnia",24.3f,53.3f,3.11f, Timestamp.valueOf("2016-07-14 20:22:22"));


        exampleListItemList = new ArrayList();
        exampleListItemList.add(tsd1);
        exampleListItemList.add(tsd2);
        exampleListItemList.add(tsd3);
        exampleListItemList.add(tsd4);
        SensorDataListAdapter  mAdapter = new SensorDataListAdapter(getActivity(),R.layout.sensor_row_layout, exampleListItemList);



        setListAdapter(mAdapter);
        getListView().setOnItemClickListener(this);
        */
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



            setListAdapter(mAdapter);


        }
    }
}
