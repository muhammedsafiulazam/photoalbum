package com.muhammedsafiulazam.photoalbum.feature.photolist.view

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.muhammedsafiulazam.photoalbum.R
import com.muhammedsafiulazam.photoalbum.view.BaseView
import com.muhammedsafiulazam.photoalbum.view.IViewManager
import com.muhammedsafiulazam.photoalbum.addon.AddOnType
import com.muhammedsafiulazam.photoalbum.event.Event
import com.muhammedsafiulazam.photoalbum.event.IEventManager
import com.muhammedsafiulazam.photoalbum.feature.photolist.listener.IPhotoListListener
import com.muhammedsafiulazam.photoalbum.feature.photolist.viewmodel.PhotoListActivityModel
import com.muhammedsafiulazam.photoalbum.feature.photolist.event.PhotoListEventType
import com.muhammedsafiulazam.photoalbum.feature.photoviewer.view.PhotoViewerActivity
import com.muhammedsafiulazam.photoalbum.network.model.photo.Photo
import com.muhammedsafiulazam.photoalbum.utils.ConnectivityUtils
import kotlinx.android.synthetic.main.activity_photolist.*


/**
 * Created by Muhammed Safiul Azam on 19/11/2019.
 */

class PhotoListActivity : BaseView(),
    IPhotoListListener, SwipeRefreshLayout.OnRefreshListener {
    private lateinit var mEventManager: IEventManager
    private lateinit var mActivityManager: IViewManager
    private val mPhotoList: MutableList<Photo> = mutableListOf()
    private val mPhotoListAdapter: PhotoListAdapter by lazy {
        PhotoListAdapter(mPhotoList, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_photolist)
        setActivityModel(PhotoListActivityModel::class.java)

        mEventManager = getAddOn(AddOnType.EVENT_MANAGER) as IEventManager
        mActivityManager = getAddOn(AddOnType.VIEW_MANAGER) as IViewManager

        updateMessage(null)
        updateLoader(false)

        photolist_srl_items.setOnRefreshListener(this)

        // Initialize recycler view.
        val gridLayoutManager = GridLayoutManager(this,
            PhotoListAdapter.SPAN_SIZE
        )
        photolist_ryv_items.setLayoutManager(gridLayoutManager)
        photolist_ryv_items.adapter = mPhotoListAdapter

        photolist_btn_retry.setOnClickListener(View.OnClickListener {
            onClickRetry()
        })

        // Enable receiving events.
        receiveEvents(true)

        // Loading request.
        requestLoadPhotos()
    }

    /**
     * Update loader.
     * @param show flag
     */
    fun updateLoader(show: Boolean) {
        if (show) {
            photolist_ryv_items.visibility = GONE
            photolist_pgb_loader.visibility = VISIBLE
            updateMessage(null)
        } else {
            photolist_pgb_loader.visibility = GONE
        }
    }

    /**
     * Update message.
     * @param message message
     */
    fun updateMessage(message: Any?) {
        if (message != null) {
            photolist_ryv_items.visibility = GONE

            if (message is Int) {
                photolist_txv_message.text = getString(message)
            } else if (message is String) {
                photolist_txv_message.text = message
            } else {
                photolist_txv_message.text = ""
            }

            photolist_txv_message.visibility = VISIBLE
            photolist_btn_retry.visibility = VISIBLE
            updateLoader(false)
        } else {
            photolist_txv_message.text = ""
            photolist_txv_message.visibility = GONE
            photolist_btn_retry.visibility = GONE
        }
    }

    /**
     * Update view with list of photo.
     * @param photoList list of photo
     */
    fun updateView(photoList: List<Photo>?) {
        photolist_ryv_items.visibility = VISIBLE
        updateLoader(false)
        updateMessage(null)

        mPhotoList.clear()

        if (photoList != null) {
            mPhotoList.addAll(photoList)
        }

        photolist_ryv_items.post(Runnable {
            mPhotoListAdapter.notifyDataSetChanged()
        })

        if (mPhotoList.isNullOrEmpty() && !ConnectivityUtils.isOnline()) {
            updateMessage(getString(R.string.photolist_no_connectivity))
        }
    }

    /**
     * On click photo, load photo.
     */
    override fun onClickPhoto(photo: Photo) {
        mActivityManager.loadView(PhotoViewerActivity::class.java, photo)
    }

    /**
     * Request to load photos.
     */
    private fun requestLoadPhotos() {
        val event = Event(PhotoListEventType.VIEWMODEL_REQUEST_LOAD_PHOTOS, getData(), null)
        mEventManager.send(event)
    }

    /**
     * Receive and handle events.
     * @param event event
     */
    @Suppress("UNCHECKED_CAST")
    override fun onReceiveEvents(event: Event) {
        if (TextUtils.equals(PhotoListEventType.VIEW_UPDATE_LOADER, event.type)) {
            updateLoader(event.data as Boolean)
        } else if (TextUtils.equals(PhotoListEventType.VIEW_UPDATE_MESSAGE, event.type)) {
            updateMessage(event.data)
        } else if (TextUtils.equals(PhotoListEventType.VIEWMODEL_RESPONSE_LOAD_PHOTOS, event.type)) {
            val photoList: List<Photo>? = event.data as List<Photo>?
            updateView(photoList)
        }
    }

    /**
     * On click retry, request model to reload photos.
     */
    private fun onClickRetry() {
        requestLoadPhotos()
    }

    /**
     * On refresh, request model to reload photos.
     */
    override fun onRefresh() {
        photolist_srl_items.isRefreshing = false
        requestLoadPhotos()
    }

    /**
     * On destroy, clean
     */
    override fun onDestroy() {
        // Disable receiving events.
        receiveEvents(false)
        super.onDestroy()
    }
}
