package com.pavithrayuvaraj.mylauncherapp.ui.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.pavithrayuvaraj.mylauncherapp.adapters.AppListAdapter;
import com.pavithrayuvaraj.mylauncherapp.data.AppInfo;
import com.pavithrayuvaraj.mylauncherapp.databinding.BottomSheetLayoutBinding;
import com.pavithrayuvaraj.mylauncherapp.interfaces.RecyclerViewItemClickListener;
import com.pavithrayuvaraj.mylauncherapp.viewmodel.DenyListViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class displays the list of apps installed in the device
 */
public class BottomSheetFragment extends BottomSheetDialogFragment implements RecyclerViewItemClickListener {


    public static final String TAG = BottomSheetFragment.class.getName();
    private List<AppInfo> mAppList;
    private DenyListViewModel mDenyListViewModel;
    private List<String> mDenyList;
    private SearchView mSearchView;
    private AppListAdapter mAppListAdapter;
    private BottomSheetLayoutBinding mFragmentBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mFragmentBinding = BottomSheetLayoutBinding.inflate(inflater, container, false);

        mDenyListViewModel = new ViewModelProvider(this).get(DenyListViewModel.class);

        initViews();

        if(mDenyList == null) {
            mDenyList = new ArrayList<>();
        }
        configureRecyclerView();
        getDenyList();
        getAppList();
        configureSearch();
        return mFragmentBinding.getRoot();
    }



    /**
     * view initialization
     */
    private void initViews() {
        mSearchView = mFragmentBinding.searchView;
        mSearchView.requestFocus();
    }



    /**
     * Method to config recycler view
     */
    public void configureRecyclerView() {
        RecyclerView recyclerView = mFragmentBinding.appList;
        mAppListAdapter = new AppListAdapter(new AppListAdapter.AppInfoDiff(), getContext(), this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAppListAdapter);
    }

    /**
     * Method updates the adapter whenever there is a change in the deny list
     */
    private void getDenyList() {
        mDenyListViewModel.getData(getActivity().getApplication());
        mDenyListViewModel.getDenyListRepo().observe(this, list -> {

            mDenyList.addAll(list);
            updateDenyList();
            Log.d(TAG, "getDenyList: " + mAppList.size());
            mAppListAdapter.submitList(mAppList);
        });
    }

    /**
     * Method updates the existing data in the list based on the deny list data
     */
    private void updateDenyList() {
        for(AppInfo appInfo : mAppList) {
            Log.d(TAG, "updateDenyList: " + appInfo.getPackageName());
            Log.d(TAG, "updateDenyList: " + mDenyList.get(0));
            if(mDenyList.contains(appInfo.getPackageName())) {
                Log.d(TAG, "updateDenyList: if");
                appInfo.setAllowed(false);
            }
        }
    }

    /**
     * method to get the installed apps using package manager and add it to a list
     */
    public void getAppList() {
        PackageManager pm = getContext().getPackageManager();
        if(mAppList == null) {
            mAppList = new ArrayList<>();
        }
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resolveInfos) {
            AppInfo ap = new AppInfo();
            ap.setLabel(resolveInfo.loadLabel(pm).toString());
            ap.setPackageName(resolveInfo.activityInfo.packageName);

            ap.setIcon(resolveInfo.loadIcon(pm));
            mAppList.add(ap);
        }

        Collections.sort(mAppList, (o1, o2) -> o1.getLabel().toString().compareTo(o2.getLabel().toString()));
        mAppListAdapter.submitList(mAppList);
    }

    /**
     * Method to configure search functionality for the list of apps
     */
    private void configureSearch() {
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });
    }

    /**
     * method to update the list based on the search string
     * @param filter
     */
    private void filter(String filter) {
        List<AppInfo> mFilteredList = new ArrayList<>();
        for(AppInfo ap : mAppList) {
            if(ap.getLabel().toLowerCase().contains(filter.toLowerCase())) {
                mFilteredList.add(ap);
            }
        }
        if(mFilteredList.isEmpty()) {
            Toast.makeText(getContext(), "Searched app is not installed.", Toast.LENGTH_SHORT).show();
        } else {
            mAppListAdapter.submitList(mFilteredList);
        }
    }

    /**
     * method to open the app being click on
     * @param position  list position
     */
    @Override
    public void onItemClick(int position) {
        Log.d(TAG, "onItemClick: position " + position);
        Intent launcherIntent = getContext().getPackageManager().getLaunchIntentForPackage(mAppList.get(position).getPackageName());
        startActivity(launcherIntent);
        Toast.makeText(getContext(), mAppList.get(position).getLabel(), Toast.LENGTH_LONG).show();
    }
}