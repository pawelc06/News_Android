package com.example.android.networkconnect;

/**
 * Created by Paweł on 2016-04-24.
 */
public class TempSensorData {
    private String temp;
    private String voltage;
    private String timeStamp;

    public TempSensorData() {

    }

    public TempSensorData(String temp, String voltage, String timeStamp) {
        this.temp = temp;
        this.voltage = voltage;
        this.timeStamp = timeStamp;
    }

    public String getTemp() {
        return temp;
    }

    public String getVoltage() {
        return voltage;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
