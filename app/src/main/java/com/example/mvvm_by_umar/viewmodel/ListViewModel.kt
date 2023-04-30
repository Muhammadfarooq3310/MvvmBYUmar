package com.example.mvvm_by_umar.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.mvvm_by_umar.network.APIService
import com.example.mvvm_by_umar.network.RetroInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import androidx.lifecycle.ViewModel
import com.example.mvvm_by_umar.model.FectModel

class ListViewModel : ViewModel() {
    val TAG = "ListViewModelTag"

    private val fectList: MutableLiveData<List<FectModel>> by lazy {
        MutableLiveData<List<FectModel>>()
    }

    fun getFectListObserver(): MutableLiveData<List<FectModel>> {
        return fectList
    }

    fun makeApiCall() {
        val apiService = RetroInstance.getRetroClient().create(APIService::class.java)
        val call = apiService.getList()
        call.enqueue(object : Callback<List<FectModel>> {
            override fun onResponse(
                call: Call<List<FectModel>>,
                response: Response<List<FectModel>>
            ) {

                val fectTextList = ArrayList<String>() // create new list for text only
                response.body()?.let { fectModelList ->
                    for (model in fectModelList) {
                        fectTextList.add(model.text) // add text to new list
                    }
                    val newFectList = fectTextList.map { FectModel(type = "cat", text = it) } // create new list of FectModel from text list
                    fectList.postValue(newFectList) // post the new list to live data
                }

            }
            override fun onFailure(call: Call<List<FectModel>>, t: Throwable) {
                fectList.postValue(null)
            }
        })
    }
}