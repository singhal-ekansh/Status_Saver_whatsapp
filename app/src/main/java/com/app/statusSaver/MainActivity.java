package com.app.statusSaver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.os.Bundle;


import com.app.statusSaver.fragments.Gallery;
import com.app.statusSaver.fragments.savedStatus;
import com.app.statusSaver.fragments.status;
import com.google.android.material.tabs.TabLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private Toolbar toolbar;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.myTabs);
        toolbar = findViewById(R.id.myToolbar);
        viewPager = findViewById(R.id.myViewPager);

        //get files permission from user
        Dexter.withContext(this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {

                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();


        setSupportActionBar(toolbar);
        setViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setViewPager(ViewPager view) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),  BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerAdapter.addFragment(new status(), "STATUS");
        viewPagerAdapter.addFragment(new savedStatus(), "SAVED STATUS");
        viewPagerAdapter.addFragment(new Gallery(), "GALLERY");

        view.setAdapter(viewPagerAdapter);
    }
}