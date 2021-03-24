package com.template.core.viewmodel.base

import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.template.core.arc.SingleLiveEvent
import com.template.core.utils.NavigationCommand
import com.template.core.viewmodel.SharedViewModel
import com.template.core.viewmodel.ToolbarPropertyViewModel
import com.template.domain.entity.common.ErrorEntity

/****
 * Base view model. All the common implementation of viewmodel goes here
 * Author: Lajesh Dineshkumar
 * Company:
 * Created on: 2/2/21
 * Modified on: 2/2/21
 *****/
abstract class BaseViewModel : ViewModel(), Observable {

    /**
     * Live data to handle loading
     */
    val loadingEvent = SingleLiveEvent<Boolean>()

    /**
     * Live data to handle error
     */
    val errorEvent = SingleLiveEvent<ErrorEntity.Error>()

    /**
     * Set the Logout Click to true for showing confirmation before logout
     */
    var logoutClickEvent = SingleLiveEvent<Boolean>()

    val navigationCommands = SingleLiveEvent<NavigationCommand>()

    val hideKeyboard = MutableLiveData<Boolean>()


    lateinit var sharedViewModel: SharedViewModel


    private val callbacks = PropertyChangeRegistry()


    var toolbarPropertyViewModel: ToolbarPropertyViewModel = ToolbarPropertyViewModel()


    val isSharedViewModelInitialized: Boolean
        get() = this::sharedViewModel.isInitialized


    override fun addOnPropertyChangedCallback(
        callback: Observable.OnPropertyChangedCallback
    ) {
        callbacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(
        callback: Observable.OnPropertyChangedCallback
    ) {
        callbacks.remove(callback)
    }

    /**
     * Notifies observers that all properties of this instance have changed.
     */
    internal fun notifyChange() {
        callbacks.notifyCallbacks(this, 0, null)
    }

    /**
     * Notifies observers that a specific property has changed. The getter for the
     * property that changes should be marked with the @Bindable annotation to
     * generate a field in the BR class to be used as the fieldId parameter.
     *
     * @param fieldId The generated BR id for the Bindable field.
     */
    internal fun notifyPropertyChanged(fieldId: Int) {
        callbacks.notifyCallbacks(this, fieldId, null)
    }

    fun showLoading(show: Boolean) {
        loadingEvent.value = show
    }

    fun postLoading(show: Boolean) {
        loadingEvent.postValue(show)
    }

    fun getLoading(): SingleLiveEvent<Boolean> {
        return loadingEvent
    }

    /**
     * Method call to handle error
     */
    fun setError(error: ErrorEntity.Error?) {
        errorEvent.value = error
    }

    fun navigate(directions: NavDirections) {
        navigationCommands.postValue(NavigationCommand.To(directions))
    }
}
