package com.example.android.networkconnect;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paweł on 2016-04-24.
 */
public class JsonSensorDataParser {
    public static List<SensorData> parseData(InputStream json) throws Exception{
        BufferedReader reader = new BufferedReader(new InputStreamReader(json));

        StringBuilder sb = new StringBuilder();
        String str;
        int len;
        ArrayList sensorDataList;
        SensorData sensorData=null;

        try{
            String line = reader.readLine();
            while(line != null){
                sb.append(line);
                line = reader.readLine();
            }

        } catch (IOException e) {
            throw e;
        } finally {
            reader.close();
        }
        str = sb.toString();
        //len = sb.length();

       // str = str.substring(0,len-1);

        sensorDataList = new ArrayList();

        JSONArray jsonReply = new JSONArray(str);
        sensorData = new SensorData();
        JSONObject jsonObjTemp = jsonReply.getJSONObject(0);

        sensorData.setSensorId(1);
        sensorData.setSensorName("Balkon");
        sensorData.setTemp((float)jsonObjTemp.getDouble("outTemp"));
        sensorData.setHum((float)jsonObjTemp.getDouble("outHum"));
        sensorData.setVbat((float)jsonObjTemp.getDouble("voltage"));
        sensorData.setTs(jsonObjTemp.getString("lastFrameTimestamp"));

        sensorDataList.add(sensorData);

        sensorData = new SensorData();

        sensorData.setSensorId(2);
        sensorData.setSensorName("Pokój Kasi");
        sensorData.setTemp((float)jsonObjTemp.getDouble("temp1"));
        sensorData.setHum((float)jsonObjTemp.getDouble("hum1"));
        sensorData.setVbat((float)jsonObjTemp.getDouble("vbat1"));
        sensorData.setTs(jsonObjTemp.getString("ts1"));

        sensorDataList.add(sensorData);

        sensorData = new SensorData();

        sensorData.setSensorId(3);
        sensorData.setSensorName("Pokój Basi");
        sensorData.setTemp((float)jsonObjTemp.getDouble("temp2"));
        sensorData.setHum((float)jsonObjTemp.getDouble("hum2"));
        sensorData.setVbat((float)jsonObjTemp.getDouble("vbat2"));
        sensorData.setTs(jsonObjTemp.getString("ts2"));

        sensorDataList.add(sensorData);

        sensorData = new SensorData();

        sensorData.setSensorId(4);
        sensorData.setSensorName("Sypialnia");
        sensorData.setTemp((float)jsonObjTemp.getDouble("temp3"));
        sensorData.setHum((float)jsonObjTemp.getDouble("hum3"));
        sensorData.setVbat((float)jsonObjTemp.getDouble("vbat3"));
        sensorData.setTs(jsonObjTemp.getString("ts3"));

        sensorDataList.add(sensorData);

        sensorData = new SensorData();

        sensorData.setSensorId(0);
        sensorData.setSensorName("Salon");
        sensorData.setTemp((float)jsonObjTemp.getDouble("temp0"));
        sensorData.setHum((float)jsonObjTemp.getDouble("hum0"));

        sensorData.setTs(jsonObjTemp.getString("serverTime"));

        sensorDataList.add(sensorData);


        return sensorDataList;
    }
}
