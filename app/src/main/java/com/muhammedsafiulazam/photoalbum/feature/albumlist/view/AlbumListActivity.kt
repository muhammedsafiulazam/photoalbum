package com.muhammedsafiulazam.photoalbum.feature.albumlist.view

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.muhammedsafiulazam.photoalbum.R
import com.muhammedsafiulazam.photoalbum.activity.BaseActivity
import com.muhammedsafiulazam.photoalbum.activity.IActivityManager
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

class AlbumListActivity : BaseActivity(),
    IAlbumListListener, SwipeRefreshLayout.OnRefreshListener {

    private lateinit var mEventManager: IEventManager
    private lateinit var mActivityManager: IActivityManager
    private val mAlbumList: MutableList<Album> = mutableListOf()
    private val mAlbumListAdapter: AlbumListAdapter by lazy {
        AlbumListAdapter(mAlbumList, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_albumlist)
        setActivityModel(AlbumListActivityModel::class.java)

        mEventManager = getAddOn(AddOnType.EVENT_MANAGER) as IEventManager
        mActivityManager = getAddOn(AddOnType.ACTIVITY_MANAGER) as IActivityManager

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

        receiveEvents(true)

        // Loading request.
        requestLoadAlbums()
    }

    fun updateLoader(show: Boolean) {
        if (show) {
            albumlist_ryv_items.visibility = GONE
            albumlist_pgb_loader.visibility = VISIBLE
            updateMessage(null)
        } else {
            albumlist_pgb_loader.visibility = GONE
        }
    }

    fun updateMessage(message: String?) {
        if (message != null) {
            albumlist_ryv_items.visibility = GONE
            albumlist_txv_message.text = message
            albumlist_txv_message.visibility = VISIBLE
            albumlist_btn_retry.visibility = VISIBLE
            updateLoader(false)
        } else {
            albumlist_txv_message.visibility = GONE
            albumlist_btn_retry.visibility = GONE
        }
    }

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
            updateMessage(getString(R.string.no_connectivity))
        }
    }

    override fun onClickAlbum(album: Album) {
        mActivityManager.loadActivity(PhotoListActivity::class.java, album)
    }

    private fun requestLoadAlbums() {
        val event = Event(AlbumListEventType.REQUEST_LOAD_ALBUMS, null, null)
        mEventManager.send(event)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onReceiveEvents(event: Event) {
        if (TextUtils.equals(AlbumListEventType.UPDATE_LOADER, event.type)) {
            updateLoader(event.data as Boolean)
        } else if (TextUtils.equals(AlbumListEventType.UPDATE_MESSAGE, event.type)) {
            updateMessage(event.data as String)
        } else if (TextUtils.equals(AlbumListEventType.RESPONSE_LOAD_ALBUMS, event.type)) {
            val albumList: List<Album>? = event.data as List<Album>?
            updateView(albumList)
        }
    }

    private fun onClickRetry() {
        requestLoadAlbums()
    }

    override fun onRefresh() {
        albumlist_srl_items.isRefreshing = false
        requestLoadAlbums()
    }

    override fun onDestroy() {
        receiveEvents(false)
        super.onDestroy()
    }
}
