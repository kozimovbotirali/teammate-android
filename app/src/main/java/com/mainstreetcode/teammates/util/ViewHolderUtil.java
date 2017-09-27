package com.mainstreetcode.teammates.util;


import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ViewHolderUtil {

    public static View getItemView(@LayoutRes int res, ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(res, parent, false);
    }
}
