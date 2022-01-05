package in.linus.busmate.Activity;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.content.FileProvider;
import in.linus.busmate.R;
import in.linus.busmate.Utility.Constants;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class AlertMeActivity extends Activity {
    public static final String TAG = "Alert";
    Context context;
    private Button mClientDownload;
    private TextView mTitleBar;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_alert_me);
        TextView textView = (TextView) findViewById(R.id.txt_title);
        this.mTitleBar = textView;
        textView.setText("Alert Me");
        Button button = (Button) findViewById(R.id.btn_dwnld);
        this.mClientDownload = button;
        this.context = this;
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(AlertMeActivity.this, "Downloading...", 0).show();
                new Thread(new Runnable() {
                    public void run() {
                        AlertMeActivity.this.downloadFile(Constants.client_app);
                        try {
                            File file = new File("client.apk");
                            Context context = AlertMeActivity.this.context;
                            FileProvider.getUriForFile(context, AlertMeActivity.this.context.getApplicationContext().getPackageName() + ".provider", file);
                            Uri uriForFile = FileProvider.getUriForFile(AlertMeActivity.this, "com.balysv.materialripple.provider", file);
                            Intent intent = new Intent("android.intent.action.VIEW");
                            intent.setDataAndType(uriForFile, "application/octet-stream");
                            intent.addFlags(1);
                            AlertMeActivity.this.startActivity(intent);
                        } catch (Exception e) {
                            Log.e(AlertMeActivity.TAG, e.toString());
                        }
                    }
                }).start();
            }
        });
    }

    public boolean storeClient(String str) {
        try {
            if (!isExternalStorageReadable()) {
                Log.e(TAG, "Storage not available");
                return false;
            } else if (isExternalStorageWritable()) {
                new File(this.context.getFilesDir(), "client.apk");
                return false;
            } else {
                Log.e(TAG, "Storage not writable");
                return false;
            }
        } catch (Exception e) {
            Log.e("AlertClient APK: ", e.getMessage());
            return false;
        }
    }

    private boolean isExternalStorageWritable() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    private boolean isExternalStorageReadable() {
        return Environment.getExternalStorageState().equals("mounted") || Environment.getExternalStorageState().equals("mounted_ro");
    }

    public boolean downloadFile(String str) {
        try {
            File file = new File(getApplicationContext().getPackageName() + ".provider/client.apk");
            if (!file.exists()) {
                file.mkdir();
            }
            Log.i(TAG, "Path: " + Environment.getExternalStorageDirectory());
            URL url = new URL(str);
            int contentLength = url.openConnection().getContentLength();
            DataInputStream dataInputStream = new DataInputStream(url.openStream());
            byte[] bArr = new byte[contentLength];
            dataInputStream.readFully(bArr);
            dataInputStream.close();
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(file));
            dataOutputStream.write(bArr);
            dataOutputStream.flush();
            dataOutputStream.close();
            return true;
        } catch (FileNotFoundException | IOException unused) {
            return false;
        }
    }

    public void downloadFile2(String str) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(str));
        request.setDescription("Downloading client.apk");
        request.setTitle("CLient.apk");
        request.setVisibleInDownloadsUi(false);
        if (Build.VERSION.SDK_INT >= 11) {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(2);
        }
        request.setDestinationInExternalFilesDir(getApplicationContext(), "/File/", "client.apk");
        DownloadManager downloadManager = (DownloadManager) getSystemService("download");
        Objects.requireNonNull(downloadManager);
        downloadManager.enqueue(request);
        Log.i(TAG, "Successfully downloaded the file.");
    }
}
