package Utils;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Dream on 2016/5/6.
 */
public class JavaUtils {

    public static String[] reverse(String[] arr)
    {
        String[] newArr = new String[arr.length];
        for(int i=0;i<arr.length;i++)
        {
            newArr[i] = arr[arr.length-i-1];
        }
        return newArr;
    }

    public static String getStringFromFlag(String string) {
        String str = "";
        Pattern p = Pattern.compile("#(.*)#");
        Matcher m = p.matcher(string);
        while(m.find()){
            str = m.group(1);
        }

        return str;
    }
    public static String generateTime(int totalSeconds) {

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        if (hours > 0) {
            return String.format(Locale.US, "%02d:%02d:%02d", hours, minutes,
                    seconds);
        } else {
            return String.format(Locale.US, "%02d:%02d", minutes, seconds);
        }
    }
}
