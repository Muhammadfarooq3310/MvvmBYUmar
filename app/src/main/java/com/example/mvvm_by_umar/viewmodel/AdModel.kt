package com.example.mvvm_by_umar.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
/**Use this to maintain time between ad load and show**/
class AdModel:ViewModel(){
    private  var timer=Timer()
    val count=MutableLiveData<Int>(0)
    var myValue=0
    fun updateCount(value:Int){
        count.value=value
        myValue=value
    }
    fun getCount():Int{
        return myValue
    }
    fun startTimer(){
        timer= Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                myValue++
                CoroutineScope(Dispatchers.Main).launch {
                    updateCount(myValue)
                }
            }

        },0,1000)
    }
    fun stopTimer() {
        timer.cancel()
        timer.purge()
    }
}