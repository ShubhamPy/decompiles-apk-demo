package in.linus.busmate.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import in.linus.busmate.Utility.BackgroundServicesAPI;

public class PermissionHandler {
    public static final int PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    /* access modifiers changed from: private */
    public Context context;

    public PermissionHandler(Context context2) {
        this.context = context2;
    }

    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(this.context, "android.permission.READ_CONTACTS") == 0) {
            Log.i("Permission:", "Contact Fetching Started");
            new BackgroundServicesAPI(this.context).fetchContact();
        } else if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) this.context, "android.permission.READ_CONTACTS")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
            builder.setTitle("Read Contacts permission");
            builder.setPositiveButton(17039370, (DialogInterface.OnClickListener) null);
            builder.setMessage("Please enable access to contacts.");
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                public void onDismiss(DialogInterface dialogInterface) {
                    ActivityCompat.requestPermissions((Activity) PermissionHandler.this.context, new String[]{"android.permission.READ_CONTACTS"}, 1);
                }
            });
            builder.show();
        } else {
            ActivityCompat.requestPermissions((Activity) this.context, new String[]{"android.permission.READ_CONTACTS"}, 1);
        }
    }
}
