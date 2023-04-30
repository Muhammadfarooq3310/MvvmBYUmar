package com.example.mvvm_by_umar.ads;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mvvm_by_umar.viewmodel.AdModel;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;


public class AddsClass extends AppCompatActivity {
    AdView adView;
    Activity activity;
    private InterstitialAd mInterstitialAd;

    public AddsClass(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();

    }

    public void load_Interstitial() {
            AdRequest adRequest = new AdRequest.Builder().build();
            InterstitialAd.load(activity,AddIds.getInterstialId(), adRequest, new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                    mInterstitialAd = interstitialAd;
                    Log.i("TAG", "onAdLoaded");
                }
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    // Handle the error
                    Log.i("TAG", loadAdError.getMessage());
                    mInterstitialAd = null;
                    Toast.makeText(activity, "Ad not Loaded", Toast.LENGTH_SHORT).show();
                }
            });
    }
    public void intentFunctAdd(final Context context, final String type, AdModel adModel) {
            if (mInterstitialAd != null) {
                Log.d("intentfunction", "inside if: ");
                mInterstitialAd.show(activity);

                 mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        load_Interstitial();
                        adModel.updateCount(0);
                    }

                     @Override
                     public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                         super.onAdFailedToShowFullScreenContent(adError);
                         Log.d("intentfunction", "on ad failed: "+adError.getMessage());

                     }

                     @Override
                     public void onAdImpression() {
                         super.onAdImpression();
                         Log.d("intentfunction", "inside ad impression ");

                     }

                     @Override
                     public void onAdShowedFullScreenContent() {
                         super.onAdShowedFullScreenContent();
                     }
                 });
            }
            else {
                Log.d("intentfunction", "inside else: ");
            }

    }



}
