package com.noah37.alertview

import androidx.appcompat.app.AppCompatActivity

open class UIAlertController {

    var title: String
    var message: String
    var preferredStyle: UIAlertControllerStyle
    private var actions: ArrayList<UIAlertAction> = ArrayList()

    constructor(title:String, message: String, preferredStyle: UIAlertControllerStyle) {
        this.title = title
        this.message = message
        this.preferredStyle = preferredStyle
    }

    /**
     * Add Actions to UIAlertController
     * @param action: UIAlertAction
     */
    fun addAction(action: UIAlertAction) {
        actions.add(action)
    }

    /**
     * Show AlertView
     * @param activity: AppCompatActivity
     */
    fun presentModalViewController(activity: AppCompatActivity) {
        when (preferredStyle) {
            UIAlertControllerStyle.ACTIONSHEET -> {
                val bottomSheet = iOSActionSheetFragment(title, message, actions, AlertThemeStyle.LIGHT, "")
                bottomSheet.show(activity.supportFragmentManager, bottomSheet.tag)
            }
            UIAlertControllerStyle.ALERT -> {
                val bottomSheet = iOSAlertFragment(title, message, actions, AlertThemeStyle.LIGHT, "")
                bottomSheet.show(activity.supportFragmentManager, bottomSheet.tag)
            }
        }
    }

    companion object {
        @JvmStatic
        fun alerControllerWithTitle(title: String, message: String, preferredStyle: UIAlertControllerStyle) =
            UIAlertController(title, message, preferredStyle)
    }
}

fun AppCompatActivity.presentModalViewController(controller:UIAlertController) {
    controller.presentModalViewController(this)
}