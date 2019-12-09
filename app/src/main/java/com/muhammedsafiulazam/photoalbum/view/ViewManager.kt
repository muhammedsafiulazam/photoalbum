package com.muhammedsafiulazam.photoalbum.view

import android.content.Intent
import android.os.Parcelable
import com.muhammedsafiulazam.photoalbum.addon.AddOn

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

class ViewManager : AddOn(), IViewManager {
    private var mCurrentView: BaseView? = null

    /**
     * Get current view.
     * @return current view
     */
    override fun getCurrentView() : BaseView? {
        return mCurrentView
    }

    /**
     * Track starting view.
     * @param view view
     */
    override fun onStartView(view: BaseView) {
        this.mCurrentView = view
    }

    /**
     * Track stopping view.
     * @param view view
     */
    override fun onStopView(view: BaseView) {
        if (view == mCurrentView) {
            mCurrentView = null
        }
    }

    /**
     * Load view.
     * @param view view class
     */
    override fun loadView(view: Class<out BaseView>) {
        loadView(view, null, null)
    }

    /**
     * Load view with flags.
     * @param view view class
     * @param flags flags
     */
    override fun loadView(view: Class<out BaseView>, flags: Int?) {
        loadView(view, flags, null)
    }

    /**
     * Load view with data.
     * @param view view class
     * @param data data
     */
    override fun loadView(view: Class<out BaseView>, data: Parcelable?) {
        loadView(view, null, data)
    }

    /**
     * Load view with flags and data.
     * @param view view class
     * @param flags flags
     * @param data data
     */
    override fun loadView(view: Class<out BaseView>, flags: Int?, data: Parcelable?) {
        if (mCurrentView != null) {
            val intent = Intent(mCurrentView, view)

            if (flags != null) {
                intent.setFlags(flags)
            }

            if (data != null) {
                intent.putExtra(BaseView.KEY_DATA, data)
            }

            mCurrentView?.startActivity(intent)
        }
    }
}