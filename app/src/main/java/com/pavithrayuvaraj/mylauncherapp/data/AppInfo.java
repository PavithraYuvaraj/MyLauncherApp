package com.pavithrayuvaraj.mylauncherapp.data;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

public class AppInfo {
    private String label;
    private String packageName;
//    private byte[] icon;
    private Drawable icon;

    private boolean isAllowed = true;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

  /*  public byte[] getIcon() {
        return icon;
    }

    public void setIcon(byte[] icon) {
        this.icon = icon;
    }
*/
    public boolean isAllowed() {
        return isAllowed;
    }

    public void setAllowed(boolean allowed) {
        this.isAllowed = allowed;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}
