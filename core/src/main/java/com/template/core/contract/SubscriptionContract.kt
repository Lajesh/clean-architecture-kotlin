package com.template.core.contract

/****
 * Subscription contract. Wherever you want to subcribe for the network response
 * or navigation event, implement this interface
 * Author: Lajesh Dineshkumar
 * Company:
 * Created on: 2020-03-03
 * Modified on: 2020-03-03
 *****/
interface SubscriptionContract {
    fun subscribeNetworkResponse() {
        // Implementation goes in the owner
    }
    fun subscribeNavigationEvent() {
        // Implementation goes in the owner
    }
}
