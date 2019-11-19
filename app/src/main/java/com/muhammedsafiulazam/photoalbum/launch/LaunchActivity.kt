package com.muhammedsafiulazam.photoalbum.launch

import android.os.Bundle
import com.muhammedsafiulazam.photoalbum.R
import com.muhammedsafiulazam.photoalbum.activity.BaseActivity
import com.muhammedsafiulazam.photoalbum.activity.IActivityManager
import com.muhammedsafiulazam.photoalbum.addon.AddOnType
import com.muhammedsafiulazam.photoalbum.feature.albumlist.AlbumListActivity

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

class LaunchActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
    }

    override fun onStart() {
        super.onStart()

        // Entry activity.
        val activityManager: IActivityManager = getAddOn(AddOnType.ACTIVITY_MANAGER) as IActivityManager
        activityManager.loadActivity(AlbumListActivity::class.java)
    }
}
