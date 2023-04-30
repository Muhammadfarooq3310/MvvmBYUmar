package com.example.mvvm_by_umar

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_by_umar.adapter.FectListAdapter
import com.example.mvvm_by_umar.ads.AdClass
import com.example.mvvm_by_umar.databinding.ActivityMainBinding
import com.example.mvvm_by_umar.model.FectModel
import com.example.mvvm_by_umar.viewmodel.AdModel
import com.example.mvvm_by_umar.viewmodel.ListViewModel
import com.google.android.gms.ads.*
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView


class MainActivity : AppCompatActivity(), FectListAdapter.ItemClickListener {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var fectModelList: List<FectModel>
    private lateinit var adapter: FectListAdapter
    private lateinit var viewModel: ListViewModel
     private  lateinit var adViewModel:AdModel

    private lateinit var nativeAd: NativeAd

    private var objAds: AdClass? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        adViewModel= ViewModelProvider(this)[AdModel::class.java]
        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
       /**Loading the Native ad**/
        val adLoader = AdLoader.Builder(this, getString(R.string.ADMOB_ADS_UNIT_ID))
            .forNativeAd { ad : NativeAd ->
                nativeAd = ad
                val adView = layoutInflater.inflate(R.layout.ad_unified, null) as NativeAdView
                populateNativeAdView(nativeAd, adView)
                binding.adContainer.addView(adView)
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(adError: LoadAdError) {/** Handle the error.**/
                    Toast.makeText(this@MainActivity, "${adError.message}", Toast.LENGTH_SHORT).show()
                }
            })
            .build()

        adLoader.loadAd(AdRequest.Builder().build())

        objAds = AdClass(this)
        objAds!!.loadInterstitial()

        adViewModel.startTimer()

        binding.adButton.setOnClickListener{
            if (adViewModel.getCount() < 2) {
                Toast.makeText(this, "Ad not loaded yet", Toast.LENGTH_SHORT).show()
            }else{
                objAds!!.intentFunctAdd(applicationContext, "",adViewModel)
            }

        }
/**show the data to recyclerView That featced from api**/

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        fectModelList= arrayListOf()

        adapter = FectListAdapter(this, fectModelList, this)
        recyclerView.adapter = adapter
        viewModel.getFectListObserver().observe(this, { fectModels ->
            if (fectModels != null) {
                fectModelList = fectModels
                adapter.setFectList(fectModels)
            } else {
                Toast.makeText(this, "There is no internet connection", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.makeApiCall()
    }

    override fun onDestroy() {
        super.onDestroy()
        adViewModel.stopTimer()
    }
    override fun onItemClick(fects: FectModel) {
    }
    /**Exit dialog function that call on backpressed**/
    private fun showExitDialog(){
        val dialog=Dialog(this@MainActivity)
        dialog.setContentView(R.layout.exit_dialog)
        val btnNo=dialog.findViewById<Button>(R.id.btn_cancel)
        val btnYes=dialog.findViewById<Button>(R.id.btn_exit)
        btnNo.setOnClickListener { //on button click dilalg will dismiss
            dialog.dismiss()
        }
        btnYes.setOnClickListener { //on button click dilalg will dismiss also applicaion will close
            dialog.dismiss()
            finishAffinity()
        }
        dialog.show()
    }
    override fun onBackPressed() {
        /**Exit Dialog on BackPressed**/
        showExitDialog()
    }

    /**populate the data to the Native Ad View**/
    private fun populateNativeAdView(nativeAd: NativeAd, adView: NativeAdView) {
        adView.mediaView = adView.findViewById<View>(R.id.ad_media) as com.google.android.gms.ads.nativead.MediaView
        adView.headlineView = adView.findViewById(R.id.ad_headline)
        adView.bodyView = adView.findViewById(R.id.ad_body)
        adView.callToActionView = adView.findViewById(R.id.ad_call_to_action)
        adView.iconView = adView.findViewById(R.id.ad_icon)
        adView.priceView = adView.findViewById(R.id.ad_price)
        adView.starRatingView = adView.findViewById(R.id.ad_stars)
        adView.storeView = adView.findViewById(R.id.ad_store)
        adView.advertiserView = adView.findViewById(R.id.ad_advertiser)

        (adView.headlineView as TextView).text = nativeAd.headline
        (adView.bodyView as TextView).text = nativeAd.body
        (adView.callToActionView as Button).text = nativeAd.callToAction
        (adView.iconView as ImageView).setImageDrawable(nativeAd.icon!!.drawable)
        adView.setNativeAd(nativeAd)
    }
}