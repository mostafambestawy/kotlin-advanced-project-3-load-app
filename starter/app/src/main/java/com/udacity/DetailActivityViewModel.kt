package com.udacity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DetailActivityViewModel : ViewModel() {
    var statusString = MutableLiveData<String>("")

    var fileName = MutableLiveData<String>("")
    fun updateStatusString(mStatusString: String) {
        statusString.value = mStatusString
    }

    fun updateFileName(mFileName: String) {
        fileName.value = mFileName
    }
}