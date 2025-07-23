package ai.deepdetect.utils;

import java.util.Calendar;
import java.util.Date;

import ai.deepdetect.exceptions.OTPExpiredException;

public class DateTimeUtils {

    public static Date addHours(int hrs) {
        Calendar calendar = Calendar.getInstance(); 
        calendar.add(Calendar.HOUR, hrs); 
        return calendar.getTime();
    }

    public static boolean isTokenInTime(Date expirationDate) throws OTPExpiredException {
        Date now = new Date();
        
        if (expirationDate.before(now)) 
            throw new OTPExpiredException();

        return true;

    }

}
