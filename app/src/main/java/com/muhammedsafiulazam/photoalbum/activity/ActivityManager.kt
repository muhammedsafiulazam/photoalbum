package com.muhammedsafiulazam.photoalbum.activity

import android.content.Intent
import android.os.Parcelable
import com.muhammedsafiulazam.photoalbum.addon.AddOn

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

class ActivityManager : AddOn(), IActivityManager {
    private var mCurrentActivity: BaseActivity? = null

    /**
     * Get current activity.
     * @return current activity
     */
    override fun getCurrentActivity() : BaseActivity? {
        return mCurrentActivity
    }

    /**
     * Track starting activity.
     * @param baseActivity activity
     */
    override fun onStartActivity(baseActivity: BaseActivity) {
        this.mCurrentActivity = baseActivity
    }

    /**
     * Track stopping activity.
     * @param baseActivity activity
     */
    override fun onStopActivity(baseActivity: BaseActivity) {
        if (baseActivity == mCurrentActivity) {
            mCurrentActivity = null
        }
    }

    /**
     * Load activity.
     * @param activity activity class
     */
    override fun loadActivity(activity: Class<out BaseActivity>) {
        loadActivity(activity, null, null)
    }

    /**
     * Load activity with flags.
     * @param activity activity class
     * @param flags flags
     */
    override fun loadActivity(activity: Class<out BaseActivity>, flags: Int?) {
        loadActivity(activity, flags, null)
    }

    /**
     * Load activity with data.
     * @param activity activity class
     * @param data data
     */
    override fun loadActivity(activity: Class<out BaseActivity>, data: Parcelable?) {
        loadActivity(activity, null, data)
    }

    /**
     * Load activity with flags and data.
     * @param activity activity class
     * @param flags flags
     * @param data data
     */
    override fun loadActivity(activity: Class<out BaseActivity>, flags: Int?, data: Parcelable?) {
        if (mCurrentActivity != null) {
            val intent = Intent(mCurrentActivity, activity)

            if (flags != null) {
                intent.setFlags(flags)
            }

            if (data != null) {
                intent.putExtra(BaseActivity.KEY_DATA, data)
            }

            mCurrentActivity?.startActivity(intent)
        }
    }
}