package in.linus.busmate.Activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import com.google.android.material.navigation.NavigationView;
import in.linus.busmate.Adapter.BusInformationAdapter;
import in.linus.busmate.Model.BusmateModelHelper;
import in.linus.busmate.R;
import in.linus.busmate.Utility.Constants;
import in.linus.busmate.Utility.CustomProgressDialog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BusInformationActivity extends AppCompatActivity {
    public static final String TAG = "Bus Information";
    private DrawerLayout drawerLayout;
    /* access modifiers changed from: private */
    public RecyclerView.Adapter mAdapter;
    /* access modifiers changed from: private */
    public TextView mBusName;
    CustomProgressDialog mDialog;
    /* access modifiers changed from: private */
    public TextView mFromTo;
    /* access modifiers changed from: private */
    public String mLat;
    /* access modifiers changed from: private */
    public RecyclerView.LayoutManager mLayoutManager;
    /* access modifiers changed from: private */
    public String mLng;
    List<BusmateModelHelper> mModel = new ArrayList();
    /* access modifiers changed from: private */
    public RecyclerView mRecyclerView;
    private TextView mTitleBar;
    private NavigationView navigationView;
    private Toolbar toolbar;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_bus_information);
        int intExtra = getIntent().getIntExtra("bus_id", 0);
        Log.i(TAG, " ID: " + intExtra);
        this.mDialog = new CustomProgressDialog(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.m_recycler_view);
        this.mRecyclerView = recyclerView;
        recyclerView.setHasFixedSize(true);
        this.mLayoutManager = new LinearLayoutManager(this);
        TextView textView = (TextView) findViewById(R.id.txt_title);
        this.mTitleBar = textView;
        textView.setText("Route Information");
        this.mBusName = (TextView) findViewById(R.id.m_bus_name);
        this.mFromTo = (TextView) findViewById(R.id.m_from_to);
        getBusDetails(intExtra);
    }

    private void getBusDetails(int i) {
        this.mModel.clear();
        if (!this.mDialog.isShowing()) {
            this.mDialog.show();
        }
        final int i2 = i;
        Volley.newRequestQueue(this).add(new StringRequest(1, Constants.bus_bus_info, new Response.Listener<String>() {
            public void onResponse(String str) {
                RecyclerView recyclerView;
                DividerItemDecoration dividerItemDecoration;
                Log.e("Response ::", BuildConfig.FLAVOR + str.toString());
                BusInformationActivity.this.mDialog.dismiss();
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    Log.i(BusInformationActivity.TAG, "RESPONSE:" + jSONObject.toString());
                    BusInformationActivity.this.mBusName.setText(jSONObject.getString("name"));
                    TextView access$100 = BusInformationActivity.this.mFromTo;
                    access$100.setText(jSONObject.getString("from") + " - " + jSONObject.getString("to"));
                    String unused = BusInformationActivity.this.mLat = jSONObject.getString("latitude");
                    String unused2 = BusInformationActivity.this.mLng = jSONObject.getString("longitude");
                    JSONArray jSONArray = jSONObject.getJSONArray("details");
                    BusInformationActivity.this.mModel.clear();
                    for (int i = 0; i < jSONArray.length(); i++) {
                        JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                        Log.i(BusInformationActivity.TAG, "OBJECT:" + jSONObject2.toString());
                        BusmateModelHelper busmateModelHelper = new BusmateModelHelper();
                        busmateModelHelper.setBus_stop_name(jSONObject2.getString("place"));
                        busmateModelHelper.setArriveTime(jSONObject2.getString("reach_time"));
                        busmateModelHelper.setDepartTime(jSONObject2.getString("depart_time"));
                        busmateModelHelper.setBus_stop_latitude(jSONObject2.getString("latitude"));
                        busmateModelHelper.setBus_stop_longitude(jSONObject2.getString("longitude"));
                        BusInformationActivity.this.mModel.add(busmateModelHelper);
                    }
                    BusInformationActivity.this.mRecyclerView.setLayoutManager(BusInformationActivity.this.mLayoutManager);
                    BusInformationActivity.this.mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    BusInformationActivity busInformationActivity = BusInformationActivity.this;
                    List<BusmateModelHelper> list = busInformationActivity.mModel;
                    BusInformationActivity busInformationActivity2 = BusInformationActivity.this;
                    RecyclerView.Adapter unused3 = busInformationActivity.mAdapter = new BusInformationAdapter(list, busInformationActivity2, busInformationActivity2.mLat, BusInformationActivity.this.mLng);
                    recyclerView = BusInformationActivity.this.mRecyclerView;
                    dividerItemDecoration = new DividerItemDecoration(BusInformationActivity.this, 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                    BusInformationActivity.this.mRecyclerView.setLayoutManager(BusInformationActivity.this.mLayoutManager);
                    BusInformationActivity.this.mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    BusInformationActivity busInformationActivity3 = BusInformationActivity.this;
                    List<BusmateModelHelper> list2 = busInformationActivity3.mModel;
                    BusInformationActivity busInformationActivity4 = BusInformationActivity.this;
                    RecyclerView.Adapter unused4 = busInformationActivity3.mAdapter = new BusInformationAdapter(list2, busInformationActivity4, busInformationActivity4.mLat, BusInformationActivity.this.mLng);
                    recyclerView = BusInformationActivity.this.mRecyclerView;
                    dividerItemDecoration = new DividerItemDecoration(BusInformationActivity.this, 1);
                } catch (Exception e2) {
                    Log.e(BusInformationActivity.TAG, "Error " + e2.toString());
                    BusInformationActivity.this.mRecyclerView.setLayoutManager(BusInformationActivity.this.mLayoutManager);
                    BusInformationActivity.this.mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    BusInformationActivity busInformationActivity5 = BusInformationActivity.this;
                    List<BusmateModelHelper> list3 = busInformationActivity5.mModel;
                    BusInformationActivity busInformationActivity6 = BusInformationActivity.this;
                    RecyclerView.Adapter unused5 = busInformationActivity5.mAdapter = new BusInformationAdapter(list3, busInformationActivity6, busInformationActivity6.mLat, BusInformationActivity.this.mLng);
                    recyclerView = BusInformationActivity.this.mRecyclerView;
                    dividerItemDecoration = new DividerItemDecoration(BusInformationActivity.this, 1);
                } catch (Throwable th) {
                    BusInformationActivity.this.mRecyclerView.setLayoutManager(BusInformationActivity.this.mLayoutManager);
                    BusInformationActivity.this.mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    BusInformationActivity busInformationActivity7 = BusInformationActivity.this;
                    List<BusmateModelHelper> list4 = busInformationActivity7.mModel;
                    BusInformationActivity busInformationActivity8 = BusInformationActivity.this;
                    RecyclerView.Adapter unused6 = busInformationActivity7.mAdapter = new BusInformationAdapter(list4, busInformationActivity8, busInformationActivity8.mLat, BusInformationActivity.this.mLng);
                    BusInformationActivity.this.mRecyclerView.addItemDecoration(new DividerItemDecoration(BusInformationActivity.this, 1));
                    BusInformationActivity.this.mRecyclerView.setAdapter(BusInformationActivity.this.mAdapter);
                    throw th;
                }
                recyclerView.addItemDecoration(dividerItemDecoration);
                BusInformationActivity.this.mRecyclerView.setAdapter(BusInformationActivity.this.mAdapter);
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                BusInformationActivity.this.mDialog.dismiss();
                if ((volleyError instanceof TimeoutError) || (volleyError instanceof NoConnectionError)) {
                    Toast.makeText(BusInformationActivity.this, "Failed to fetch data. Please check your network connection", 1).show();
                    Log.e(BusInformationActivity.TAG, "timeout");
                } else if (volleyError instanceof AuthFailureError) {
                    Log.e(BusInformationActivity.TAG, "AuthFailure");
                } else if (volleyError instanceof ServerError) {
                    Toast.makeText(BusInformationActivity.this, "Server error", 0).show();
                    Log.e(BusInformationActivity.TAG, "Server Error: " + volleyError.toString());
                } else if (volleyError instanceof NetworkError) {
                    Log.e(BusInformationActivity.TAG, "Network Error: " + volleyError.toString());
                    Toast.makeText(BusInformationActivity.this, "Network error", 0).show();
                } else if (volleyError instanceof ParseError) {
                    Log.e(BusInformationActivity.TAG, "Parse Error: " + volleyError.toString());
                    Toast.makeText(BusInformationActivity.this, "Parse error", 0).show();
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
                hashMap.put("bus_id", String.valueOf(i2));
                return hashMap;
            }
        });
    }
}
