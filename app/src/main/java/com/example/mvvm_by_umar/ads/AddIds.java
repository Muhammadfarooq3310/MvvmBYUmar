package com.example.mvvm_by_umar.ads;


 import com.example.mvvm_by_umar.BuildConfig;

public class AddIds {
    public static  String  getInterstialId(){

        String id = "";
        if(BuildConfig.DEBUG){
            id = "ca-app-pub-3940256099942544/1033173712"; /**Interstial test id**/
        }else{
            id = "ca-app-pub-3940256099942544/1033173712"; /** Interstial test id here if have orignal can replace**/
        }
        return  id;
    }
}
