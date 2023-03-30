package com.pavithrayuvaraj.mylauncherapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pavithrayuvaraj.mylauncherapp.R;
import com.pavithrayuvaraj.mylauncherapp.data.AppInfo;
import com.pavithrayuvaraj.mylauncherapp.interfaces.RecyclerViewItemClickListener;

public class AppsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public static final String TAG = AppsViewHolder.class.getName();
    public RecyclerViewItemClickListener mRecyclerViewItemClickListener;
    public int position;
    public ImageView imageView;
    public TextView textView;
    public AppInfo mAppInfo;
    public AppsViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.textView);
        imageView = itemView.findViewById(R.id.imageView);

        itemView.setOnClickListener(this);
    }

    public void bind(AppInfo appInfo,
                     Context context,
                     RecyclerViewItemClickListener recyclerViewItemClickListener,
                     int position) {
        mRecyclerViewItemClickListener = recyclerViewItemClickListener;

        this.position = position;
        mAppInfo = appInfo;

        textView.setText(mAppInfo.getLabel());

        imageView.setImageDrawable(mAppInfo.getIcon());
        if(!mAppInfo.isAllowed()) {
            Log.d(TAG, "bind: app not allowed " + mAppInfo.getLabel());
            itemView.setClickable(false);
            //itemView.setBackgroundColor(Color.parseColor("#66000000"));
            textView.setTextColor(Color.parseColor("#808080"));
        } else {
            itemView.setClickable(true);
            if(isNightMode(itemView.getContext())) {
                textView.setTextColor(Color.WHITE);
            } else {
                textView.setTextColor(Color.BLACK);
            }
        }
    }

    public boolean isNightMode(Context context) {
        int nightModeFlags = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES;
    }

    static AppsViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new AppsViewHolder(view);
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: view " + v.getId());
        Context context = v.getContext();
        Intent launcherIntent = context.getPackageManager().getLaunchIntentForPackage(mAppInfo.getPackageName().toString());
        context.startActivity(launcherIntent);
        Toast.makeText(context, mAppInfo.getLabel(), Toast.LENGTH_LONG).show();
    }
}
