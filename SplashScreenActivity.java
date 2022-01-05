package in.linus.busmate.Activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import in.linus.busmate.R;

public class SplashScreenActivity extends Activity {
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "SpplashScreen ";
    private boolean isReceiverRegistered;
    private TextView mInformationTextView;
    SharedPreferences mPreferences;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private ProgressBar mRegistrationProgressBar;
    SharedPreferences sharedPreferences;

    private void registerReceiver() {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_splash_screen);
        registerReceiver();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                SplashScreenActivity.this.startActivity(new Intent(SplashScreenActivity.this, Login_Activity.class));
                SplashScreenActivity.this.finish();
            }
        }, 2000);
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        registerReceiver();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mRegistrationBroadcastReceiver);
        this.isReceiverRegistered = false;
        super.onPause();
    }
}
