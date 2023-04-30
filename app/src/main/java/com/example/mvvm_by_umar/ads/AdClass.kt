package com.example.mvvm_by_umar.ads

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mvvm_by_umar.viewmodel.AdModel
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class AdClass(private val activity: Activity) : AppCompatActivity() {
    private var adView: AdView? = null
    private var mInterstitialAd: InterstitialAd? = null
    override fun onPause() {
        adView?.pause()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        adView?.resume()
    }

    override fun onDestroy() {
        adView?.destroy()
        super.onDestroy()
    }

    fun loadInterstitial() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            activity,
            "ca-app-pub-3940256099942544/1033173712",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                    Log.i("TAG", "onAdLoaded")
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    // Handle the error
                    Log.i("TAG", loadAdError.message)
                    mInterstitialAd = null
                    Toast.makeText(activity, "Ad not Loaded", Toast.LENGTH_SHORT).show()
                }
            })
    }

    fun intentFunctAdd(context: Context, type: String, adModel: AdModel) {
        mInterstitialAd?.let { interstitialAd ->
            Log.d("intentfunction", "inside if: ")
            interstitialAd.show(activity)
            interstitialAd.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    loadInterstitial()
                    adModel.updateCount(0)
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    super.onAdFailedToShowFullScreenContent(adError)
                    Log.d("intentfunction", "on ad failed: ${adError.message}")
                }

                override fun onAdImpression() {
                    super.onAdImpression()
                    Log.d("intentfunction", "inside ad impression ")
                }

                override fun onAdShowedFullScreenContent() {
                    super.onAdShowedFullScreenContent()
                }
            }
        } ?: run {
            Log.d("intentfunction", "inside else: ")
        }
    }
}