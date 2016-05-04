package com.example.android.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Paweł on 2016-04-26.
 */
final public class DateTimeUtil {
    public static String getDateInURL(String param){
        String strDate,strURLDate, hourStr;

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        strDate = dateFormat.format(date);


        hourStr = "06";

        /*
        if(date.getHours() < 12){
            hourStr = "06";
        } else {
            hourStr = "12";
        }
        */

        //strURLDate = "http://www.meteo.pl/um/metco/mgram_pict.php?ntype=0u&fdate=" + strDate + hourStr + "&row=406&col=250&lang=pl";
        strURLDate = "http://www.meteo.pl/um/metco/mgram_pict.php?ntype=0u&fdate=" + param + "&row=406&col=250&lang=pl";

        return strURLDate;
        //System.out.println(dateFormat.format(date)); //2014/08/06 15:59:48
    }
}
