/**
 * View model class for initiating a network request.
 */
package com.pavithrayuvaraj.mylauncherapp.viewmodel;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.pavithrayuvaraj.mylauncherapp.data.RejectionListRepository;

import java.util.List;

public class DenyListViewModel extends AndroidViewModel {
    private MutableLiveData<List<String>> mDenyListMutableLiveData;
    private RejectionListRepository mRejectionListRepository;


    public DenyListViewModel(@NonNull Application application) {
        super(application);
        getData(application);
    }

    /**
     * Method to get data from the server
     * @param application - Application
     */
    public void getData(Application application) {
        if (mDenyListMutableLiveData != null) {
            return;
        }
        mRejectionListRepository = RejectionListRepository.getInstance(application);
        mDenyListMutableLiveData = mRejectionListRepository.getDenyList();
    }

    /**
     * method returns a list of denied apps
     * @return - List of strings
     */
    public LiveData<List<String>> getDenyListRepo() {
        return mDenyListMutableLiveData;
    }
}
