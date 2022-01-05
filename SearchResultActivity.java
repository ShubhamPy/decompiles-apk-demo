package in.linus.busmate.Activity;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.android.material.navigation.NavigationView;
import in.linus.busmate.Adapter.BusDetailsAdapter;
import in.linus.busmate.Model.BusmateModelHelper;
import in.linus.busmate.R;
import in.linus.busmate.Utility.CustomProgressDialog;
import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity {
    /* access modifiers changed from: private */
    public DrawerLayout drawerLayout;
    private RecyclerView.Adapter mAdapter;
    CustomProgressDialog mDialog;
    private RecyclerView.LayoutManager mLayoutManager;
    List<BusmateModelHelper> mModel = new ArrayList();
    private RecyclerView mRecyclerView;
    private TextView mTitleBar;
    private NavigationView navigationView;
    private Toolbar toolbar;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_search_result);
        TextView textView = (TextView) findViewById(R.id.txt_title);
        this.mTitleBar = textView;
        textView.setText("Search Results");
        this.mDialog = new CustomProgressDialog(this);
        this.mModel = getIntent().getParcelableArrayListExtra("bus_data");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.m_recycler_view);
        this.mRecyclerView = recyclerView;
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        this.mLayoutManager = linearLayoutManager;
        this.mRecyclerView.setLayoutManager(linearLayoutManager);
        this.mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        this.mAdapter = new BusDetailsAdapter(this.mModel, this);
        this.mRecyclerView.addItemDecoration(new DividerItemDecoration(this, 1));
        this.mRecyclerView.setAdapter(this.mAdapter);
        SetnavigationDrawer();
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
                SearchResultActivity.this.drawerLayout.closeDrawers();
                switch (menuItem.getItemId()) {
                    case R.id.alertMe /*2131230797*/:
                        SearchResultActivity.this.startActivity(new Intent(SearchResultActivity.this, AlertMeActivity.class));
                        SearchResultActivity.this.overridePendingTransition(R.anim.right_in, R.anim.left_out);
                        return true;
                    case R.id.home /*2131230955*/:
                        SearchResultActivity.this.startActivity(new Intent(SearchResultActivity.this, MainActivity.class));
                        SearchResultActivity.this.overridePendingTransition(R.anim.right_in, R.anim.left_out);
                        return true;
                    case R.id.ksrtc /*2131230978*/:
                        return false;
                    case R.id.logout /*2131231000*/:
                        Toast.makeText(SearchResultActivity.this.getApplicationContext(), "Logging out", 0).show();
                        Intent intent = new Intent(SearchResultActivity.this.getBaseContext(), Login_Activity.class);
                        intent.addFlags(67108864);
                        SearchResultActivity.this.startActivity(intent);
                        SearchResultActivity.this.finish();
                        return true;
                    default:
                        Toast.makeText(SearchResultActivity.this.getApplicationContext(), "Somethings went wrong!", 0).show();
                        return true;
                }
            }
        });
        DrawerLayout drawerLayout2 = (DrawerLayout) findViewById(R.id.drawer);
        this.drawerLayout = drawerLayout2;
        AnonymousClass2 r1 = new ActionBarDrawerToggle(this, drawerLayout2, this.toolbar, R.string.openDrawer, R.string.closeDrawer) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View view) {
                super.onDrawerOpened(view);
                view.bringToFront();
                SearchResultActivity.this.drawerLayout.requestLayout();
            }
        };
        this.drawerLayout.setDrawerListener(r1);
        r1.syncState();
    }
}
