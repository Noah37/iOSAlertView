package com.noah37.alertview

import androidx.appcompat.app.AppCompatActivity

open class UIAlertView(private var title: String, private var message: String,
                private var style: UIAlertControllerStyle) {

    private var cancelButtonText: String = ""
    private var theme: AlertThemeStyle = AlertThemeStyle.LIGHT
    private var actions: ArrayList<UIAlertAction> = ArrayList()

    /**
     * Add Actions to AlertView
     * @param action: AlertAction
     */
    fun addAction(action: UIAlertAction) {
        actions.add(action)
    }

    /**
     * Add Actions to AlertView
     * @param string: String
     */
    fun setCancelButtonText(string: String) {
        this.cancelButtonText = string
    }

    /**
     * Show AlertView
     * @param activity: AppCompatActivity
     */
    fun show(activity: AppCompatActivity) {
        when (style) {
            UIAlertControllerStyle.ACTIONSHEET -> {
                val bottomSheet = iOSActionSheetFragment(title, message, actions, theme, cancelButtonText)
                bottomSheet.show(activity.supportFragmentManager, bottomSheet.tag)
            }
            UIAlertControllerStyle.ALERT -> {
                val bottomSheet = iOSActionSheetFragment(title, message, actions, theme, cancelButtonText)
                bottomSheet.show(activity.supportFragmentManager, bottomSheet.tag)
            }
        }
    }

    /**
     * Set theme for the AlertView
     * @param theme: AlertTheme
     */
    fun setTheme(theme: AlertThemeStyle) {
        this.theme = theme
    }
}