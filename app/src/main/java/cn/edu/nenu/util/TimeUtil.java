package cn.edu.nenu.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

//    获取当前系统时间，由于ubuntu设置，时间相差八小时，利用formatTimeEight更新时间格式
    public static String formatTimeEight(String time) throws Exception {
        Date date = null;
        date = sdf.parse(time);
        long rTime = (long)(date.getTime() + 8 * 60 * 60 * 1000);
        return sdf.format(rTime);
    }

    public static String getCurrentSystemTime() {
        Date date = new Date();
        String currentTime = null;
        try {
            currentTime = formatTimeEight(sdf.format(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return currentTime;
    }
}
