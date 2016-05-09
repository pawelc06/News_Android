package com.example.android.networkconnect;

/**
 * Created by Paweł on 2016-04-24.
 */
public class TempSensorData {
    private String temp;
    private String voltage;
    private String timeStamp;



    private String internalTemp;



    private String lastFrameTimestamp;

    public TempSensorData() {

    }

    public TempSensorData(String temp, String voltage, String timeStamp) {
        this.temp = temp;
        this.voltage = voltage;
        this.timeStamp = timeStamp;
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
