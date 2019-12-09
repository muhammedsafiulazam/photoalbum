package com.muhammedsafiulazam.photoalbum.launch.view

import android.os.Bundle
import com.muhammedsafiulazam.photoalbum.R
import com.muhammedsafiulazam.photoalbum.view.BaseView
import com.muhammedsafiulazam.photoalbum.view.IViewManager
import com.muhammedsafiulazam.photoalbum.addon.AddOnType
import com.muhammedsafiulazam.photoalbum.feature.albumlist.view.AlbumListActivity



/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

class LaunchActivity : BaseView() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
    }

    override fun onStart() {
        super.onStart()

        // Entry activity.
        val activityManager: IViewManager = getAddOn(AddOnType.VIEW_MANAGER) as IViewManager
        activityManager.loadView(AlbumListActivity::class.java)

        // Finish.
        finish()
    }
}
