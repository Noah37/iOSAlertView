package com.noah37.alertview

class UIAlertAction {
    var title: String
    var style: UIAlertActionStyle
    var action: ((UIAlertAction) -> Unit)?
    var actionListener: AlertActionListener?

    constructor(title: String, style: UIAlertActionStyle, action: (UIAlertAction) -> Unit) {
        this.title = title
        this.style = style
        this.action = action
        this.actionListener = null
    }

    constructor(title: String, style: UIAlertActionStyle, actionListener: AlertActionListener) {
        this.title = title
        this.style = style
        this.actionListener = actionListener
        this.action = null
    }

    companion object {

        @JvmStatic
        fun actionWithTitle(title: String, style: UIAlertActionStyle, action: (UIAlertAction) -> Unit) =
            UIAlertAction(title, style, action)
    }

}