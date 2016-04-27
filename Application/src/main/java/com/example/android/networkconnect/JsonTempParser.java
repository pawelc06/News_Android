package com.example.android.networkconnect;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Paweł on 2016-04-24.
 */
public class JsonTempParser {
    public static TempSensorData parseData(InputStream json) throws Exception{
        BufferedReader reader = new BufferedReader(new InputStreamReader(json));

        StringBuilder sb = new StringBuilder();
        String str;
        int len;

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

        JSONArray jsonReply = new JSONArray(str);
        TempSensorData tempData = new TempSensorData();
        JSONObject jsonObjTemp = jsonReply.getJSONObject(0);
        tempData.setTemp(jsonObjTemp.getString("temp"));
        tempData.setVoltage(jsonObjTemp.getString("voltage"));
        tempData.setTimeStamp(jsonObjTemp.getString("serverTime"));


        return tempData;
    }
}
