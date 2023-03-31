package com.pavithrayuvaraj.mylauncherapp.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.pavithrayuvaraj.mylauncherapp.R;
import com.pavithrayuvaraj.mylauncherapp.databinding.FragmentHomeBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HomeFragment extends Fragment{

    public static final String TAG = HomeFragment.class.getName();

    private FragmentHomeBinding mFragmentHomeBinding;
    private GestureDetector mDetector;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mFragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false);
        setCurrentDate();
        setGestureDetector();
        setTouchListener();
        return mFragmentHomeBinding.getRoot();
    }

    /**
     * method to set Touch Listener
     */
    @SuppressLint("ClickableViewAccessibility")
    private void setTouchListener() {
        mFragmentHomeBinding.getRoot().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mDetector.onTouchEvent(event);
            }
        });
    }

    /**
     * Method to set gesture listener to observe the fling event and open the bottom sheet
     */
    private void setGestureDetector(){
        mDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

                Log.d(TAG, "onFling SimpleOnGestureListener: ");
                // NavHostFragment navHostFragment = (NavHostFragment) getParentFragmentManager().findFragmentById(R.id.nav_host_fragment);
                //   NavController navController = navHostFragment.getNavController();
                if(Navigation.findNavController(mFragmentHomeBinding.getRoot()).getCurrentDestination() != null
                        && Navigation.findNavController(mFragmentHomeBinding.getRoot()).getCurrentDestination().getId() == R.id.homeFragment) {
                    NavDirections action = HomeFragmentDirections.actionHomeFragmentToBottomSheetFragment();
                    Navigation.findNavController(mFragmentHomeBinding.getRoot()).navigate(action);
                }


                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }

    /**
     * Method to set the current date in the Home screen in the format of month, date
     */
    public void setCurrentDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM, dd");
        Calendar calendar = Calendar.getInstance();
        String date = simpleDateFormat.format(calendar.getTime());
        mFragmentHomeBinding.currentDate.setText(date);
        Log.d(TAG, "setCurrentDate: " + calendar.get(Calendar.MONTH));
    }

}