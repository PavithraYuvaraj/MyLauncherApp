package com.pavithrayuvaraj.mylauncherapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import com.pavithrayuvaraj.mylauncherapp.R;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getCurrentFragment();
        // if the fragment is HomeFragment, we are blocking the back button operation
        if (fragment != null && fragment.getClass().getSimpleName().equals("BottomSheetFragment")) {
            super.onBackPressed();
        }
    }

    /**
     * Method to get the current fragment
     * @return - fragment
     */
    private Fragment getCurrentFragment() {
        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        return navHostFragment == null ?  null : navHostFragment.getChildFragmentManager().getFragments().get(0);
    }
}
