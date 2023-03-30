package com.pavithrayuvaraj.mylauncherapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pavithrayuvaraj.mylauncherapp.data.AppInfo;

import java.util.List;

public class AppInfoViewModel extends ViewModel {
    public MutableLiveData<List<AppInfo>> mAppInfoListMutableLiveData;
    public AppInfoViewModel() {
        if(mAppInfoListMutableLiveData == null) {
            mAppInfoListMutableLiveData = new MutableLiveData<>();
        }
    }

    public MutableLiveData<List<AppInfo>> getmAppInfoListMutableLiveData() {
        return mAppInfoListMutableLiveData;
    }
}
