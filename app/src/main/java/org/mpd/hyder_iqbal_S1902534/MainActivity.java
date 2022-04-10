package org.mpd.hyder_iqbal_S1902534;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.FragmentManager;

import android.app.admin.SystemUpdateInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
//Iqbal_Haider_S1902534
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import org.mpd.hyder_iqbal_S1902534.adapter.FragmentAdapter;
import org.mpd.hyder_iqbal_S1902534.adapter.IncidentsMainAdapter;
import org.mpd.hyder_iqbal_S1902534.databinding.ActivityMainBinding;
import org.mpd.hyder_iqbal_S1902534.fragments.PlannedFragment;
import org.mpd.hyder_iqbal_S1902534.fragments.RoadWorksFragment;
import org.mpd.hyder_iqbal_S1902534.modelclasses.RoadWorksModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding layoutBinding;
    private int mPosition = 0;
    private MenuItem ivSearch;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());

        settingTabLayout();
        settingViewPager();
    }

    private void settingTabLayout() {

        layoutBinding.tabLayout.addTab(layoutBinding.tabLayout.newTab().setText("Current"));
        layoutBinding.tabLayout.addTab(layoutBinding.tabLayout.newTab().setText("Road Works"));
        layoutBinding.tabLayout.addTab(layoutBinding.tabLayout.newTab().setText("Planned"));
        layoutBinding.tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    private void settingViewPager() {
        final FragmentAdapter adapter = new FragmentAdapter(this, getSupportFragmentManager(),
                layoutBinding.tabLayout.getTabCount());
        layoutBinding.viewPager.setAdapter(adapter);

        layoutBinding.viewPager.addOnPageChangeListener(new
                TabLayout.TabLayoutOnPageChangeListener(layoutBinding.tabLayout));

        layoutBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                layoutBinding.viewPager.setCurrentItem(tab.getPosition());
                /*if (mPosition == 1) {
                    RoadWorksFragment.filter("");
                } else if (mPosition == 2) {
                    PlannedFragment.filter("");
                }*/
                mPosition = tab.getPosition();
                if (ivSearch != null) {
                    MenuItemCompat.collapseActionView(ivSearch);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        ivSearch = menu.findItem(R.id.action_search);
        searchView = (SearchView) ivSearch.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (mPosition == 1) {
                    RoadWorksFragment.filter(newText);
                } else if (mPosition == 2) {
                    PlannedFragment.filter(newText);
                }
                return false;
            }
        });

        ivSearch.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Toast.makeText(MainActivity.this, "Action Expand", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Toast.makeText(MainActivity.this, "Action Collapse", Toast.LENGTH_SHORT).show();
                return true;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }
}