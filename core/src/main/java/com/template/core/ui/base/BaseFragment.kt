package com.template.core.ui.base

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.template.core.R
import com.template.core.contract.SubscriptionContract
import com.template.core.di.Injectable
import com.template.core.listeners.BackButtonHandlerListener
import com.template.core.listeners.BackPressListener
import com.template.core.utils.NavigationCommand
import com.template.core.viewmodel.SharedViewModel
import com.template.core.viewmodel.ToolbarPropertyViewModel
import com.template.core.viewmodel.base.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.reflect.KClass

/****
 * Parent for all the UI fragments. All the common things to be kept here
 * Author: Lajesh Dineshkumar
 * Company:
 * Created on: 2020-03-03
 * Modified on: 2020-03-03
 *****/
abstract class BaseFragment<V : ViewModel, D : ViewDataBinding>(clazz: KClass<V>) :
    Fragment(),
    Injectable,
    BackPressListener {

    val viewModel: V by viewModel(clazz)

    private val sharedModel by sharedViewModel(SharedViewModel::class)

    open lateinit var sharedViewModel: SharedViewModel

    protected lateinit var dataBinding: D


    @get:LayoutRes
    protected abstract val layoutRes: Int

    abstract val bindingVariable: Int

    open val subscriptionContract: SubscriptionContract? = null

    protected var isBackDisabled: Boolean = false

    private var backButtonHandler: BackButtonHandlerListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (viewModel as BaseViewModel).sharedViewModel = sharedModel
        sharedViewModel = sharedModel
        subscriptionContract?.subscribeNetworkResponse()
    }

    /**
     * Method to override the back press behaviour on indivitual fragment
     */
    override fun onBackPress(): Boolean {
        if (!isBackDisabled) {
            return false
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        backButtonHandler?.addBackpressListener(this)
    }

    override fun onPause() {
        super.onPause()
        backButtonHandler?.removeBackpressListener(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        backButtonHandler = context as BackButtonHandlerListener
    }

    override fun onDetach() {
        super.onDetach()
        backButtonHandler?.removeBackpressListener(this)
        backButtonHandler = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupToolbar(getToolbarProperty())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        dataBinding.lifecycleOwner = viewLifecycleOwner
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding.setVariable(bindingVariable, viewModel)
        dataBinding.executePendingBindings()
        initViewSubscriptions()
    }

    private fun initViewSubscriptions() {
        subscriptionContract?.subscribeNavigationEvent()

        (viewModel as BaseViewModel).errorEvent.observe(
            viewLifecycleOwner,
            Observer {
                showErrorDialog(it.errorMessage as String)
            }
        )

        (viewModel as BaseViewModel).hideKeyboard.observe(
            viewLifecycleOwner, Observer {
                val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
            }
        )

        (viewModel as BaseViewModel).navigationCommands.observe(
            viewLifecycleOwner, Observer {
                when (it) {
                    is NavigationCommand.To, -> findNavController().navigate(it.directions)
                    is NavigationCommand.Back -> findNavController().popBackStack()
                    is NavigationCommand.BackTo -> findNavController().popBackStack(it.destinationId, false)
                    is NavigationCommand.ToByDeepLink -> findNavController().navigate(it.deepLink)

                }
            }
        )
    }

    /**
     * Method to show service related error messages
     */
    private fun showErrorDialog(message: String?) {
        val alertDialog = AlertDialog.Builder(activity)
            .setTitle(requireActivity().getString(R.string.title_error))
            .setMessage(message)
            .setPositiveButton(requireActivity().getString(R.string.action_ok), null)
            .create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    protected open fun setupToolbar(toolbarProperty: ToolbarPropertyViewModel?) {
        // Nothing to Implement
        toolbarProperty?.backButtonAction?.observe(
            viewLifecycleOwner,
            Observer {
                activity?.let {
                    // Logic
                }
            }
        )

        toolbarProperty?.closeButtonAction?.observe(
            viewLifecycleOwner,
            Observer {
                // Logic
            }
        )
    }

    protected fun getToolbarProperty(): ToolbarPropertyViewModel? {
        return (viewModel as BaseViewModel).toolbarPropertyViewModel
    }

    /**
     * setting up locally
     */
    fun setAppLocale(lngCode: String) {
        (activity as? BaseActivity<*, *>)?.setUpLocale(lngCode)
    }

    /**
     * Return the base activity
     */
    fun getBaseActivity(): BaseActivity<*, *>? {
        return activity?.let {
            return when (it) {
                is BaseActivity<*, *> -> {
                    it
                }
                else -> {
                    null
                }
            }
        }
    }

    /**
     * Setting the layer type to HARDWARE for better animation performance
     */
    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        var animation = super.onCreateAnimation(transit, enter, nextAnim)
        if (animation == null && nextAnim != 0) {
            animation = AnimationUtils.loadAnimation(activity, nextAnim)

            if (animation != null) {
                view?.setLayerType(View.LAYER_TYPE_HARDWARE, null)
                animation.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationRepeat(p0: Animation?) {
                        // Nothing goes here
                    }

                    override fun onAnimationEnd(p0: Animation?) {
                        view?.setLayerType(View.LAYER_TYPE_NONE, null)
                    }

                    override fun onAnimationStart(p0: Animation?) {
                        // Nothing goes here
                    }
                })
            }
        }

        return animation
    }
}
