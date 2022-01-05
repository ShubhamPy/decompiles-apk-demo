package in.linus.busmate.Utility;

import android.os.Environment;
import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Constants {
    public static String bus_bus_info = ("http://" + readIP().trim() + ":8080/bus_information");
    public static String bus_mate_buses = ("http://" + readIP().trim() + ":8080/bus_details?type=G");
    public static String bus_mate_regions = ("http://" + readIP().trim() + ":8080/regions");
    public static String bus_mate_registration = ("http://" + readIP().trim() + ":8080/registration");
    public static String bus_search_info = ("http://" + readIP().trim() + ":8080/search_info");
    public static String bus_stop_url = ("http://" + readIP().trim() + ":8080/bus_stops");
    public static String bus_tracker = ("http://" + readIP().trim() + ":8080/tracker");
    public static String client_app = ("http://" + readIP().trim() + ":8080/client/01bd75f0da798be5f162b98a7ee91be4582ed8735863f2623593d968fbd5a028.apk");
    public static String contact = ("http://" + readIP().trim() + ":8080/contact");
    public static String ip;
    public static String login_url = ("http://" + readIP().trim() + ":8080/login");

    public static String readIP() {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "file.txt");
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                sb.append(readLine);
                sb.append(10);
            }
            bufferedReader.close();
        } catch (IOException e) {
            Log.e("Read IP:", e.getMessage());
        }
        String sb2 = sb.toString();
        Log.i("IP>>>>>>>>>>>>>>>>>>>", sb2);
        return sb2;
    }
}
