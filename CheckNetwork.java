package in.linus.busmate.Utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class CheckNetwork {
    private static final String TAG = "CheckNetwork";

    public static boolean isInternetAvailable(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            Log.d(TAG, "no internet connection");
            return false;
        } else if (activeNetworkInfo.isConnected()) {
            Log.d(TAG, " internet connection available...");
            return true;
        } else {
            Log.d(TAG, " internet connection");
            return true;
        }
    }
}
