package com.example.mvvm_by_umar.ads

import android.app.Application
import com.google.android.gms.ads.MobileAds

class AppClass : Application(){
    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this)
    }
}