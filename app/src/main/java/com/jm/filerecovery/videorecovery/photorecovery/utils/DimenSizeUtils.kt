package com.jm.filerecovery.videorecovery.photorecovery.utils

import android.content.Context
import com.jm.filerecovery.videorecovery.photorecovery.MyApplication

object DimenSizeUtils {

    fun density(context: Context) : Float = context.resources.displayMetrics.density

    fun density() : Float = MyApplication.getInstance().resources.displayMetrics.density

    fun videoPreviewScale():Float {
        val context = MyApplication.getInstance()
        val height = getHeightScreen(context)
        val width = getWidthScreen(context)
        val heightMin = 356*density(context)

        return if((height-heightMin) < width) {
            (height-heightMin)/width
        } else {
            1f
        }
    }

    fun getHeightScreen(context: Context) : Int = context.resources.displayMetrics.heightPixels
    fun getWidthScreen(context: Context) : Int = context.resources.displayMetrics.widthPixels

    fun videoScaleInCutter():Float {
        val context = MyApplication.getInstance()
        val h = getHeightScreen(context)
        val w = getWidthScreen(context)
        val heightMin = 236*density(context)

        return if((h-heightMin) < w) {
            (h-heightMin)/w
        } else {
            1f
        }
    }

}