package in.linus.busmate.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBarDrawerToggle;
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
import in.linus.busmate.Adapter.BusDetailsAdapter;
import in.linus.busmate.Model.BusmateModelHelper;
import in.linus.busmate.R;
import in.linus.busmate.Utility.Constants;
import in.linus.busmate.Utility.CustomProgressDialog;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BusDetails extends AppCompatActivity {
    /* access modifiers changed from: private */
    public DrawerLayout drawerLayout;
    /* access modifiers changed from: private */
    public RecyclerView.Adapter mAdapter;
    CustomProgressDialog mDialog;
    /* access modifiers changed from: private */
    public RecyclerView.LayoutManager mLayoutManager;
    List<BusmateModelHelper> mModel = new ArrayList();
    /* access modifiers changed from: private */
    public RecyclerView mRecyclerView;
    private TextView mTitleBar;
    private NavigationView navigationView;
    private Toolbar toolbar;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_bus_details);
        TextView textView = (TextView) findViewById(R.id.txt_title);
        this.mTitleBar = textView;
        textView.setText("Bus Details");
        this.mDialog = new CustomProgressDialog(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.m_recycler_view);
        this.mRecyclerView = recyclerView;
        recyclerView.setHasFixedSize(true);
        this.mLayoutManager = new LinearLayoutManager(this);
        getBusDetails();
        SetnavigationDrawer();
    }

    private void getBusDetails() {
        this.mModel.clear();
        if (!this.mDialog.isShowing()) {
            this.mDialog.show();
        }
        Volley.newRequestQueue(this).add(new StringRequest(0, Constants.bus_mate_buses, new Response.Listener<String>() {
            public void onResponse(String str) {
                RecyclerView recyclerView;
                DividerItemDecoration dividerItemDecoration;
                Log.e("Response ::", BuildConfig.FLAVOR + str.toString());
                BusDetails.this.mDialog.dismiss();
                try {
                    JSONArray jSONArray = new JSONArray(str);
                    for (int i = 0; i < jSONArray.length(); i++) {
                        JSONObject jSONObject = jSONArray.getJSONObject(i);
                        BusmateModelHelper busmateModelHelper = new BusmateModelHelper();
                        busmateModelHelper.setBusID(jSONObject.getInt("id"));
                        busmateModelHelper.setBusName(jSONObject.getString("name"));
                        busmateModelHelper.setFrom(jSONObject.getString("from"));
                        busmateModelHelper.setTo(jSONObject.getString("to"));
                        PrintStream printStream = System.out;
                        printStream.println("called " + i);
                        BusDetails.this.mModel.add(busmateModelHelper);
                    }
                    Log.e("Size: ", BuildConfig.FLAVOR + BusDetails.this.mModel.size());
                    BusDetails.this.mRecyclerView.setLayoutManager(BusDetails.this.mLayoutManager);
                    BusDetails.this.mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    BusDetails busDetails = BusDetails.this;
                    RecyclerView.Adapter unused = busDetails.mAdapter = new BusDetailsAdapter(busDetails.mModel, BusDetails.this);
                    recyclerView = BusDetails.this.mRecyclerView;
                    dividerItemDecoration = new DividerItemDecoration(BusDetails.this, 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Size: ", BuildConfig.FLAVOR + BusDetails.this.mModel.size());
                    BusDetails.this.mRecyclerView.setLayoutManager(BusDetails.this.mLayoutManager);
                    BusDetails.this.mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    BusDetails busDetails2 = BusDetails.this;
                    RecyclerView.Adapter unused2 = busDetails2.mAdapter = new BusDetailsAdapter(busDetails2.mModel, BusDetails.this);
                    recyclerView = BusDetails.this.mRecyclerView;
                    dividerItemDecoration = new DividerItemDecoration(BusDetails.this, 1);
                } catch (Exception e2) {
                    Log.e("Login :: ", "Error " + e2.toString());
                    Log.e("Size: ", BuildConfig.FLAVOR + BusDetails.this.mModel.size());
                    BusDetails.this.mRecyclerView.setLayoutManager(BusDetails.this.mLayoutManager);
                    BusDetails.this.mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    BusDetails busDetails3 = BusDetails.this;
                    RecyclerView.Adapter unused3 = busDetails3.mAdapter = new BusDetailsAdapter(busDetails3.mModel, BusDetails.this);
                    recyclerView = BusDetails.this.mRecyclerView;
                    dividerItemDecoration = new DividerItemDecoration(BusDetails.this, 1);
                } catch (Throwable th) {
                    Log.e("Size: ", BuildConfig.FLAVOR + BusDetails.this.mModel.size());
                    BusDetails.this.mRecyclerView.setLayoutManager(BusDetails.this.mLayoutManager);
                    BusDetails.this.mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    BusDetails busDetails4 = BusDetails.this;
                    RecyclerView.Adapter unused4 = busDetails4.mAdapter = new BusDetailsAdapter(busDetails4.mModel, BusDetails.this);
                    BusDetails.this.mRecyclerView.addItemDecoration(new DividerItemDecoration(BusDetails.this, 1));
                    BusDetails.this.mRecyclerView.setAdapter(BusDetails.this.mAdapter);
                    throw th;
                }
                recyclerView.addItemDecoration(dividerItemDecoration);
                BusDetails.this.mRecyclerView.setAdapter(BusDetails.this.mAdapter);
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                BusDetails.this.mDialog.dismiss();
                if ((volleyError instanceof TimeoutError) || (volleyError instanceof NoConnectionError)) {
                    Toast.makeText(BusDetails.this, "Failed to fetch data. Please check your network connection", 1).show();
                    Log.e("LoginActivity: ", "timeout");
                } else if (!(volleyError instanceof AuthFailureError)) {
                    if (volleyError instanceof ServerError) {
                        Toast.makeText(BusDetails.this, "Server error", 0).show();
                        Log.e("Server Error : ", BuildConfig.FLAVOR + volleyError.toString());
                    } else if (volleyError instanceof NetworkError) {
                        Toast.makeText(BusDetails.this, "Network error", 0).show();
                    } else if (volleyError instanceof ParseError) {
                        Toast.makeText(BusDetails.this, "Parse error", 0).show();
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
                hashMap.put("type", "G");
                return hashMap;
            }
        });
    }

    private void SetnavigationDrawer() {
        this.toolbar = (Toolbar) findViewById(R.id.toolbar_hm);
        NavigationView navigationView2 = (NavigationView) findViewById(R.id.navigation_view);
        this.navigationView = navigationView2;
        navigationView2.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                BusDetails.this.drawerLayout.closeDrawers();
                switch (menuItem.getItemId()) {
                    case R.id.alertMe /*2131230797*/:
                        BusDetails.this.startActivity(new Intent(BusDetails.this, AlertMeActivity.class));
                        BusDetails.this.overridePendingTransition(R.anim.right_in, R.anim.left_out);
                        return true;
                    case R.id.home /*2131230955*/:
                        BusDetails.this.startActivity(new Intent(BusDetails.this, MainActivity.class));
                        BusDetails.this.overridePendingTransition(R.anim.right_in, R.anim.left_out);
                        return true;
                    case R.id.ksrtc /*2131230978*/:
                        return false;
                    case R.id.logout /*2131231000*/:
                        Toast.makeText(BusDetails.this.getApplicationContext(), "Logging out", 0).show();
                        Intent intent = new Intent(BusDetails.this.getBaseContext(), Login_Activity.class);
                        intent.addFlags(67108864);
                        BusDetails.this.startActivity(intent);
                        BusDetails.this.finish();
                        return true;
                    default:
                        Toast.makeText(BusDetails.this.getApplicationContext(), "Somethings Wrong", 0).show();
                        return true;
                }
            }
        });
        DrawerLayout drawerLayout2 = (DrawerLayout) findViewById(R.id.drawer);
        this.drawerLayout = drawerLayout2;
        AnonymousClass5 r1 = new ActionBarDrawerToggle(this, drawerLayout2, this.toolbar, R.string.openDrawer, R.string.closeDrawer) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View view) {
                super.onDrawerOpened(view);
                view.bringToFront();
                BusDetails.this.drawerLayout.requestLayout();
            }
        };
        this.drawerLayout.setDrawerListener(r1);
        r1.syncState();
    }
}
