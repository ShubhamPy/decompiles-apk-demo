package in.linus.busmate.Activity;

import android.app.Activity;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.balysv.materialripple.BuildConfig;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.tasks.OnSuccessListener;
import in.linus.busmate.R;
import in.linus.busmate.Utility.Constants;
import in.linus.busmate.Utility.CustomProgressDialog;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONException;
import org.json.JSONObject;

public class DriverHome extends AppCompatActivity implements OnMapReadyCallback {
    final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private String REQUESTING_LOCATION_UPDATES_KEY = "req_location";
    final Handler handler = new Handler();
    CustomProgressDialog mDialog;
    private FusedLocationProviderClient mFusedLocationClient;
    /* access modifiers changed from: private */
    public Double mLat = null;
    /* access modifiers changed from: private */
    public Double mLng = null;
    private LocationCallback mLocationCallback;
    boolean mLocationPermissionGranted = true;
    LocationRequest mLocationRequest;
    private boolean mRequestingLocationUpdates = true;
    TimerTask mTimerTask;
    GoogleMap map;
    private Button startTracking;
    private Button stopTracking;
    Timer t = new Timer();
    String user_id = null;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_driver_home);
        this.startTracking = (Button) findViewById(R.id.btn_start_tracking);
        this.stopTracking = (Button) findViewById(R.id.btn_stop_tracking);
        String stringExtra = getIntent().getStringExtra("user_id");
        this.user_id = stringExtra;
        Log.d("USER ID(passed):  ", stringExtra);
        this.mDialog = new CustomProgressDialog(this);
        this.startTracking.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                DriverHome.this.lambda$onCreate$0$DriverHome(view);
            }
        });
        this.stopTracking.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                DriverHome.this.lambda$onCreate$1$DriverHome(view);
            }
        });
        this.mFusedLocationClient = LocationServices.getFusedLocationProviderClient((Activity) this);
        if (ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0 || ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            this.mFusedLocationClient.getLastLocation().addOnSuccessListener((Activity) this, new OnSuccessListener() {
                public final void onSuccess(Object obj) {
                    DriverHome.this.lambda$onCreate$2$DriverHome((Location) obj);
                }
            });
            this.mLocationCallback = new LocationCallback() {
                public void onLocationResult(LocationResult locationResult) {
                    if (locationResult != null) {
                        for (Location next : locationResult.getLocations()) {
                            Double unused = DriverHome.this.mLat = Double.valueOf(next.getLatitude());
                            Double unused2 = DriverHome.this.mLng = Double.valueOf(next.getLongitude());
                            DriverHome driverHome = DriverHome.this;
                            Toast.makeText(driverHome, "L2 : " + next.getLatitude(), 0).show();
                        }
                    }
                }
            };
        }
    }

    public /* synthetic */ void lambda$onCreate$0$DriverHome(View view) {
        Toast.makeText(view.getContext(), "Tracking started", 0);
        AnonymousClass1 r3 = new TimerTask() {
            public void run() {
                DriverHome.this.handler.post(new Runnable() {
                    public void run() {
                        Log.d("TIMER", "TimerTask run");
                        Double unused = DriverHome.this.mLat = Double.valueOf((((double) Math.round(new Random().nextDouble() * 1000.0d)) / 1000.0d) + 28.0d);
                        Random random = new Random();
                        Double unused2 = DriverHome.this.mLng = Double.valueOf(random.nextDouble() + 76.0d);
                        Double unused3 = DriverHome.this.mLng = Double.valueOf((((double) Math.round(random.nextDouble() * 1000.0d)) / 1000.0d) + 76.0d);
                        DriverHome.this.sendLocationDetails(DriverHome.this.mLat, DriverHome.this.mLng, DriverHome.this.user_id);
                    }
                });
            }
        };
        this.mTimerTask = r3;
        this.t.schedule(r3, 500, 3000);
    }

    public /* synthetic */ void lambda$onCreate$1$DriverHome(View view) {
        if (this.mTimerTask != null) {
            Log.d("TIMER", "timer canceled");
            this.mTimerTask.cancel();
            this.t.cancel();
            Toast.makeText(view.getContext(), "Tracking stopped ", 0).show();
        }
    }

    public /* synthetic */ void lambda$onCreate$2$DriverHome(Location location) {
        if (location != null) {
            this.mLat = Double.valueOf(location.getLatitude());
            this.mLng = Double.valueOf(location.getLongitude());
            Toast.makeText(this, "L1: " + location.getLatitude() + " :: " + location.getLongitude(), 0).show();
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        if (this.mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
    }

    /* access modifiers changed from: protected */
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putBoolean(this.REQUESTING_LOCATION_UPDATES_KEY, this.mRequestingLocationUpdates);
        super.onSaveInstanceState(bundle);
    }

    private void stopLocationUpdates() {
        this.mFusedLocationClient.removeLocationUpdates(this.mLocationCallback);
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0 || ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            this.mFusedLocationClient.requestLocationUpdates(this.mLocationRequest, this.mLocationCallback, (Looper) null);
        }
    }

    private boolean hasPermission(String str) {
        if (Build.VERSION.SDK_INT < 23 || checkSelfPermission(str) == 0) {
            return true;
        }
        return false;
    }

    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        Toast.makeText(this, "map fun called", 0).show();
    }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.ACCESS_FINE_LOCATION") == 0) {
            this.mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 1);
        }
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        this.mLocationPermissionGranted = false;
        if (i == 1 && iArr.length > 0 && iArr[0] == 0) {
            this.mLocationPermissionGranted = true;
        }
        updateLocationUI();
    }

    private void updateLocationUI() {
        GoogleMap googleMap = this.map;
        if (googleMap != null) {
            try {
                if (this.mLocationPermissionGranted) {
                    googleMap.setMyLocationEnabled(true);
                    this.map.getUiSettings().setMyLocationButtonEnabled(true);
                    return;
                }
                googleMap.setMyLocationEnabled(false);
                this.map.getUiSettings().setMyLocationButtonEnabled(false);
                getLocationPermission();
            } catch (SecurityException e) {
                Log.e("Exception: %s", e.getMessage());
            }
        }
    }

    public void onStart() {
        super.onStart();
    }

    /* access modifiers changed from: private */
    public void sendLocationDetails(Double d, Double d2, String str) {
        if (!this.mDialog.isShowing()) {
            this.mDialog.show();
        }
        Log.d("USER ID(b4 sent):  ", str);
        final Double d3 = d;
        final Double d4 = d2;
        final String str2 = str;
        Volley.newRequestQueue(this).add(new StringRequest(1, Constants.bus_tracker, new Response.Listener<String>() {
            public void onResponse(String str) {
                DriverHome.this.mDialog.dismiss();
                try {
                    new JSONObject(str);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e2) {
                    Log.e("Login :: ", "Error " + e2.toString());
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                DriverHome.this.mDialog.dismiss();
                if ((volleyError instanceof TimeoutError) || (volleyError instanceof NoConnectionError)) {
                    Toast.makeText(DriverHome.this, "Failed to fetch data. Please check your network connection", 1).show();
                    Log.e("Near By Activity: ", "timeout");
                } else if (!(volleyError instanceof AuthFailureError)) {
                    if (volleyError instanceof ServerError) {
                        Toast.makeText(DriverHome.this, "Server error", 0).show();
                        Log.e("Server Error : ", BuildConfig.FLAVOR + volleyError.toString());
                    } else if (volleyError instanceof NetworkError) {
                        Toast.makeText(DriverHome.this, "Network error", 0).show();
                    } else if (volleyError instanceof ParseError) {
                        Toast.makeText(DriverHome.this, "Parse error", 0).show();
                    }
                }
            }
        }) {
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            /* access modifiers changed from: protected */
            public Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                HashMap hashMap = new HashMap();
                hashMap.put("lat", String.valueOf(d3));
                hashMap.put("lng", String.valueOf(d4));
                hashMap.put("user_id", str2);
                return hashMap;
            }
        });
    }
}
