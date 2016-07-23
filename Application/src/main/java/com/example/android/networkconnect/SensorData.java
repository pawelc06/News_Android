package com.example.android.networkconnect;

import java.sql.Timestamp;

/**
 * Created by Paweł on 2016-07-15.
 */
public class SensorData {
    int sensorId;
    String sensorName;
    float temp;
    float hum;
    float vbat;
    String ts;

    public SensorData(int sensorId, String sensorName, float temp, float hum, float vbat, String ts) {
        this.sensorId = sensorId;
        this.sensorName = sensorName;
        this.temp = temp;
        this.hum = hum;
        this.vbat = vbat;
        this.ts = ts;
    }

    public SensorData() {
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public float getTemp() {
        return temp;
    }

    public String getTempString() {
        return Float.toString(temp)+"°C";

    }

    public String getHumString() {
        return Float.toString(hum)+" %";
    }

    public String getVbatString() {
        return Float.toString(vbat)+" V";
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public float getHum() {
        return hum;
    }

    public void setHum(float hum) {
        this.hum = hum;
    }

    public float getVbat() {
        return vbat;
    }

    public void setVbat(float vbat) {
        this.vbat = vbat;
    }

    public String getTs() {
        return ts;
    }

    public String getTimestampString() {

        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }


}
