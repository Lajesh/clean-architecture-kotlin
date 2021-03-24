package com.template.core.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.template.core.arc.SingleLiveEvent

/****
 * Tool bar Property ViewModel
 * Author: Lajesh Dineshkumar
 * Company:
 * Created on: 5/17/20
 * Modified on: 5/17/20
 *****/
class ToolbarPropertyViewModel : ViewModel() {
    var showBack = MutableLiveData<Boolean>(true)
    var showClose = MutableLiveData<Boolean>(false)
    var toolbarTitle = MutableLiveData<String>()
    val closeButtonAction: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val backButtonAction: SingleLiveEvent<Boolean> = SingleLiveEvent()
}
