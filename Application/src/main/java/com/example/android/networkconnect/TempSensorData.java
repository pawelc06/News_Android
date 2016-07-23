package com.example.android.networkconnect;

/**
 * Created by Paweł on 2016-04-24.
 */
public class TempSensorData {
    private String outTemp;
    private String outHum;
    private String voltage;
    private String timeStamp;
    private String internalTemp;
    private String lastFrameTimestamp;

    private String temp1;
    private String hum1;
    private String ts1;

    private String temp2;
    private String hum2;
    private String ts2;


    public TempSensorData() {

    }

    public TempSensorData(String outTemp, String voltage, String timeStamp) {
        this.outTemp = outTemp;
        this.voltage = voltage;
        this.timeStamp = timeStamp;
    }

    public TempSensorData(String outTemp,String outHum, String voltage, String timeStamp) {
        this.outTemp = outTemp;
        this.voltage = voltage;
        this.timeStamp = timeStamp;
        this.outHum = outHum;
    }

    public TempSensorData(String outTemp, String outHum, String voltage, String timeStamp, String internalTemp, String lastFrameTimestamp, String temp1, String hum1, String ts1, String temp2, String hum2, String ts2) {
        this.outTemp = outTemp;
        this.outHum = outHum;
        this.voltage = voltage;
        this.timeStamp = timeStamp;
        this.internalTemp = internalTemp;
        this.lastFrameTimestamp = lastFrameTimestamp;
        this.temp1 = temp1;
        this.hum1 = hum1;
        this.ts1 = ts1;
        this.temp2 = temp2;
        this.hum2 = hum2;
        this.ts2 = ts2;
    }

    public String getLastFrameTimestamp() {
        return lastFrameTimestamp;
    }

    public void setLastFrameTimestamp(String lastFrameTimestamp) {
        this.lastFrameTimestamp = lastFrameTimestamp;
    }

    public String getInternalTemp() {
        return internalTemp;
    }

    public void setInternalTemp(String internalTemp) {
        this.internalTemp = internalTemp;
    }

    public String getOutTemp() {
        return outTemp;
    }

    public String getVoltage() {
        return voltage;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setOutTemp(String outTemp) {
        this.outTemp = outTemp;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getOutHum() {
        return outHum;
    }

    public void setOutHum(String outHum) {
        this.outHum = outHum;
    }

    public String getTemp1() {
        return temp1;
    }

    public void setTemp1(String temp1) {
        this.temp1 = temp1;
    }

    public String getHum1() {
        return hum1;
    }

    public void setHum1(String hum1) {
        this.hum1 = hum1;
    }

    public String getTs1() {
        return ts1;
    }

    public void setTs1(String ts1) {
        this.ts1 = ts1;
    }

    public String getTemp2() {
        return temp2;
    }

    public void setTemp2(String temp2) {
        this.temp2 = temp2;
    }

    public String getHum2() {
        return hum2;
    }

    public void setHum2(String hum2) {
        this.hum2 = hum2;
    }

    public String getTs2() {
        return ts2;
    }

    public void setTs2(String ts2) {
        this.ts2 = ts2;
    }
}
