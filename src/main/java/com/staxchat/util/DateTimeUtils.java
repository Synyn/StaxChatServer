package com.staxchat.util;

import java.time.Instant;
import java.util.Date;

public class DateTimeUtils {
    public static Date now(){
        return Date.from(Instant.now());
    }


}
