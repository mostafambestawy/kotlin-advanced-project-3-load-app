package com.udacity

import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel(){
    var selectedUrlNo:Int? = null
    fun setUrl1(isChecked:Boolean){
        if(isChecked) selectedUrlNo = 1
    }
    fun setUrl2(isChecked:Boolean){
        if(isChecked) selectedUrlNo = 2
    }
    fun setUrl3(isChecked:Boolean){
        if(isChecked) selectedUrlNo = 3
    }

}