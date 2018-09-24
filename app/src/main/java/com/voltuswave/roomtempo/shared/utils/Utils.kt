package com.voltuswave.roomtempo.shared.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.graphics.Typeface
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.os.StatFs
import android.support.design.widget.FloatingActionButton
import android.telephony.TelephonyManager
import android.view.View
import android.widget.LinearLayout
import com.voltuswave.roomtempo.utils.StaticConstants

object Utils {

    /**
     * Checks if external storage is available to at least read
     * @return
     */
    val isExternalStorageReadable: Boolean
        get() {
            val state = Environment.getExternalStorageState()
            return Environment.MEDIA_MOUNTED == state || Environment.MEDIA_MOUNTED_READ_ONLY == state
        }

    /**
     * Function to get Internal Memory Available
     *
     * @return
     */
    val availableInternalMemorySize: String
        get() {
            val path = Environment.getDataDirectory()
            val stat = StatFs(path.path)
            val blockSize = stat.blockSize.toLong()
            val availableBlocks = stat.availableBlocks.toLong()
            return formatSize(availableBlocks * blockSize, false)
        }

    /**
     * Function to get Total Internal Memory Available
     *
     * @return
     */

    val totalInternalMemorySize: String
        get() {
            val path = Environment.getDataDirectory()
            val stat = StatFs(path.path)
            val blockSize = stat.blockSize.toLong()
            val totalBlocks = stat.blockCount.toLong()
            return formatSize(totalBlocks * blockSize, false)
        }

    /**
     * Function to get External Memory Available
     *
     * @return
     */

    val availableExternalMemorySize: String
        get() {
            if (externalMemoryAvailable()) {
                val path = Environment.getExternalStorageDirectory()
                val stat = StatFs(path.path)
                val blockSize = stat.blockSize.toLong()
                val availableBlocks = stat.availableBlocks.toLong()
                return formatSize(availableBlocks * blockSize, false)
            } else {
                return "error"
            }
        }

    /**
     * Function to get Total External Memory Available
     *
     * @return
     */

    val totalExternalMemorySize: String
        get() {
            if (externalMemoryAvailable()) {
                val path = Environment.getExternalStorageDirectory()
                val stat = StatFs(path.path)
                val blockSize = stat.blockSize.toLong()
                val totalBlocks = stat.blockCount.toLong()
                return formatSize(totalBlocks * blockSize, false)
            } else {
                return "error"
            }
        }

    /**
     * Function to get normal Typeface
     * @param context
     * @return
     */
    fun getTypeFaceNormal(context: Context): Typeface {
        return Typeface.createFromAsset(context.assets, StaticConstants.TYPEFACE_FONT_ROBOTO_LIGHT)
    }


    /**
     * Function to get Monospace Typeface
     * @param context
     * @return
     */
    fun getTypeFaceMonospace(context: Context): Typeface {
        return Typeface.createFromAsset(context.assets, StaticConstants.TYPEFACE_FONT_ROBOTO_BOLD)
    }


    /**
     * Function to convert Drawable to Bitmap
     * @param context
     * @return
     */
    fun convertDrawableToBitmap(context: Context, drawable: Int): Bitmap {

        return BitmapFactory.decodeResource(context.resources, drawable)
    }


    /**
     * Function to Check External Memory Available
     *
     * @return
     */
    fun externalMemoryAvailable(): Boolean {

        val state = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == state
    }

    /**
     * Function to get Format size
     *
     * @param size
     * @return
     */

    fun formatSize(size: Long, isSuffix: Boolean): String {
        var size = size
        var suffix: String? = null

        if (size >= 1024) {
            suffix = "KB"
            size /= 1024
            if (size >= 1024) {
                suffix = "MB"
                size /= 1024
            } else {
                size = 1
            }
        }

        val resultBuffer = StringBuilder(java.lang.Long.toString(size))

        if (isSuffix) {
            var commaOffset = resultBuffer.length - 3
            while (commaOffset > 0) {
                resultBuffer.insert(commaOffset, ',')
                commaOffset -= 3
            }
            if (suffix != null) resultBuffer.append(suffix)
        }
        return resultBuffer.toString()
    }


    /**
     * Function to make activity full screen
     */
    fun setFullScreen(activity: Activity) {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {

            // lower api
            val v = activity.window.decorView
            v.systemUiVisibility = View.GONE

        } else if (Build.VERSION.SDK_INT >= 19) {

            //for new api versions.
            val decorView = activity.window.decorView
            val uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            decorView.systemUiVisibility = uiOptions
        }
    }


    /**
     * Function to toggle CTA in onboarding with keyboard
     */
    fun toggleCTAKeyboard(root: View, cta_layout: LinearLayout) {

        val r = Rect()
        root.getWindowVisibleDisplayFrame(r)
        val screenHeight = root.rootView.height

        // r.bottom is the position above soft keypad or device button.
        // if keypad is shown, the r.bottom is smaller than that before.
        val keypadHeight = screenHeight - r.bottom

        if (keypadHeight < screenHeight * 0.15) {

            // Splash activity handler
            val handler = Handler()
           // handler.postDelayed({ cta_layout.visibility = View.VISIBLE }, StaticConstants.APP_CTA_TIMEOUT)

        } else {

            cta_layout.visibility = View.GONE
        }
    }


    /**
     * Function to toggle FAB in onboarding with keyboard
     */
    fun toggleFABKeyboard(root: View, fab: FloatingActionButton) {

        val r = Rect()
        root.getWindowVisibleDisplayFrame(r)
        val screenHeight = root.rootView.height

        // r.bottom is the position above soft keypad or device button.
        // if keypad is shown, the r.bottom is smaller than that before.
        val keypadHeight = screenHeight - r.bottom

        if (keypadHeight < screenHeight * 0.15) {

            // Splash activity handler
            val handler = Handler()
          //  handler.postDelayed({ fab.visibility = View.VISIBLE }, StaticConstants.APP_CTA_TIMEOUT)

        } else {

          //  fab.visibility = View.GONE
        }
    }


    /**
     * Function to toggle FAB in onboarding with keyboard
     */
    fun toggleFABKeyboard(root: View, fab: FloatingActionButton, size: Int) {

        val r = Rect()
        root.getWindowVisibleDisplayFrame(r)
        val screenHeight = root.rootView.height

        // r.bottom is the position above soft keypad or device button.
        // if keypad is shown, the r.bottom is smaller than that before.
        val keypadHeight = screenHeight - r.bottom

        if (keypadHeight < screenHeight * 0.15) {

            // Splash activity handler
            val handler = Handler()
            /*handler.postDelayed({
                if (size > 0) {
                    fab.visibility = View.VISIBLE
                }
            }, StaticConstants.APP_CTA_TIMEOUT)*/

        } else {

           // fab.visibility = View.GONE
        }
    }
}
