package com.template.core.ui.base

import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.template.core.listeners.BackButtonHandlerListener
import com.template.core.listeners.BackPressListener
import com.template.core.utils.LocaleManager
import com.template.core.viewmodel.ToolbarPropertyViewModel
import com.template.core.viewmodel.base.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.ref.WeakReference
import java.util.ArrayList
import kotlin.reflect.KClass

/****
 * All the activity should be extended from this parent class.
 * All the common functionalities across activities should be kept here
 * Author: Lajesh Dineshkumar
 * Company:
 * Created on: 2020-03-03
 * Modified on: 2020-03-03
 *****/
abstract class BaseActivity<V : ViewModel, D : ViewDataBinding>(clazz: KClass<V>) :
    AppCompatActivity(), BackButtonHandlerListener {

    val viewModel: V by viewModel(clazz)

    private val toolbarModel: ToolbarPropertyViewModel by viewModel()

    lateinit var dataBinding: D

    @get:LayoutRes
    protected abstract val layoutRes: Int

    abstract val bindingVariable: Int

    protected abstract fun getViewModel(): Class<V>

    private val backClickListenersList = ArrayList<WeakReference<BackPressListener>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeDataBinding()
        (viewModel as? BaseViewModel)?.toolbarPropertyViewModel = toolbarModel

    }

    private fun initializeDataBinding(){
        dataBinding = DataBindingUtil.setContentView(this, layoutRes)
        dataBinding.lifecycleOwner = this

        (viewModel as? BaseViewModel)?.toolbarPropertyViewModel = toolbarModel

        dataBinding.setVariable(bindingVariable, viewModel)
        dataBinding.executePendingBindings()
    }

    fun logout(showConfirm: Boolean? = false) {
        (viewModel as BaseViewModel).logoutClickEvent.value = showConfirm ?: false
    }

    /**
     * Showing progress bar over screen
     */
    fun showLoading(it: Boolean?) {
        it?.let { disableTouch ->
            (viewModel as BaseViewModel).showLoading(it)
            if (disableTouch) {
                window.setFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                )
            } else {
                window.clearFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                )
            }
        }
    }

    fun setUpLocale(lngCode: String, isSelected: Boolean = false) {
        val currentLanguage = LocaleManager.getCurrentLanguage(this)
        if (!currentLanguage.equals(lngCode, true)) {
            LocaleManager.setNewLocale(this, lngCode)
            if (currentLanguage != null || isSelected) {
                LocaleManager.notifyLanguageChange(this)
            }
        }
    }

    /**
     * Methods which handles the hardware back button / navigation back view
     */
    override fun onBackPressed() {
        if (!fragmentsBackKeyIntercept()) {
            super.onBackPressed()
        }
    }

    /**
     * Add the back navigation listener here.
     * Call this method from onAttach of your fragment
     * @param listner - back navigation listener
     */
    override fun addBackpressListener(listner: BackPressListener) {
        backClickListenersList.add(WeakReference(listner))
    }

    /**
     * remove the back navigation listener here.
     * Call this method from onDetach of your fragment
     * @param listner - back navigation listener
     */
    override fun removeBackpressListener(listner: BackPressListener) {
        val iterator = backClickListenersList.iterator()
        while (iterator.hasNext()) {
            val weakRef = iterator.next()
            if (weakRef.get() === listner) {
                iterator.remove()
            }
        }
    }

    /**
     * This method checks if any frgament is overriding the back button behavior or not
     * @return true/false
     */
    private fun fragmentsBackKeyIntercept(): Boolean {
        var isIntercept = false
        for (weakRef in backClickListenersList) {
            val backpressListner = weakRef.get()
            if (backpressListner != null) {
                val isFragmIntercept: Boolean = backpressListner.onBackPress()
                if (!isIntercept)
                    isIntercept = isFragmIntercept
            }
        }
        return isIntercept
    }

}