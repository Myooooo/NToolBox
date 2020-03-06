package com.moyang.ntoolbox;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeUtility {

    public static String formatTime(int format, Date date){

        SimpleDateFormat simpleDateFormat;
        String pattern;
        Locale locale;

        switch(format){
            case (Constants.TIME_ENG_SHORT):
                pattern = "MMM dd yyyy HH:mm";
                locale = Locale.ENGLISH;
                break;
            case (Constants.TIME_ENG_LONG):
                pattern = "yyyy/MM/dd HH:mm:ss";
                locale = Locale.ENGLISH;
                break;
            case (Constants.TIME_CN_SHORT):
                pattern = "yyyy年MM月dd日 aa K:mm";
                locale = Locale.CHINA;
                break;
            case (Constants.TIME_CN_LONG):
                pattern = "yyyy年MM月dd日 HH:mm:ss";
                locale = Locale.CHINA;
                break;
            case (Constants.TIME_CN_MIDDLE):
                pattern = "yyyy年MM月dd日 HH:mm";
                locale = Locale.CHINA;
                break;
            case (Constants.TIME_CN_HOUR):
                pattern = "aaK:mm";
                locale = Locale.CHINA;
                break;
            case (Constants.TIME_CN_MONTH):
                pattern = "M月d日";
                locale = Locale.CHINA;
                break;
            case (Constants.TIME_CN_YEAR):
                pattern = "yyyy年M月d日";
                locale = Locale.CHINA;
                break;
            default:
                pattern = "yyyy/MM/dd K:mm aa";
                locale = Locale.ENGLISH;
                break;
        }

        simpleDateFormat = new SimpleDateFormat(pattern, locale);

        return simpleDateFormat.format(date);

    }

    public static String getCurrentTimeStr(int format){
        return formatTime(format,new Date(System.currentTimeMillis()));
    }

    // 根据开始日期和结束日期计算时间差 天，小时，分钟，秒
    public static long[] getTimeDiff(Date startDate, Date endDate){
        long startMillis = startDate.getTime();
        long endMillis = endDate.getTime();
        long timeDiffMillis = endMillis - startMillis;
        long[] timeDiff = new long[4]; // 天，小时，分钟，秒

        timeDiff[0] = timeDiffMillis / (1000 * 60 * 60 * 24);
        timeDiff[1] = (timeDiffMillis % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        timeDiff[2] = (timeDiffMillis % (1000 * 60 * 60)) / (1000 * 60);
        timeDiff[3] = (timeDiffMillis % (1000 * 60)) / 1000;

        return timeDiff;
    }

    // 根据时间差计算结束日期，单位为毫秒
    public static Date getEndDate(Date startDate, long period){
        return new Date(startDate.getTime() + period);
    }

    // 将年 月 日 小时的格式转为毫秒
    public static long toMillis(int[] period){
        long days = (period[0] * 365) + (period[1] * 30) + period[2];
        long hours = (days * 24) + period[3];
        return hours * 60 * 60 * 1000;
    }

    // 将年 月 日 小时 分钟的格式转为日期
    public static Date toDate(int[] arr){
        String format = "yyyy-MM-dd-HH-mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String dateStr = arr[0] + "-" + arr[1] + "-" + arr[2] + "-" + arr[3] + "-" + arr[4];

        Date date = null;

        try {
            date = simpleDateFormat.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return date;
    }

    // 天 小时 分钟 秒 转为 年 月 日 小时
    public static int[] daysToYears(long[] arr){
        int[] array = new int[4];

        array[3] = (int) arr[1];
        array[2] = (int) arr[0];
        array[1] = 0;
        array[0] = 0;

        return standardizePeriod(array);
    }

    // 标准化年 月 日 小时的格式
    public static int[] standardizePeriod(int[] arr){

        // 小时转天
        if(arr[3] >= 24){
            arr[2] += (arr[3] / 24);
            arr[3] %= 24;
        }

        // 天转年
        if(arr[2] >= 365){
            arr[0] += (arr[2] / 365);
            arr[2] %= 365;
        }

        // 天转月
        if(arr[2] >= 30) {
            arr[1] += (arr[2] / 30);
            arr[2] %= 30;
        }

        // 月转年
        if(arr[1] >= 12){
            arr[0] += (arr[1] / 12);
            arr[1] %= 12;
        }

        return arr;
    }

    // 到现在经过的时间
    public static String getTimePast(Date addDate){
        Date currentDate = new Date(System.currentTimeMillis());
        long[] timeDiff = getTimeDiff(addDate,currentDate);

        if(timeDiff[0] == 0 && timeDiff[1] == 0 && timeDiff[2] == 0){
            return "刚刚";
        }

        if(timeDiff[0] == 0 && timeDiff[1] == 0){
            return timeDiff[2] + "分钟前";
        }

        if(timeDiff[0] == 0){
            return timeDiff[1] + "小时前";
        }else if(timeDiff[0] == 1){
            return "昨天";
        }else if(timeDiff[0] == 2){
            return "前天";
        }else if(timeDiff[0] < 30){
            return timeDiff[0] + "天前";
        }else if(timeDiff[0] > 30 && timeDiff[0] < 365){
            return (timeDiff[0] / 30) + "个月前";
        }else{
            return (timeDiff[0] / 365) + "年前";
        }

    }

    // 获取某年某月的最后一天
    public static int getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        // 设置年份
        cal.set(Calendar.YEAR, year);
        // 设置月份
        cal.set(Calendar.MONTH, month - 1);
        // 获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        return lastDay;
    }

}