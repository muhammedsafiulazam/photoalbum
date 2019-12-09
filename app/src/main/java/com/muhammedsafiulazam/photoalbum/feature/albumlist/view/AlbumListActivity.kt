package com.muhammedsafiulazam.photoalbum.feature.albumlist.view

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
import com.muhammedsafiulazam.photoalbum.feature.albumlist.event.AlbumListEventType
import com.muhammedsafiulazam.photoalbum.feature.albumlist.listener.IAlbumListListener
import com.muhammedsafiulazam.photoalbum.feature.albumlist.model.Album
import com.muhammedsafiulazam.photoalbum.feature.albumlist.viewmodel.AlbumListActivityModel
import com.muhammedsafiulazam.photoalbum.feature.photolist.view.PhotoListActivity
import com.muhammedsafiulazam.photoalbum.utils.ConnectivityUtils
import kotlinx.android.synthetic.main.activity_albumlist.*


/**
 * Created by Muhammed Safiul Azam on 19/11/2019.
 */

class AlbumListActivity : BaseView(),
    IAlbumListListener, SwipeRefreshLayout.OnRefreshListener {

    private lateinit var mEventManager: IEventManager
    private lateinit var mActivityManager: IViewManager
    private val mAlbumList: MutableList<Album> = mutableListOf()
    private val mAlbumListAdapter: AlbumListAdapter by lazy {
        AlbumListAdapter(mAlbumList, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_albumlist)
        setActivityModel(AlbumListActivityModel::class.java)

        mEventManager = getAddOn(AddOnType.EVENT_MANAGER) as IEventManager
        mActivityManager = getAddOn(AddOnType.VIEW_MANAGER) as IViewManager

        updateMessage(null)
        updateLoader(false)

        albumlist_srl_items.setOnRefreshListener(this)

        // Initialize recycler view.
        val gridLayoutManager = GridLayoutManager(this,
            AlbumListAdapter.SPAN_SIZE
        )
        albumlist_ryv_items.setLayoutManager(gridLayoutManager)
        albumlist_ryv_items.adapter = mAlbumListAdapter

        albumlist_btn_retry.setOnClickListener(View.OnClickListener {
            onClickRetry()
        })

        // Enable receiving events.
        receiveEvents(true)

        // Loading request.
        requestLoadAlbums()
    }

    /**
     * Update loader.
     * @param show flag
     */
    fun updateLoader(show: Boolean) {
        if (show) {
            albumlist_ryv_items.visibility = GONE
            albumlist_pgb_loader.visibility = VISIBLE
            updateMessage(null)
        } else {
            albumlist_pgb_loader.visibility = GONE
        }
    }

    /**
     * Update message.
     * @param message message
     */
    fun updateMessage(message: Any?) {
        if (message != null) {
            albumlist_ryv_items.visibility = GONE

            if (message is Int) {
                albumlist_txv_message.text = getString(message)
            } else if (message is String) {
                albumlist_txv_message.text = message
            } else {
                albumlist_txv_message.text = ""
            }

            albumlist_txv_message.visibility = VISIBLE
            albumlist_btn_retry.visibility = VISIBLE
            updateLoader(false)
        } else {
            albumlist_txv_message.text = ""
            albumlist_txv_message.visibility = GONE
            albumlist_btn_retry.visibility = GONE
        }
    }

    /**
     * Update view with list of album.
     * @param albumList list of album
     */
    fun updateView(albumList: List<Album>?) {
        albumlist_ryv_items.visibility = VISIBLE
        updateLoader(false)
        updateMessage(null)

        mAlbumList.clear()

        if (albumList != null) {
            mAlbumList.addAll(albumList)
        }

        albumlist_ryv_items.post(Runnable {
            mAlbumListAdapter.notifyDataSetChanged()
        })

        if (mAlbumList.isNullOrEmpty() && !ConnectivityUtils.isOnline()) {
            updateMessage(getString(R.string.albumlist_no_connectivity))
        }
    }

    /**
     * On click album, load photos.
     * @param album album
     */
    override fun onClickAlbum(album: Album) {
        mActivityManager.loadView(PhotoListActivity::class.java, album)
    }

    /**
     * Request to load albums.
     */
    private fun requestLoadAlbums() {
        val event = Event(AlbumListEventType.VIEWMODEL_REQUEST_LOAD_ALBUMS, null, null)
        mEventManager.send(event)
    }

    /**
     * Receive and handle events.
     * @param event event
     */
    @Suppress("UNCHECKED_CAST")
    override fun onReceiveEvents(event: Event) {
        if (TextUtils.equals(AlbumListEventType.VIEW_UPDATE_LOADER, event.type)) {
            updateLoader(event.data as Boolean)
        } else if (TextUtils.equals(AlbumListEventType.VIEW_UPDATE_MESSAGE, event.type)) {
            updateMessage(event.data)
        } else if (TextUtils.equals(AlbumListEventType.VIEWMODEL_RESPONSE_LOAD_ALBUMS, event.type)) {
            val albumList: List<Album>? = event.data as List<Album>?
            updateView(albumList)
        }
    }

    /**
     * On click retry, request model to reload albums.
     */
    private fun onClickRetry() {
        requestLoadAlbums()
    }

    /**
     * On refresh, request model to relaod albums.
     */
    override fun onRefresh() {
        albumlist_srl_items.isRefreshing = false
        requestLoadAlbums()
    }

    /**
     * On destroy, clean.
     */
    override fun onDestroy() {
        // Disable receiving events.
        receiveEvents(false)
        super.onDestroy()
    }
}
