package com.muhammedsafiulazam.photoalbum.feature.photoviewer.view

import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.muhammedsafiulazam.photoalbum.R
import com.muhammedsafiulazam.photoalbum.activity.BaseActivity
import com.muhammedsafiulazam.photoalbum.network.model.photo.Photo
import com.muhammedsafiulazam.photoalbum.utils.ConnectivityUtils
import com.muhammedsafiulazam.photoalbum.utils.CoroutineUtils
import com.muhammedsafiulazam.photoalbum.utils.PicassoUtils
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_photolist.*
import kotlinx.android.synthetic.main.activity_photoviewer.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

class PhotoViewerActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var mPhoto: Photo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Fullscreen.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_photoviewer)

        mPhoto = getData() as Photo

        photoviewer_srl_photo.setOnRefreshListener(this)
        photoviewer_txv_title.text = getString(R.string.photoviewer_photo_title, mPhoto.id)
        photoviewer_txv_description.text = mPhoto.title
        photoviewer_phv_photo.setImageDrawable(null)

        photoviewer_btn_retry.setOnClickListener(View.OnClickListener {
            onClickRetry()
        })

        // Load photo.
        loadPhoto()
    }

    private fun updateLoader(show: Boolean) {
        if (show) {
            photoviewer_pgb_loader.visibility = View.VISIBLE
            photoviewer_phv_photo.visibility = View.GONE
            updateMessage(null)
        } else {
            photoviewer_pgb_loader.visibility = View.GONE
        }
    }

    private fun updateMessage(message: String?) {
        if (message != null) {
            photoviewer_txv_message.text = message
            photoviewer_txv_message.visibility = View.VISIBLE
            photoviewer_btn_retry.visibility = View.VISIBLE
            photoviewer_phv_photo.visibility = View.GONE
            updateLoader(false)
        } else {
            photoviewer_txv_message.visibility = View.GONE
            photoviewer_btn_retry.visibility = View.GONE
        }
    }

    private fun onClickRetry() {
        loadPhoto()
    }

    override fun onRefresh() {
        photoviewer_srl_photo.isRefreshing = false
        loadPhoto()
    }

    private fun loadPhoto() {
        // Show loader.
        updateLoader(true)

        CoroutineScope(CoroutineUtils.DISPATCHER_MAIN).launch {
            PicassoUtils.getPicasso().load(mPhoto.url).into(photoviewer_phv_photo, object: Callback {
                override fun onSuccess() {
                    photoviewer_phv_photo.visibility = View.VISIBLE
                    // Better viewer control.
                    photoviewer_srl_photo.isEnabled = false
                    updateLoader(false)
                }
                override fun onError(e: Exception) {
                    PicassoUtils.getPicasso().load(mPhoto.thumbnailUrl).into(photoviewer_phv_photo, object: Callback {
                        override fun onSuccess() {
                            photoviewer_phv_photo.visibility = View.VISIBLE
                            // Better viewer control.
                            photoviewer_srl_photo.isEnabled = false
                            updateLoader(false)
                        }
                        override fun onError(e: Exception) {
                            if (!ConnectivityUtils.isOnline()) {
                                updateMessage(getString(R.string.no_connectivity))

                            } else {
                                updateMessage(getString(R.string.photoviewer_error_load))
                            }
                        }
                    })
                }
            })
        }
    }
}
