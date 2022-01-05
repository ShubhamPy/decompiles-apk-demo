package in.linus.busmate.Activity;

import android.app.Activity;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import in.linus.busmate.R;
import in.linus.busmate.Utility.Constants;
import in.linus.busmate.Utility.CustomProgressDialog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NearByActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final float DEFAULT_ZOOM = 50.0f;
    final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private String REQUESTING_LOCATION_UPDATES_KEY = "req_location";
    private ImageButton btnAdd;
    private ImageButton btnFav;
    private ImageButton btnFavPress;
    private ImageButton btnSearch;
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
    GoogleMap map;
    Toolbar tbottom;
    Toolbar toolbar;
    private TextView txt;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_near_by);
        this.toolbar = (Toolbar) findViewById(R.id.tool_barN);
        this.mDialog = new CustomProgressDialog(this);
        TextView textView = (TextView) findViewById(R.id.txt_title);
        this.txt = textView;
        textView.setText("NearBy");
        this.mFusedLocationClient = LocationServices.getFusedLocationProviderClient((Activity) this);
        if (ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0 || ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            this.mFusedLocationClient.getLastLocation().addOnSuccessListener((Activity) this, new OnSuccessListener<Location>() {
                public void onSuccess(Location location) {
                    if (location != null) {
                        Double unused = NearByActivity.this.mLat = Double.valueOf(location.getLatitude());
                        Double unused2 = NearByActivity.this.mLng = Double.valueOf(location.getLongitude());
                        NearByActivity nearByActivity = NearByActivity.this;
                        Toast.makeText(nearByActivity, "L1: " + location.getLatitude() + " :: " + location.getLongitude(), 0).show();
                        ((SupportMapFragment) NearByActivity.this.getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(NearByActivity.this);
                    }
                }
            });
            this.mLocationCallback = new LocationCallback() {
                public void onLocationResult(LocationResult locationResult) {
                    if (locationResult != null) {
                        for (Location latitude : locationResult.getLocations()) {
                            NearByActivity nearByActivity = NearByActivity.this;
                            Toast.makeText(nearByActivity, "L2 : " + latitude.getLatitude(), 0).show();
                        }
                    }
                }
            };
            updateValuesFromBundle(bundle);
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
        stopLocationUpdates();
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

    private ArrayList<String> permissionsToRequest(ArrayList<String> arrayList) {
        ArrayList<String> arrayList2 = new ArrayList<>();
        Iterator<String> it = arrayList.iterator();
        while (it.hasNext()) {
            String next = it.next();
            if (!hasPermission(next)) {
                arrayList2.add(next);
            }
        }
        return arrayList2;
    }

    private boolean hasPermission(String str) {
        if (Build.VERSION.SDK_INT < 23 || checkSelfPermission(str) == 0) {
            return true;
        }
        return false;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        Toast.makeText(this, "map fun called", 0).show();
        Double d = this.mLat;
        if (!(d == null || this.mLng == null)) {
            CameraUpdate newLatLng = CameraUpdateFactory.newLatLng(new LatLng(d.doubleValue(), this.mLng.doubleValue()));
            CameraUpdate zoomTo = CameraUpdateFactory.zoomTo(15.0f);
            this.map.moveCamera(newLatLng);
            this.map.animateCamera(zoomTo);
        }
        getBusStopDetails(googleMap);
        updateLocationUI();
    }

    private void updateValuesFromBundle(Bundle bundle) {
        if (bundle != null && bundle.keySet().contains(this.REQUESTING_LOCATION_UPDATES_KEY)) {
            this.mRequestingLocationUpdates = bundle.getBoolean(this.REQUESTING_LOCATION_UPDATES_KEY);
        }
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

    private void getBusStopDetails(final GoogleMap googleMap) {
        if (!this.mDialog.isShowing()) {
            this.mDialog.show();
        }
        Volley.newRequestQueue(this).add(new StringRequest(1, Constants.bus_stop_url, new Response.Listener<String>() {
            public void onResponse(String str) {
                Log.e("Response ::", BuildConfig.FLAVOR + str.toString());
                NearByActivity.this.mDialog.dismiss();
                try {
                    JSONArray jSONArray = new JSONArray(String.valueOf(str));
                    for (int i = 0; i < jSONArray.length(); i++) {
                        JSONObject jSONObject = jSONArray.getJSONObject(i);
                        googleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(jSONObject.getString("lat")), Double.parseDouble(jSONObject.getString("lng")))).title(jSONObject.getString("stop_name")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e2) {
                    Log.e("Login :: ", "Error " + e2.toString());
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                NearByActivity.this.mDialog.dismiss();
                if ((volleyError instanceof TimeoutError) || (volleyError instanceof NoConnectionError)) {
                    Toast.makeText(NearByActivity.this, "Failed to fetch data. Please check your network connection", 1).show();
                    Log.e("Near By Activity: ", "timeout");
                } else if (!(volleyError instanceof AuthFailureError)) {
                    if (volleyError instanceof ServerError) {
                        Toast.makeText(NearByActivity.this, "Server error", 0).show();
                        Log.e("Server Error : ", BuildConfig.FLAVOR + volleyError.toString());
                    } else if (volleyError instanceof NetworkError) {
                        Toast.makeText(NearByActivity.this, "Network error", 0).show();
                    } else if (volleyError instanceof ParseError) {
                        Toast.makeText(NearByActivity.this, "Parse error", 0).show();
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
                hashMap.put("cur_lat", "10.25645");
                hashMap.put("cur_lng", "17.32458");
                return hashMap;
            }
        });
    }
}
