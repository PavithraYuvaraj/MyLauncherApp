package com.pavithrayuvaraj.mylauncherapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.pavithrayuvaraj.mylauncherapp.data.AppInfo;

/**
 * adapter class
 */
public class AppListAdapter extends ListAdapter<AppInfo, AppsViewHolder> {
    public static final String TAG = AppListAdapter.class.getName();
    private Context mContext;

    public AppListAdapter(@NonNull DiffUtil.ItemCallback<AppInfo> diffCallback, Context context) {
        super(diffCallback);
        mContext = context;
    }

    @NonNull
    @Override
    public AppsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        return AppsViewHolder.create(parent);
    }


    @Override
    public void onBindViewHolder(@NonNull AppsViewHolder holder, int position) {
        AppInfo appInfo = getItem(position);
        Log.d(TAG, "onBindViewHolder: " + position);
        holder.bind(appInfo, mContext, position);
    }

    public static class AppInfoDiff extends DiffUtil.ItemCallback<AppInfo> {

        @Override
        public boolean areItemsTheSame(@NonNull AppInfo oldItem, @NonNull AppInfo newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull AppInfo oldItem, @NonNull AppInfo newItem) {
            return oldItem.getLabel().equals(newItem.getLabel());
        }
    }
}
