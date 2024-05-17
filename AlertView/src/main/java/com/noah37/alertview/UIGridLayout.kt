package com.noah37.alertview

import android.content.Context
import android.widget.GridLayout

class UIGridLayout(private val context:Context): GridLayout(context) {

    override fun onMeasure(widthSpec: Int, heightSpec: Int) {
        super.onMeasure(widthSpec, heightSpec)

    }
}