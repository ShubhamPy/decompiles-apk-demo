package in.linus.busmate.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.balysv.materialripple.BuildConfig;
import in.linus.busmate.R;
import in.linus.busmate.Utility.CheckNetwork;
import in.linus.busmate.Utility.Constants;
import in.linus.busmate.Utility.CustomProgressDialog;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class Registeration extends AppCompatActivity {
    private Button ButtonSave;
    /* access modifiers changed from: private */
    public EditText edtConfirmPassword;
    /* access modifiers changed from: private */
    public EditText edtEmail;
    /* access modifiers changed from: private */
    public EditText edtMobileNo;
    /* access modifiers changed from: private */
    public EditText edtName;
    /* access modifiers changed from: private */
    public EditText edtPassword;
    private ImageButton imgBackToLogin;
    CustomProgressDialog mDialog;
    SharedPreferences mSharedPreferences;
    String sConfirmPassword;
    String sEmail;
    String sMobileNo;
    String sName;
    String sPassword;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_registeration);
        this.imgBackToLogin = (ImageButton) findViewById(R.id.imgPre);
        this.mDialog = new CustomProgressDialog(this);
        this.edtName = (EditText) findViewById(R.id.edt_userName);
        this.edtPassword = (EditText) findViewById(R.id.edt_pass);
        this.edtConfirmPassword = (EditText) findViewById(R.id.edt_confirm_pass);
        this.edtEmail = (EditText) findViewById(R.id.edt_emailID);
        this.edtMobileNo = (EditText) findViewById(R.id.edt_mobile);
        Button button = (Button) findViewById(R.id.btn_save);
        this.ButtonSave = button;
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Registeration registeration = Registeration.this;
                registeration.sName = registeration.edtName.getText().toString();
                Registeration registeration2 = Registeration.this;
                registeration2.sPassword = registeration2.edtPassword.getText().toString();
                Registeration registeration3 = Registeration.this;
                registeration3.sConfirmPassword = registeration3.edtConfirmPassword.getText().toString();
                Registeration registeration4 = Registeration.this;
                registeration4.sEmail = registeration4.edtEmail.getText().toString();
                Registeration registeration5 = Registeration.this;
                registeration5.sMobileNo = registeration5.edtMobileNo.getText().toString();
                if (Registeration.this.sName.length() == 0) {
                    Toast.makeText(Registeration.this, "Name field cannot be left blank.", 0).show();
                } else if (Registeration.this.sPassword.length() == 0) {
                    Toast.makeText(Registeration.this, "Password field cannot be left blank.", 0).show();
                } else if (Registeration.this.sConfirmPassword.length() == 0) {
                    Toast.makeText(Registeration.this, "Confirm Password field cannot be left blank.", 0).show();
                } else if (Registeration.this.sEmail.length() == 0) {
                    Toast.makeText(Registeration.this, "Email ID  field cannot be left blank.", 0).show();
                } else if (Registeration.this.sMobileNo.length() == 0) {
                    Toast.makeText(Registeration.this, "Mobile Number field cannot be left blank.", 0).show();
                } else if (!Registeration.this.sPassword.equals(Registeration.this.sConfirmPassword)) {
                    Toast.makeText(Registeration.this, "Passwords do not match.", 0).show();
                } else if (Registeration.this.sMobileNo.length() < 10) {
                    Toast.makeText(Registeration.this, "Provide a valid mobile number.", 0).show();
                } else if (!Registeration.this.sEmail.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
                    Toast.makeText(Registeration.this, "Provide a valid email address.", 0).show();
                } else if (CheckNetwork.isInternetAvailable(Registeration.this)) {
                    Registeration.this.registerUser();
                } else {
                    Toast.makeText(Registeration.this, "Failed to connect to network!", 0).show();
                }
            }
        });
        this.imgBackToLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Registeration.this.onBackPressed();
                Registeration.this.overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });
    }

    /* access modifiers changed from: private */
    public void registerUser() {
        if (!this.mDialog.isShowing()) {
            this.mDialog.show();
        }
        RequestQueue newRequestQueue = Volley.newRequestQueue(this);
        Log.i("====>Post Registration", Constants.bus_mate_registration);
        AnonymousClass3 r3 = new StringRequest(1, Constants.bus_mate_registration, new Response.Listener() {
            public final void onResponse(Object obj) {
                Registeration.this.lambda$registerUser$0$Registeration((String) obj);
            }
        }, new Response.ErrorListener() {
            public final void onErrorResponse(VolleyError volleyError) {
                Registeration.this.lambda$registerUser$1$Registeration(volleyError);
            }
        }) {
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            /* access modifiers changed from: protected */
            public Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                HashMap hashMap = new HashMap();
                hashMap.put("name", Registeration.this.sName);
                hashMap.put("email", Registeration.this.sEmail);
                hashMap.put("password", Registeration.this.sPassword);
                hashMap.put("mobile_number", Registeration.this.sMobileNo);
                return hashMap;
            }

            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap hashMap = new HashMap();
                hashMap.put(HttpHeaderParser.HEADER_CONTENT_TYPE, "application/x-www-form-urlencoded");
                return hashMap;
            }
        };
        r3.setRetryPolicy(new DefaultRetryPolicy(20000, 2, 1.0f));
        newRequestQueue.add(r3);
    }

    public /* synthetic */ void lambda$registerUser$0$Registeration(String str) {
        Log.e("Response ::", BuildConfig.FLAVOR + str.toString());
        this.mDialog.dismiss();
        try {
            JSONObject jSONObject = new JSONObject(String.valueOf(str));
            String string = jSONObject.getString(NotificationCompat.CATEGORY_STATUS);
            Log.e("Status :: ", BuildConfig.FLAVOR + string);
            if (string.equals("success")) {
                Toast.makeText(getApplicationContext(), jSONObject.getString("message"), 0).show();
                startActivity(new Intent(this, Login_Activity.class));
                finish();
                return;
            }
            Toast.makeText(getApplicationContext(), jSONObject.getString("message"), 0).show();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            Log.e("Login :: ", "Error " + e2.toString());
        }
    }

    public /* synthetic */ void lambda$registerUser$1$Registeration(VolleyError volleyError) {
        this.mDialog.dismiss();
        if (volleyError instanceof TimeoutError) {
            Toast.makeText(this, "Timeout", 1).show();
            Log.e("RegistrationActivity: ", "timeout");
        } else if (volleyError instanceof NoConnectionError) {
            Toast.makeText(this, "Failed to fetch data. Please check your network connection", 1).show();
        } else if (!(volleyError instanceof AuthFailureError)) {
            if (volleyError instanceof ServerError) {
                Toast.makeText(this, "Server error", 0).show();
                Log.e("Server Error : ", BuildConfig.FLAVOR + volleyError.toString());
            } else if (volleyError instanceof NetworkError) {
                Toast.makeText(this, "Network error", 0).show();
            } else if (volleyError instanceof ParseError) {
                Toast.makeText(this, "Parse error", 0).show();
            }
        }
    }
}
