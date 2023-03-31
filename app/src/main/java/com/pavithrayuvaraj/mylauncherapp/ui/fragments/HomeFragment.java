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
import android.widget.TextView;

import com.pavithrayuvaraj.mylauncherapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HomeFragment extends Fragment{

    public static final String TAG = HomeFragment.class.getName();

    View homeFragment;
    private GestureDetector mDetector;
    public TextView mDateTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        homeFragment =  inflater.inflate(R.layout.fragment_home, container, false);
        setCurrentDate();
        setGestureDetector();
        setTouchListener();
        return homeFragment;
    }

    private void setTouchListener() {
        homeFragment.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mDetector.onTouchEvent(event);
            }
        });
    }

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
                if(Navigation.findNavController(homeFragment).getCurrentDestination() != null
                        && Navigation.findNavController(homeFragment).getCurrentDestination().getId() == R.id.homeFragment) {
                    NavDirections action = HomeFragmentDirections.actionHomeFragmentToBottomSheetFragment();
                    Navigation.findNavController(homeFragment).navigate(action);
                }


                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }

    public void setCurrentDate() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM, dd");
        Calendar calendar = Calendar.getInstance();
        String date = simpleDateFormat.format(calendar.getTime());
        mDateTextView = homeFragment.findViewById(R.id.current_date);
        mDateTextView.setText(date);
        Log.d(TAG, "setCurrentDate: " + calendar.get(Calendar.MONTH));
    }

}