package in.linus.busmate.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.balysv.materialripple.BuildConfig;
import in.linus.busmate.R;
import in.linus.busmate.Utility.CheckNetwork;
import in.linus.busmate.Utility.Constants;
import in.linus.busmate.Utility.CustomProgressDialog;
import in.linus.busmate.user;
import in.linus.busmate.user_sqlhelper;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class Login_Activity extends AppCompatActivity {
    public static final int PERMISSIONS_REQUEST_READ_EXTERNAL_STOARGE = 2;
    public static final int PERMISSIONS_REQUEST_WRITE_EXTERNAL_STOARGE = 3;
    public static final String TAG = "Login";
    private Button btnlogin;
    private Button buttonSignup;
    String cust_id;
    /* access modifiers changed from: private */
    public EditText editPass;
    String editPassValue;
    /* access modifiers changed from: private */
    public EditText editUser;
    String editUserValue;
    /* access modifiers changed from: private */
    public Boolean exit = false;
    InputStream is = null;
    String line = null;
    CustomProgressDialog mDialog;
    SharedPreferences mSharedPreferences;
    String mob;
    String name;
    ProgressDialog progressDialog;
    String pwd;
    String result = null;
    int status = 20;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_login);
        this.buttonSignup = (Button) findViewById(R.id.btn_signup);
        this.btnlogin = (Button) findViewById(R.id.btn_login);
        this.mDialog = new CustomProgressDialog(this);
        this.editUser = (EditText) findViewById(R.id.edt_Loguser);
        this.editPass = (EditText) findViewById(R.id.edt_Logpass);
        Constants.ip = Constants.readIP();
        checkPermission();
        this.buttonSignup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Login_Activity.this.startActivity(new Intent(Login_Activity.this, Registeration.class));
            }
        });
        this.btnlogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (Login_Activity.this.editUser.getText().toString().trim().isEmpty()) {
                    Login_Activity.this.editUser.setError("Email-ID cannot be left blank.");
                } else if (Login_Activity.this.editPass.getText().toString().trim().isEmpty()) {
                    Login_Activity.this.editUser.setError("Password cannot be empty!");
                    Login_Activity.this.editPass.setError(BuildConfig.FLAVOR);
                } else {
                    Login_Activity login_Activity = Login_Activity.this;
                    login_Activity.editUserValue = login_Activity.editUser.getText().toString();
                    Login_Activity login_Activity2 = Login_Activity.this;
                    login_Activity2.editPassValue = login_Activity2.editPass.getText().toString();
                    Login_Activity.this.editUser.setError((CharSequence) null);
                    Login_Activity.this.editPass.setError((CharSequence) null);
                    if (CheckNetwork.isInternetAvailable(Login_Activity.this)) {
                        Login_Activity.this.hitLoginApi();
                    } else {
                        Toast.makeText(Login_Activity.this, "No Internet Connection", 0).show();
                    }
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void hitLoginApi() {
        if (!this.mDialog.isShowing()) {
            this.mDialog.show();
        }
        RequestQueue newRequestQueue = Volley.newRequestQueue(this);
        Log.e("URL:", Constants.login_url);
        newRequestQueue.add(new StringRequest(1, Constants.login_url, new Response.Listener() {
            public final void onResponse(Object obj) {
                Login_Activity.this.lambda$hitLoginApi$0$Login_Activity((String) obj);
            }
        }, new Response.ErrorListener() {
            public final void onErrorResponse(VolleyError volleyError) {
                Login_Activity.this.lambda$hitLoginApi$1$Login_Activity(volleyError);
            }
        }) {
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            /* access modifiers changed from: protected */
            public Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                HashMap hashMap = new HashMap();
                hashMap.put("EmailID", Login_Activity.this.editUserValue);
                hashMap.put("Password", Login_Activity.this.editPassValue);
                return hashMap;
            }
        });
    }

    public /* synthetic */ void lambda$hitLoginApi$0$Login_Activity(String str) {
        this.mDialog.dismiss();
        try {
            JSONObject jSONObject = new JSONObject(String.valueOf(str));
            if (jSONObject.getString(NotificationCompat.CATEGORY_STATUS).equals("success")) {
                user user = new user();
                user.setUsername(jSONObject.getString("username"));
                user.setPassword(this.editPassValue);
                user.setEmail_id(jSONObject.getString("email"));
                new user_sqlhelper(this).addUser(user);
                SharedPreferences.Editor edit = getApplicationContext().getSharedPreferences(getString(R.string.preferences_file_key), 0).edit();
                edit.putString(getString(R.string.mail_id), this.editUserValue);
                edit.putString(getString(R.string.passwd), this.editPassValue);
                edit.putString("user_id", jSONObject.getString("user_id"));
                edit.putString("flag", "csa{sHGTDB6ucpu8GRFTHAdcaStwd7GorD14}");
                edit.commit();
                Toast.makeText(this, jSONObject.getString("message"), 0).show();
                if (jSONObject.getString("type").equals("D")) {
                    Intent intent = new Intent(this, DriverHome.class);
                    intent.putExtra("user_id", jSONObject.getString("user_id"));
                    startActivity(intent);
                    finish();
                    return;
                }
                startActivity(new Intent(this, MainActivity.class));
                finish();
                return;
            }
            Toast.makeText(this, jSONObject.getString("message"), 0).show();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            Log.e("Login ", "Error: " + e2.toString());
        }
    }

    public /* synthetic */ void lambda$hitLoginApi$1$Login_Activity(VolleyError volleyError) {
        this.mDialog.dismiss();
        if ((volleyError instanceof TimeoutError) || (volleyError instanceof NoConnectionError)) {
            Toast.makeText(this, "Failed to fetch data. Please check your network connection", 1).show();
            Log.e("Login: ", "Timeout Occured");
        } else if (volleyError instanceof AuthFailureError) {
            Log.e("Login: ", "AuthFailure");
        } else if (volleyError instanceof ServerError) {
            Toast.makeText(this, "Server error", 0).show();
            Log.e("Server Error : ", BuildConfig.FLAVOR + volleyError.toString());
        } else if (volleyError instanceof NetworkError) {
            Log.e("Login: ", "Network Error");
            Toast.makeText(this, "Network error", 0).show();
        } else if (volleyError instanceof ParseError) {
            Log.e("Login: ", "Parse Error");
            Toast.makeText(this, "Parse error", 0).show();
        }
    }

    public void onBackPressed() {
        if (this.exit.booleanValue()) {
            finish();
            return;
        }
        Toast.makeText(this, "Press again to exit", 0).show();
        this.exit = true;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Boolean unused = Login_Activity.this.exit = false;
            }
        }, 3000);
    }

    public void checkPermission() {
        if (checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") == 0) {
            return;
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.READ_EXTERNAL_STORAGE")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Read Storage");
            builder.setPositiveButton(17039370, (DialogInterface.OnClickListener) null);
            builder.setMessage("Please enable storage access for smooth functioning of the app.");
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                public void onDismiss(DialogInterface dialogInterface) {
                    Login_Activity.this.requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 2);
                }
            });
            builder.show();
            return;
        }
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 2);
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 2 && iArr.length > 0 && iArr[0] == 0) {
            Log.i(TAG, "Permissison Granted");
        }
    }
}
