package com.ip.project.api.utils;

import java.sql.Timestamp;

public class DateUtils {

    public static Timestamp getTimeStamp(){
        Long datetime = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(datetime);
        return timestamp;
    }
}
