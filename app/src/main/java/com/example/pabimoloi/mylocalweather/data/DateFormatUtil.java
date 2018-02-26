package com.example.pabimoloi.mylocalweather.data;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by PMoloi on 2018/02/26.
 */

public class DateFormatUtil {
    public DateFormatUtil() {
    }
    public SimpleDateFormat getSimpleDateFormat(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E,LLLL d y", Locale.getDefault());
        return simpleDateFormat;
    }
}
