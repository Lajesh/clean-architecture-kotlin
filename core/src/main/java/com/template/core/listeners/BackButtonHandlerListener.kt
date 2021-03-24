package com.template.core.listeners

/**
 * Back button handler interface. Add/remove listener functionality
 * Created by Lajesh Dineshkumar on 10/31/2019.
 * Company: 
 * Email: lajeshds2007@gmail.com
 */
interface BackButtonHandlerListener {
    fun addBackpressListener(listner: BackPressListener)
    fun removeBackpressListener(listner: BackPressListener)
}